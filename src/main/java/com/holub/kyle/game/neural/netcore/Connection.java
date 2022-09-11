package com.holub.kyle.game.neural.netcore;

import lombok.Data;

@Data
public class Connection {
    private final String id;
    private final Neuron fromNeuron;
    private final Neuron toNeuron;

    private double weight = 0;
    private double deltaWeight = 0;
    private double prevDeltaWeight = 0;

    public Connection(String id, Neuron fromN, Neuron toN) {
        this.id = id;
        fromNeuron = fromN;
        toNeuron = toN;
    }

    public void setDeltaWeight(double deltaWeight) {
        this.prevDeltaWeight = this.deltaWeight;
        this.deltaWeight = deltaWeight;
    }
}
