package com.holub.kyle.game.neural.function.activation;

public interface ActivationFunction extends DifferentiableFunction {
    double calculateOutput(double summedInput);
}
