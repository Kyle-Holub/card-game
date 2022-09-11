package com.holub.kyle.game.neural;

import com.holub.kyle.game.neural.netcore.NeuralNetLayer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NeuralNet {
    private String id;
    private NeuralNetLayer inputLayer;
    private List<NeuralNetLayer> hiddenLayers;
    private NeuralNetLayer outputLayer;
}
