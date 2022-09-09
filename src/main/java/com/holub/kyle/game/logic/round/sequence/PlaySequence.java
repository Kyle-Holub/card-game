package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.logic.trick.TrickEvaluator;
import com.holub.kyle.game.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class PlaySequence {

    private static final BiFunction<Player, Integer, Integer> addTrickWonFunction = (playerKey, trickValue) -> trickValue + 1;

    public Map<Player, Integer> playCards(List<Player> players, int numTricks, Card trumpCard) {
        Map<Player, Integer> trickMap = initTrickMap(players);
        IntStream.range(0, numTricks).forEach(i -> playTrick(players, trumpCard, trickMap));
        return trickMap;
    }

    private void playTrick(List<Player> players, Card trumpCard, Map<Player, Integer> trickMap) {
        Map<Card, Player> playedCardsMap = playCardFromEachPlayer(players, trumpCard);
        evaluateWinner(trumpCard, trickMap, playedCardsMap);
    }

    private Map<Card, Player> playCardFromEachPlayer(List<Player> players, Card trumpCard) {
        Map<Card, Player> playedCardsMap = new HashMap<>();
        Card leadCard = players.get(0).playCard(null, trumpCard.getSuit());
        playedCardsMap.put(leadCard, players.get(0));
        for (int j = 1; j < players.size(); j++) {
            playedCardsMap.put(players.get(j).playCard(leadCard.getSuit(), trumpCard.getSuit()), players.get(j));
        }
        return playedCardsMap;
    }

    private void evaluateWinner(Card trumpCard, Map<Player, Integer> trickMap, Map<Card, Player> playedCardsMap) {
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

    private Map<Player, Integer> initTrickMap(List<Player> players) {
        Map<Player, Integer> trickMap = new HashMap<>();
        players.forEach(player -> trickMap.put(player, 0));
        return trickMap;
    }
}
