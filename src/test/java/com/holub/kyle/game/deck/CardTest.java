package com.holub.kyle.game.deck;

import com.holub.kyle.game.deck.enums.Rank;
import com.holub.kyle.game.deck.enums.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardTest {

    @Test
    void cardHasAFaceValue() {
        Card card = new Card(Rank.ACE, Suit.DIAMONDS);

        assertThat(card.getRank()).isEqualTo(Rank.ACE);
    }

    @Test
    void cardHasASuit() {
        Card card = new Card( Rank.TWO, Suit.CLUBS);

        assertThat(card.getSuit()).isEqualTo(Suit.CLUBS);
    }
}