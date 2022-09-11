package com.holub.kyle.game.neural.netcore;

import lombok.Data;

@Data
public class Connection {
    private final String id;
    private final ProcessingUnit fromNeuron;
    private final ProcessingUnit toNeuron;

    private double weight = 0;
    private double deltaWeight = 0;
    private double prevDeltaWeight = 0;

    public Connection(String id, ProcessingUnit fromN, ProcessingUnit toN) {
        this.id = id;
        fromNeuron = fromN;
        toNeuron = toN;
    }

    public void setDeltaWeight(double deltaWeight) {
        this.prevDeltaWeight = this.deltaWeight;
        this.deltaWeight = deltaWeight;
    }
}
