package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.player.Player;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class BidSequence {

    List<Player> players;

    public Map<Player, Integer> takeBids() {
        Map<Player, Integer> bidMap = new HashMap<>();
        players.forEach(player -> bidMap.put(player, player.getBid()));
        return bidMap;
    }
}
