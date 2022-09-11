package com.holub.kyle.game.neural.function.activation;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SigmoidActivationFunction implements ActivationFunction {

    private double slope = 1d;

    public SigmoidActivationFunction(double slope) {
        this.slope = slope;
    }

    @Override
    public double calculateOutput(double summedInput) {
        double denominator = 1 + Math.exp(-slope * summedInput);

        return (1d / denominator);
    }

    @Override
    public double calculateDerivative(double input) {
        return calculateOutput(input) * (1 - calculateOutput(input));
    }
}
