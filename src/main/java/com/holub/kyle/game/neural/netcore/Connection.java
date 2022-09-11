package com.holub.kyle.game.neural.netcore;

public class Connection {

    protected Neuron fromNeuron;

    protected Neuron toNeuron;

    protected double weight;

    public Connection(Neuron fromNeuron, Neuron toNeuron) {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.weight = Math.random();
    }

    public Connection(Neuron fromNeuron, Neuron toNeuron, double weight) {
        this(fromNeuron, toNeuron);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getInput() {
        return fromNeuron.calculateOutput();
    }

    public double getWeightedInput() {
        return fromNeuron.calculateOutput() * weight;
    }

    public Neuron getFromNeuron() {
        return fromNeuron;
    }

    public Neuron getToNeuron() {
        return toNeuron;
    }
}
