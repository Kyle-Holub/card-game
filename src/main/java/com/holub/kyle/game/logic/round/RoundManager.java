package com.holub.kyle.game.logic.round;

import com.holub.kyle.player.NpcPlayer;
import com.holub.kyle.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class RoundManager {

    public void playRounds() {
        log.info("Starting...");
        List<Player> players = IntStream.range(0, 4).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());

        for (int i = 8; i > 0; i--) {
            Round round = new Round(i, players);
            round.executeRound();
        }
        for (int i = 1; i < 9; i++) {
            Round round = new Round(i, players);
            round.executeRound();
        }

        log.info("SCORES:");
        players.forEach(player -> log.info(player.toString() + ": " + player.getScore()));
        Player winner = players.stream().max(Comparator.comparing(Player::getScore)).get();
        log.info("Winner is " + winner + " with " + winner.getScore());
    }
}
