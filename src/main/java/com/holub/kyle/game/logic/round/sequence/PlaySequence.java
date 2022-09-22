package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.logic.deck.Card;
import com.holub.kyle.game.logic.deck.enums.Suit;
import com.holub.kyle.game.logic.player.Player;
import com.holub.kyle.game.logic.player.PlayerQueue;
import com.holub.kyle.game.logic.trick.TrickEvaluator;
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

    public Map<Player, Integer> update(PlayerQueue players, int numTricks, Suit trumpSuit) {
        Map<Player, Integer> tricksWonMap = initTrickMap(players);

        IntStream.range(0, numTricks).forEach(i -> playTrick(players, trumpSuit, tricksWonMap));

        return tricksWonMap;
    }

    private void playTrick(PlayerQueue players, Suit trumpSuit, Map<Player, Integer> trickMap) {
        Map<Card, Player> playedCardsMap = playCardFromEachPlayer(players, trumpSuit);
        evaluateWinner(trumpSuit, trickMap, playedCardsMap);
    }

    private Map<Card, Player> playCardFromEachPlayer(PlayerQueue players, Suit trumpSuit) {
        Map<Card, Player> playedCardsMap = new HashMap<>();
        Card leadCard = players.getCurrentPlayer().playCard(null, trumpSuit);
        playedCardsMap.put(leadCard, players.getCurrentPlayer());
        players.nextPlayer();

        for (int i = 0; i < players.numPlayers() - 1; i++) {
            playedCardsMap.put(players.getCurrentPlayer().playCard(leadCard.getSuit(), trumpSuit), players.getCurrentPlayer());
            players.nextPlayer();
        }

        return playedCardsMap;
    }

    private void evaluateWinner(Suit trumpSuit, Map<Player, Integer> trickMap, Map<Card, Player> playedCardsMap) {
        log.info("Evaluating trick");
        TrickEvaluator trickEvaluator = new TrickEvaluator();
        Player winner = trickEvaluator.scoreTrick(playedCardsMap, trumpSuit);
        trickMap.compute(winner, addTrickWonFunction);

        winner.setLeadPlayer(true);
        logWinningCard(playedCardsMap, winner);
    }

    private Map<Player, Integer> initTrickMap(PlayerQueue players) {
        Map<Player, Integer> trickMap = new HashMap<>();
        players.getPlayerQ().forEach(player -> trickMap.put(player, 0));
        return trickMap;
    }

    private static void logWinningCard(Map<Card, Player> playedCardsMap, Player winner) {
        List<Card> winningCard = playedCardsMap.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), winner))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        log.info(winner + " won with " + winningCard.get(0));
    }
}
