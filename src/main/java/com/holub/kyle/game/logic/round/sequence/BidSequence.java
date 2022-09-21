package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.logic.player.Player;
import com.holub.kyle.game.logic.player.PlayerQueue;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class BidSequence {

    private PlayerQueue players;
    private int numCards;

    public Map<Player, Integer> takeBids() {
        Map<Player, Integer> bidMap = new HashMap<>();
        int totalBid = 0;
        for (int i = 0; i < players.numPlayers() - 1; i++) {
            int bid = players.getCurrentPlayer().getBid();
            bidMap.put(players.getCurrentPlayer(), bid);
            totalBid += bid;
            players.nextPlayer();
        }

        int bid;
        if (totalBid <= numCards) {
            int cannotBid = numCards - totalBid;
            bid = players.getCurrentPlayer().getBidWithCatch(cannotBid);
        } else {
            bid = players.getCurrentPlayer().getBid();
        }
        bidMap.put(players.getCurrentPlayer(), bid);
        players.nextPlayer();

        return bidMap;
    }
}
