package com.holub.kyle.game.neural.function.input;

import com.holub.kyle.game.neural.netcore.Connection;

import java.util.List;

public interface InputSummingFunction {
    double collectOutput(List<Connection> inputConnections);
}
