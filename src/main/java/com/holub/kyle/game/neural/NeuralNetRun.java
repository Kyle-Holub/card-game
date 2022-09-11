package com.holub.kyle.game.neural;

import com.holub.kyle.game.neural.function.activation.ActivationFunction;
import com.holub.kyle.game.neural.function.activation.IdentityActivationFunction;
import com.holub.kyle.game.neural.function.activation.SigmoidActivationFunction;
import com.holub.kyle.game.neural.netcore.Connection;
import com.holub.kyle.game.neural.netcore.Neuron;
import com.holub.kyle.game.neural.netcore.RandomGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NeuralNetRun {
    private final List<Neuron> inputLayer = new ArrayList<>();
    private final List<Neuron> hiddenLayer = new ArrayList<>();
    private final List<Neuron> outputLayer = new ArrayList<>();

    private static final Neuron BIAS = new Neuron("BIAS");
    private static final double LEARNING_RATE = 2.9f;
    private static final double MOMENTUM = 0.7f;

    /**
     * XOR inputs
     */
    private final double[][] inputs = { { 1, 1 }, { 1, 0 }, { 0, 1 }, { 0, 0 } };

    /**
     * XOR expected outputs
     */
    private final double[][] expectedOutputs = { { 0 }, { 1 }, { 1 }, { 0 } };

    /**
     * Result output with initial values
     */
    private final double[][] resultOutputs = { { -1 }, { -1 }, { -1 }, { -1 } };

    public static void main(String[] args) {
        NeuralNetRun neuralNet = new NeuralNetRun(2, 4, 1, new SigmoidActivationFunction());
        int maxRuns = 50000;
        double minErrorCondition = 0.001;
        neuralNet.train(maxRuns, minErrorCondition);
    }

    public NeuralNetRun(int numberOfInputNeurons, int numberOfHiddenNeurons, int numberOfOutputNeurons,
                     ActivationFunction activationFunction) {

        for (int j = 0; j < numberOfInputNeurons; j++) {
            Neuron neuron = new Neuron(RandomGenerator.generateId(), new IdentityActivationFunction());
            inputLayer.add(neuron);
        }

        for (int j = 0; j < numberOfHiddenNeurons; j++) {
            Neuron neuron = new Neuron(RandomGenerator.generateId(), inputLayer, BIAS, activationFunction);
            hiddenLayer.add(neuron);
        }

        for (int j = 0; j < numberOfOutputNeurons; j++) {
            Neuron neuron = new Neuron(RandomGenerator.generateId(), hiddenLayer, BIAS, activationFunction);
            outputLayer.add(neuron);
        }

        for (Neuron neuron : hiddenLayer) {
            List<Connection> connections = neuron.getInputConnections();
            for (Connection conn : connections) {
                conn.setWeight(RandomGenerator.getRandom());
            }
        }
        for (Neuron neuron : outputLayer) {
            List<Connection> connections = neuron.getInputConnections();
            for (Connection conn : connections) {
                conn.setWeight(RandomGenerator.getRandom());
            }
        }
    }
    
    public void setInput(double[] inputs) {
        for (int i = 0; i < inputLayer.size(); i++) {
            inputLayer.get(i).setOutput(inputs[i]);
        }
    }

    public double[] getOutput() {
        double[] outputs = new double[outputLayer.size()];
        for (int i = 0; i < outputLayer.size(); i++)
            outputs[i] = outputLayer.get(i).getOutput();
        return outputs;
    }

    /**
     * Calculate the output of the neural network based on the input, the
     * forward operation
     */
    public void activate() {
        for (Neuron neuron : hiddenLayer)
            neuron.calculateOutput();
        for (Neuron neuron : outputLayer)
            neuron.calculateOutput();
    }
  
    private void applyBackpropagation(double[] expectedOutput) {
        int i = 0;
        for (Neuron neuron : outputLayer) {
            List<Connection> connections = neuron.getInputConnections();
            for (Connection con : connections) {
                double ak = neuron.getOutput();
                double ai = con.getFromNeuron().getOutput();
                double desiredOutput = expectedOutput[i];

                double partialDerivative = -ak * (1 - ak) * ai * (desiredOutput - ak);
                double deltaWeight = -LEARNING_RATE * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + MOMENTUM * con.getPrevDeltaWeight());
            }
            i++;
        }

        for (Neuron neuron : hiddenLayer) {
            List<Connection> connections = neuron.getInputConnections();
            for (Connection con : connections) {
                double aj = neuron.getOutput();
                double ai = con.getFromNeuron().getOutput();
                double sumKOutputs = 0;
                int j = 0;
                for (Neuron outputNeuron : outputLayer) {
                    double wjk = outputNeuron.getConnection(neuron.getId()).getWeight();
                    double desiredOutput = expectedOutput[j];
                    double ak = outputNeuron.getOutput();
                    j++;
                    sumKOutputs = sumKOutputs + (-(desiredOutput - ak) * ak * (1 - ak) * wjk);
                }

                double partialDerivative = aj * (1 - aj) * ai * sumKOutputs;
                double deltaWeight = -LEARNING_RATE * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + MOMENTUM * con.getPrevDeltaWeight());
            }
        }
    }

    private void train(int maxSteps, double minError) {
        int i;
        double error = 1;
        for (i = 0; i < maxSteps && error > minError; i++) {
            error = 0;
            for (int p = 0; p < inputs.length; p++) {
                setInput(inputs[p]);

                activate();

                double[] output = getOutput();
                resultOutputs[p] = output;

                for (int j = 0; j < expectedOutputs[p].length; j++) {
                    double err = Math.pow(output[j] - expectedOutputs[p][j], 2);
                    error += err;
                }

                applyBackpropagation(expectedOutputs[p]);
            }
        }

        printResult(inputs);

        log.info("Sum of squared errors = " + error);
        log.info("EPOCH " + i + "\n");
        if (i == maxSteps) {
            log.info("Error in training, try again!");
        }
    }

    private void printResult(double[][] inputs) {
        log.info("Multilayer perceptron with XOR training");
        for (int p = 0; p < inputs.length; p++) {
            printValues("INPUTS: ", inputLayer, inputs[p]);
            printValues("EXPECTED: ", outputLayer, expectedOutputs[p]);
            printValues("ACTUAL: ", outputLayer, resultOutputs[p]);
        }
    }

    private void printValues(String label, List<Neuron> inputLayer, double[] inputs) {
        StringBuilder inputBuilder = new StringBuilder(label);
        for (int x = 0; x < inputLayer.size(); x++) {
            inputBuilder.append(inputs[x]).append(" ");
        }
        log.info(inputBuilder.toString());
    }
}
