package com.holub.kyle.game.player;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.enums.Suit;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static com.holub.kyle.game.util.RandomUtil.RANDOM;


@Slf4j
@EqualsAndHashCode(callSuper = true)
public class NpcPlayer extends Player {

//    private final NeuralNet net;

    public NpcPlayer() {
        super();
        this.setName(genRandomName());

//        List<Neuron> neurons = IntStream.range(0, 110).mapToObj(i -> new Neuron()).collect(Collectors.toList());
//        NeuralNetLayer inputLayer = new NeuralNetLayer("input", neurons);
//
//        List<Neuron> hiddenLayerNeurons = IntStream.range(0, 32).mapToObj(i -> new Neuron()).collect(Collectors.toList());
//        NeuralNetLayer hiddenLayer = new NeuralNetLayer("hidden", hiddenLayerNeurons);
//
//        List<Neuron> outputLayerNeurons = IntStream.range(0, 8).mapToObj(i -> new Neuron()).collect(Collectors.toList());
//        NeuralNetLayer outputLayer = new NeuralNetLayer("output", outputLayerNeurons);
//
//        net = new NeuralNet("id", inputLayer, List.of(hiddenLayer), outputLayer);
    }

    @Override
    public int getBid() {
        int bound = getHand().size() / 2;
        int bid = 0;
        if (bound > 0) {
            bid = RANDOM.nextInt(bound);
        }
        log.info(String.format("%s bid %s", this, bid));
        return bid;
    }

    @Override
    public Card playCardEnforced(List<Card> playableCards, Suit leadSuit, Suit trumpSuit) {
        int numPlayableCards = playableCards.size();
        if (numPlayableCards > 0) {
            Card cardToPlay = playableCards.get(RANDOM.nextInt(numPlayableCards));
            getHand().remove(cardToPlay);
            log.info(String.format("%s played %s", this, cardToPlay));
            return cardToPlay;
        } else {
            throw new RuntimeException("No cards to play");
        }
    }

    private String genRandomName() {
        String firstName = Arrays.asList(NpcName.FirstNames.values()).get(RANDOM.nextInt(NpcName.FirstNames.values().length)).toString();
        String lastNamePrefix = Arrays.asList(NpcName.LastNamePrefix.values()).get(RANDOM.nextInt(NpcName.LastNamePrefix.values().length)).toString();
        String lastName = Arrays.asList(NpcName.LastName.values()).get(RANDOM.nextInt(NpcName.LastName.values().length)).toString();
        return String.format("%s %s%s", firstName, lastNamePrefix, lastName);
    }
}
