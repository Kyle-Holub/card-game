package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.deck.Card;
import com.holub.kyle.player.Player;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class PlaySequence {

    private List<Player> players;
    public Map<Card, Player> playCards() {
        Map<Card, Player> playedCardsMap = new HashMap<>();
        players.forEach(player -> playedCardsMap.put(player.playCard(), player));
        return playedCardsMap;
    }
}
