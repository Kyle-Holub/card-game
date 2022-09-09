package com.holub.kyle.game.logic.trick;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Suit;
import com.holub.kyle.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrickEvaluator {

    public Player scoreTrick(Map<Card, Player> playedCardsMap, Suit trumpSuit) {
        CardComparator comparator = new CardComparator();
        List<Card> cards = new ArrayList<>(playedCardsMap.keySet());
        Card winningCard = comparator.compare(List.of(cards.get(0), cards.get(1)), trumpSuit);
        return playedCardsMap.get(winningCard);
    }
}
