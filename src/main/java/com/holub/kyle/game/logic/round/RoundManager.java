package com.holub.kyle.game.logic.round;

import com.holub.kyle.game.player.NpcPlayer;
import com.holub.kyle.game.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class RoundManager {

    Random rand = new Random();

    public void playGames(int numGames) {
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
        log.info("Starting...");
        List<Player> players = IntStream.range(0, 4).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());

        setRandomDealer(players);

        for (int numCards = 8; numCards > 0; numCards--) {
            executeRound(players, numCards);
        }
        for (int numCards = 2; numCards < 9; numCards++) {
            executeRound(players, numCards);
        }

        log.info("SCORES:");
        players.forEach(player -> log.info(player.toString() + ": " + player.getScore()));
        Optional<Player> winnerO = players.stream().max(Comparator.comparing(Player::getScore));
        if (winnerO.isPresent()) {
            Player winner = winnerO.get();
            log.info("Winner is " + winner + " with " + winner.getScore());
            return winner.getScore();
        }
        throw new NullPointerException("Could not find winner");
    }

    private void executeRound(List<Player> players, int numCards) {
        Round round = new Round(numCards, players);
        round.executeRound();
        incrementDealer(players);
    }

    private void incrementDealer(List<Player> players) {
        // TODO
    }

    private void setRandomDealer(List<Player> players) {
        Player dealer = players.get(rand.nextInt(players.size() - 1));
        log.info(String.format("%s is the dealer", dealer));
        dealer.setDealer();
    }
}
