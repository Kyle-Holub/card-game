package com.holub.kyle.game.neural.netcore;

import com.holub.kyle.game.neural.function.activation.ActivationFunction;
import com.holub.kyle.game.neural.function.input.InputSummingFunction;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private String id;

    protected List<Connection> inputConnections;

    protected List<Connection> outputConnections;

    protected InputSummingFunction inputSummingFunction;

    protected ActivationFunction activationFunction;

    public Neuron() {
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
    }

    public double calculateOutput() {
        double totalInput = inputSummingFunction.collectOutput(inputConnections);

        return activationFunction.calculateOutput(totalInput);
    }
}
