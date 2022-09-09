package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.player.Player;

import java.util.List;
import java.util.Map;

public class ScoreSequence {

    public void tallyScores(List<Player> players, Map<Player, Integer> bidMap, Map<Player, Integer> trickMap) {
        players.forEach(player -> {
            int playerBid = bidMap.get(player);
            int tricks = trickMap.get(player);
            if (playerBid == tricks) {
                player.addScore(10 + 2 * tricks);
            } else {
                int numOverUnder = Math.abs(tricks - playerBid);
                player.addScore(-5 * numOverUnder);
            }
        });
    }
}
