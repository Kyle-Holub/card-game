package com.holub.kyle.assertions;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;
import org.assertj.core.api.AbstractAssert;

public class CardAssert extends AbstractAssert<CardAssert, Card> {

    protected CardAssert(Card cardActual) {
        super(cardActual, CardAssert.class);
    }

    public static CardAssert assertThat(Card cardActual) {
        return new CardAssert(cardActual);
    }
    public CardAssert hasRank(Rank rankExpected) {
        isNotNull();
        if (actual.getRank() != rankExpected) {
            failWithMessage("Expected card to be %s but was %s", rankExpected, actual.getRank());
        }
        return this;
    }

    public CardAssert hasSuit(Suit suitExpected) {
        isNotNull();
        if (actual.getSuit() != suitExpected) {
            failWithMessage("Expected card to be %s but was %s", suitExpected, actual.getRank());
        }
        return this;
    }

}
