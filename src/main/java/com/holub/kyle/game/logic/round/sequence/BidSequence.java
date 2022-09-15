package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.player.Player;
import com.holub.kyle.game.player.PlayerQueue;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class BidSequence {

    PlayerQueue players;

    public Map<Player, Integer> takeBids() {
        Map<Player, Integer> bidMap = new HashMap<>();
        players.getPlayerQ().forEach(player -> bidMap.put(player, player.getBid()));
        return bidMap;
    }
}
