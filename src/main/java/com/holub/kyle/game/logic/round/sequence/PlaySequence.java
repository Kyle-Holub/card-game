package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.deck.Card;
import com.holub.kyle.game.logic.trick.TrickEvaluator;
import com.holub.kyle.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
public class PlaySequence {

    BiFunction<Player, Integer, Integer> addTrickWonFunction = (playerKey, trickValue) -> trickValue + 1;

    public Map<Player, Integer> playCards(List<Player> players, int numTricks, Card trumpCard) {
        Map<Player, Integer> trickMap = initTrickMap(players);

        for (int i = 0; i < numTricks; i++) {
            Map<Card, Player> playedCardsMap = new HashMap<>();
            players.forEach(player -> playedCardsMap.put(player.playCard(null, trumpCard.getSuit()), player));

            log.info("Evaluating trick");
            TrickEvaluator trickEvaluator = new TrickEvaluator();
            Player winner = trickEvaluator.scoreTrick(playedCardsMap, trumpCard.getSuit());
            trickMap.compute(winner, addTrickWonFunction);

            List<Card> winningCard = playedCardsMap.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), winner))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            log.info(winner + " won with " + winningCard.get(0));
        }

        return trickMap;
    }

    private Map<Player, Integer> initTrickMap(List<Player> players) {
        Map<Player, Integer> trickMap = new HashMap<>();
        players.forEach(player -> trickMap.put(player, 0));
        return trickMap;
    }
}
