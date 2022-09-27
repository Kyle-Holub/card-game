package com.holub.kyle.game.logic.hand.sequence;

import com.holub.kyle.game.engine.Updateable;
import com.holub.kyle.game.logic.player.Player;
import com.holub.kyle.game.logic.player.PlayerQueue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BidSequence implements Updateable {

    private final PlayerQueue players;
    private final int numTricks;

    @Getter
    private final Map<Player, Integer> bidMap;
    @Getter
    private boolean isBidding;
    private int numPlayersBid;
    private int totalBidsMade;

    public BidSequence(PlayerQueue players, int numTricks) {
        this.players = players;
        this.numTricks = numTricks;
        bidMap = new HashMap<>();
        isBidding = true;
    }

    public void update() {
        if (isBidding) {
            if (numPlayersBid == players.numPlayers() - 1) {

                int bid;
                if (totalBidsMade <= numTricks) {
                    int cannotBid = numTricks - numPlayersBid;
                    bid = players.getCurrentPlayer().getBidWithCatch(cannotBid);
                } else {
                    bid = players.getCurrentPlayer().getBid();
                }

                bidMap.put(players.getCurrentPlayer(), bid);
                numPlayersBid++;
                totalBidsMade += bid;
                players.nextPlayer();
            } else if (numPlayersBid < players.numPlayers()) {

                int bid = players.getCurrentPlayer().getBid();

                bidMap.put(players.getCurrentPlayer(), bid);
                numPlayersBid++;
                totalBidsMade += bid;
                players.nextPlayer();
            } else {
                isBidding = false;
            }
        }
    }

    public void quickUpdate() {
        int totalBid = 0;
        for (int i = 0; i < players.numPlayers() - 1; i++) {
            int bid = players.getCurrentPlayer().getBid();
            bidMap.put(players.getCurrentPlayer(), bid);
            totalBid += bid;
            players.nextPlayer();
        }

        int bid;
        if (totalBid <= numTricks) {
            int cannotBid = numTricks - totalBid;
            bid = players.getCurrentPlayer().getBidWithCatch(cannotBid);
        } else {
            bid = players.getCurrentPlayer().getBid();
        }
        bidMap.put(players.getCurrentPlayer(), bid);
        players.nextPlayer();
        isBidding = false;
    }
}
