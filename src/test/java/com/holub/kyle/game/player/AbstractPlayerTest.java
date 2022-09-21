package com.holub.kyle.game.player;

import com.holub.kyle.game.logic.deck.Card;
import com.holub.kyle.game.logic.deck.enums.Rank;
import com.holub.kyle.game.logic.deck.enums.Suit;
import com.holub.kyle.game.logic.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractPlayerTest {

    abstract Player getPlayer();

    @Test
    void canGivePlayerACard() {
        Card expectedCard = new Card(Rank.ACE, Suit.DIAMONDS);

        getPlayer().giveCard(expectedCard);

        assertThat(getPlayer().getHand()).contains(expectedCard);
    }

    @Test
    void canGivePlayerManyCards() {
        List<Card> expectedCards = List.of(new Card(Rank.ACE, Suit.DIAMONDS), new Card(Rank.TWO, Suit.DIAMONDS));

        getPlayer().giveCards(expectedCards);

        assertThat(getPlayer().getHand()).containsAll(expectedCards);
    }

}