package com.holub.kyle.deck;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardTest {

    @Test
    void cardHasAFaceValue() {
        Card card = new Card(Suit.DIAMONDS, Rank.ACE);

        assertThat(card.getRank()).isEqualTo(Rank.ACE);
    }

    @Test
    void cardHasASuit() {
        Card card = new Card(Suit.CLUBS, Rank.TWO);

        assertThat(card.getSuit()).isEqualTo(Suit.CLUBS);
    }
}