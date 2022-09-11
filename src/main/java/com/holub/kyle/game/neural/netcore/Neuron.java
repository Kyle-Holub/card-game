package com.holub.kyle.game.neural.netcore;

import com.holub.kyle.game.neural.function.activation.ActivationFunction;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Neuron implements ProcessingUnit {

    private final String id;
    private double output;
    private static final double BIAS = 1;

    private Connection biasConnection;

    private List<Connection> inputConnections = new ArrayList<>();

    private Map<String, Connection> connectionLookup = new HashMap<>();

    protected ActivationFunction activationFunction;

    public Neuron(String id) {
        this.id = id;
    }

    public Neuron(String id, ActivationFunction activationFunction) {
        this.id = id;
        this.activationFunction = activationFunction;
    }

    public Neuron(String id, List<ProcessingUnit> inNeurons, ActivationFunction activationFunction) {
        this.id = id;
        this.activationFunction = activationFunction;
        addInConnections(inNeurons);
    }

    public Neuron(String id, List<ProcessingUnit> inNeurons, ProcessingUnit bias,
                  ActivationFunction activationFunction) {
        this(id, inNeurons, activationFunction);
        addBiasConnection(bias);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void calculateOutput() {
        double weightedSum = 0;
        for (Connection con : inputConnections) {
            double weight = con.getWeight();
            double previousLayerOutput = con.getFromNeuron().getOutput();

            weightedSum = weightedSum + (weight * previousLayerOutput);
        }

        if (biasConnection != null) {
            weightedSum += (biasConnection.getWeight() * BIAS);
        }

        output = activationFunction.calculateOutput(weightedSum);
    }

    private void addInConnections(List<ProcessingUnit> inNeurons) {
        for (ProcessingUnit neuron : inNeurons) {
            Connection con = new Connection(RandomGenerator.generateId(), neuron, this);
            inputConnections.add(con);
            connectionLookup.put(neuron.getId(), con);
        }
    }

    @Override
    public Connection getConnection(String neuronId) {
        return connectionLookup.get(neuronId);
    }

    private void addInConnection(ProcessingUnit neuron) {
        Connection con = new Connection(RandomGenerator.generateId(), neuron, this);
        inputConnections.add(con);
    }

    @Override
    public List<Connection> getInputConnections() {
        return inputConnections;
    }

    private void addBiasConnection(ProcessingUnit neuron) {
        Connection con = new Connection(RandomGenerator.generateId(), neuron, this);
        biasConnection = con;
        inputConnections.add(con);
    }

    @Override
    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }
}
