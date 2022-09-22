package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.logic.player.Player;
import com.holub.kyle.game.logic.player.PlayerQueue;

import java.util.Map;

public class ScoreSequence {
    public void tallyScores(PlayerQueue players, Map<Player, Integer> bidMap, Map<Player, Integer> trickMap) {
        players.getPlayerQ().forEach(player -> tallyScore(bidMap, trickMap, player));
    }

    private static void tallyScore(Map<Player, Integer> bidMap, Map<Player, Integer> trickMap, Player player) {
        int playerBid = bidMap.get(player);
        int tricks = trickMap.get(player);
        if (playerBid == tricks) {
            addPoints(player, tricks);
        } else {
            deductPoints(player, playerBid, tricks);
        }
    }

    private static void addPoints(Player player, int tricks) {
        player.addScore(10 + 2 * tricks);
    }

    private static void deductPoints(Player player, int playerBid, int tricks) {
        int numOverUnder = Math.abs(tricks - playerBid);
        player.addScore(-5 * numOverUnder);
    }
}
