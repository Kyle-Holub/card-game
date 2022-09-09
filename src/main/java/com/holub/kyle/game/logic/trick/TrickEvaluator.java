package com.holub.kyle.game.logic.trick;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.enums.Suit;
import com.holub.kyle.game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrickEvaluator {
    public Player scoreTrick(Map<Card, Player> playedCardsMap, Suit trumpSuit) {
        CardComparator comparator = new CardComparator();
        List<Card> playedCards = new ArrayList<>(playedCardsMap.keySet());
        Card winningCard = comparator.compare(playedCards, trumpSuit);
        return playedCardsMap.get(winningCard);
    }
}
