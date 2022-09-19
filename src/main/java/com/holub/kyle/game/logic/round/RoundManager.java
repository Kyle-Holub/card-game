package com.holub.kyle.game.logic.round;

import com.holub.kyle.game.player.NpcPlayer;
import com.holub.kyle.game.player.Player;
import com.holub.kyle.game.player.PlayerQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class RoundManager {

    private static final int NUM_PLAYERS = 4;

    private PlayerQueue players;

    public void manageRounds(int numGames) {
        int highestScore = 0;

        for (int i = 0; i < numGames; i++) {
            int score = playRounds();
            if (score > highestScore) {
                highestScore = score;
            }
        }

        log.info("HIGHEST SCORE:" + highestScore);
    }

    public int playRounds() {
        players = new PlayerQueue(IntStream.range(0, NUM_PLAYERS).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList()));
        log.info("Starting...");

        for (int numCards = 8; numCards > 0; numCards--) {
            executeRound(numCards);
        }
        for (int numCards = 2; numCards < 9; numCards++) {
            executeRound(numCards);
        }

        log.info("SCORES:");
        players.getPlayerQ().forEach(player -> log.info(player.toString() + ": " + player.getScore()));
        Optional<Player> winnerO = players.getPlayerQ().stream().max(Comparator.comparing(Player::getScore));
        if (winnerO.isPresent()) {
            Player winner = winnerO.get();
            log.info("Winner is " + winner + " with " + winner.getScore());
            return winner.getScore();
        }
        throw new NullPointerException("Could not find winner");
    }

    private void executeRound(int numCards) {
        Round round = new Round(numCards, players);
        round.executeRound();
    }
}
