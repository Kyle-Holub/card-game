package com.holub.kyle.game.logic.hand.sequence;

import com.holub.kyle.game.engine.Updateable;
import com.holub.kyle.game.logic.deck.Card;
import com.holub.kyle.game.logic.deck.enums.Suit;
import com.holub.kyle.game.logic.player.Player;
import com.holub.kyle.game.logic.player.PlayerQueue;
import com.holub.kyle.game.logic.trick.TrickEvaluator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
public class PlaySequence implements Updateable {

    private final PlayerQueue players;
    private final int numTricks;
    @Setter
    private Suit trumpSuit;
    private Suit leadSuit;
    private int numCardsPlayed;
    @Getter
    private boolean isPlaying;
    @Getter
    private Map<Player, Integer> trickMap;
    private Map<Card, Player> playedCardsMap;
    private int numTricksComplete;

    private static final BiFunction<Player, Integer, Integer> addTrickWonFunction = (playerKey, trickValue) -> trickValue + 1;

    public PlaySequence(PlayerQueue players, int numTricks) {
        this.players = players;
        this.numTricks = numTricks;
        playedCardsMap = new HashMap<>();
        isPlaying = true;
        trickMap = initTrickMap(players);
    }

    public void update() {
        if (isPlaying) {
            if (numCardsPlayed == 0) {
                Card cardPlayed = players.getCurrentPlayer().playCard(null, trumpSuit);
                leadSuit = cardPlayed.getSuit();
                playedCardsMap.put(cardPlayed, players.getCurrentPlayer());
                numCardsPlayed++;
                players.nextPlayer();
            } else if (numCardsPlayed < players.numPlayers()) {
                Card cardPlayed = players.getCurrentPlayer().playCard(leadSuit, trumpSuit);
                playedCardsMap.put(cardPlayed, players.getCurrentPlayer());
                numCardsPlayed++;
                players.nextPlayer();
            } else {
                evaluateWinner();
                if (numTricksComplete < numTricks) {
                    numCardsPlayed = 0;
                    playedCardsMap = new HashMap<>();
                } else {
                    isPlaying = false;
                }
            }
        }
    }

    private void evaluateWinner() {
        log.info("Evaluating trick");
        TrickEvaluator trickEvaluator = new TrickEvaluator();
        Player winner = trickEvaluator.scoreTrick(playedCardsMap, trumpSuit);
        trickMap.compute(winner, addTrickWonFunction);
        numTricksComplete++;

        winner.setLeadPlayer(true);
        logWinningCard(playedCardsMap, winner);
    }

    private Map<Player, Integer> initTrickMap(PlayerQueue players) {
        trickMap = new HashMap<>();
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
