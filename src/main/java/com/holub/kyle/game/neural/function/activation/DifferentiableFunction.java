package com.holub.kyle.game.neural.function.activation;

public interface DifferentiableFunction {
    double calculateDerivative(double totalInput);
}