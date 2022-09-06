package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.deck.Card;
import com.holub.kyle.game.logic.trick.TrickEvaluator;
import com.holub.kyle.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class PlaySequence {

    BiFunction<Player, Integer, Integer> addTrickWonFunction = (playerKey, trickValue) -> trickValue + 1;

    public Map<Player, Integer> playCards(List<Player> players) {
        Map<Player, Integer> trickMap = initTrickMap(players);
        Map<Card, Player> playedCardsMap = new HashMap<>();
        players.forEach(player -> playedCardsMap.put(player.playCard(), player));

        TrickEvaluator trickEvaluator = new TrickEvaluator();
        Player winner = trickEvaluator.scoreTrick(playedCardsMap);
        trickMap.compute(winner, addTrickWonFunction);

        return trickMap;
    }

    private Map<Player, Integer> initTrickMap(List<Player> players) {
        Map<Player, Integer> trickMap = new HashMap<>();
        players.forEach(player -> trickMap.put(player, 0));
        return trickMap;
    }
}
