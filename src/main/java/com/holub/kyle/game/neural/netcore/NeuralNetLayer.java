package com.holub.kyle.game.neural.netcore;


import java.util.ArrayList;
import java.util.List;

public class NeuralNetLayer {

    private final String id;

    protected List<Neuron> neurons;

    public NeuralNetLayer(String id) {
        this.id = id;
        neurons = new ArrayList<>();
    }

    public NeuralNetLayer(String id, List<Neuron> neurons) {
        this.id = id;
        this.neurons = neurons;
    }
}
