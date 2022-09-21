package com.holub.kyle.game.logic.player;

import com.holub.kyle.game.logic.deck.Card;
import com.holub.kyle.game.logic.deck.enums.Rank;
import com.holub.kyle.game.logic.deck.enums.Suit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.holub.kyle.assertions.CardAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class NpcPlayerTest {

    @Test
    void npcPlayerNameIsInToString() {
        NpcPlayer player = new NpcPlayer();

        player.setName("Bob");

        log.info(player.toString());
        assertThat(player.toString()).hasToString("Bob");
    }

    @Test
    void npcPlayerHasRandomNameByDefault() {
        NpcPlayer player = new NpcPlayer();

        log.info(player.toString());
        assertThat(player.toString()).isNotEmpty();
    }

    @Test
    void playerCardChoiceEnforcedByInitialCardSuit() {
        NpcPlayer player = new NpcPlayer();
        player.giveCards(List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.CLUBS), new Card(Rank.TWO, Suit.DIAMONDS)));

        Card playedCard = player.playCard(Suit.DIAMONDS, Suit.HEARTS);

        assertThat(playedCard).hasRank(Rank.TWO).hasSuit(Suit.DIAMONDS);
    }

}