package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.deck.Deck;
import com.holub.kyle.game.deck.enums.Suit;
import com.holub.kyle.game.player.Player;
import com.holub.kyle.game.testutil.PlayerTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PlaySequenceTest {
    PlaySequence playSequence;

    @BeforeEach
    void setUp() {
        playSequence = new PlaySequence();
    }

    @Test
    void fivePlayersEachHaveAnEntryInTrickMap() {
        List<Player> players = PlayerTestUtil.buildPlayerList(5);
        Deck deck = new Deck();
        players.forEach(player -> player.giveCard(deck.drawCard()));

        Map<Player, Integer> trickMap = playSequence.playCards(players, 1, Suit.DIAMONDS);

        assertThat(trickMap).hasSize(5);
    }
}