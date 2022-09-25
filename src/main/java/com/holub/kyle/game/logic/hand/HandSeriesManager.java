package com.holub.kyle.game.logic.hand;

import com.holub.kyle.game.engine.Updateable;
import com.holub.kyle.game.logic.player.NpcPlayer;
import com.holub.kyle.game.logic.player.Player;
import com.holub.kyle.game.logic.player.PlayerQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class HandSeriesManager implements Updateable {

    private static final int NUM_PLAYERS = 5;
    private static final int MAX_TRICKS = 8;

    private PlayerQueue players;
    private Hand hand;

    @Getter
    private boolean isGameOver;
    private boolean isNumTricksDescending;

    @Getter
    private int winnerScore;
    @Getter
    private int handNumber = 1;
    private int numTricks;

    public void init() {
        players =  new PlayerQueue(IntStream.range(0, NUM_PLAYERS).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList()));
        hand =  new Hand(players);
    }

    @Override
    public void update() {
        hand.update();
        if (hand.isHandOver()) {
            if (isNumTricksDescending && numTricks == 1) {
                gameOver();
            } else {
                startNewHand();
            }
        }
    }

    private void startNewHand() {
        handNumber++;
        updateNumTricks();
        hand.newHand(numTricks);
    }

    private void updateNumTricks() {
        if (numTricks == MAX_TRICKS) {
            isNumTricksDescending = true;
        }
        if (isNumTricksDescending) {
            numTricks--;
        } else {
            numTricks++;
        }
    }

    private void gameOver() {
        isGameOver = true;

        log.info("SCORES:");
        players.getPlayerQ().forEach(player -> log.info(player.toString() + ": " + player.getScore()));
        Optional<Player> winnerO = players.getPlayerQ().stream().max(Comparator.comparing(Player::getScore));
        if (winnerO.isPresent()) {
            Player winner = winnerO.get();
            log.info("Winner is " + winner + " with " + winner.getScore());
            winnerScore = winner.getScore();
        } else {
            throw new NullPointerException("Could not find winner");
        }
    }
}
