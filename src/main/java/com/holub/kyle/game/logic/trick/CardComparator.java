package com.holub.kyle.game.logic.trick;

import com.holub.kyle.deck.Card;

public class CardComparator {
    public Card compare(Card cardOne, Card cardTwo) {
        if (suitsMatch(cardOne, cardTwo)) {
            return compareRanks(cardOne, cardTwo);
        } else if (cardOne.getSuitValue() > cardTwo.getSuitValue()) {
            return cardOne;
        } else {
            return cardTwo;
        }
    }

    private static boolean suitsMatch(Card cardOne, Card cardTwo) {
        return cardOne.getSuitValue() == cardTwo.getSuitValue();
    }

    private Card compareRanks(Card cardOne, Card cardTwo) {
        if (cardOne.getRankValue() > cardTwo.getRankValue()) {
            return cardOne;
        } else if (cardOne.getRankValue() < cardTwo.getRankValue()) {
            return cardTwo;
        } else {
            throw new IllegalArgumentException(String.format("Cards cannot be identical: %s & %s", cardOne, cardTwo));
        }
    }
}
