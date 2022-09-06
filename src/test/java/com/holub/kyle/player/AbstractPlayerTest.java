package com.holub.kyle.player;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractPlayerTest {

    abstract Player getPlayer();

    @Test
    void canGivePlayerACard() {
        Card expectedCard = new Card(Suit.DIAMONDS, Rank.ACE);

        getPlayer().giveCard(expectedCard);

        assertThat(getPlayer().getHand()).contains(expectedCard);
    }

    @Test
    void canGivePlayerManyCards() {
        List<Card> expectedCards = List.of(new Card(Suit.DIAMONDS, Rank.ACE), new Card(Suit.DIAMONDS, Rank.TWO));

        getPlayer().giveCards(expectedCards);

        assertThat(getPlayer().getHand()).containsAll(expectedCards);
    }

}