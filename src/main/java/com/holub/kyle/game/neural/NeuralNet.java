package com.holub.kyle.game.neural;

import com.holub.kyle.game.neural.netcore.NeuralNetLayer;
import lombok.Getter;

import java.util.List;

@Getter
public class NeuralNet {

    private final String id;

    private final NeuralNetLayer inputLayer;

    private final List<NeuralNetLayer> hiddenLayers;

    private final NeuralNetLayer outputLayer;

    public NeuralNet(String id, NeuralNetLayer inputLayer, List<NeuralNetLayer> hiddenLayers,
                     NeuralNetLayer outputLayer) {
        this.id = id;
        this.inputLayer = inputLayer;
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;
    }
}
