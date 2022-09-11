package com.holub.kyle.game.neural.function.input;


import com.holub.kyle.game.neural.netcore.Connection;

import java.util.List;

public final class WeightedSumFunction implements InputSummingFunction {

    @Override
    public double collectOutput(List<Connection> inputConnections) {
        double weightedSum = 0d;
//        for (Connection connection : inputConnections) {
//            weightedSum += connection.getWeightedInput();
//        }

        return weightedSum;
    }
}
