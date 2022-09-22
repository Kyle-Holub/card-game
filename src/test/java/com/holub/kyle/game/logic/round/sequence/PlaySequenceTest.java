package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.logic.deck.Deck;
import com.holub.kyle.game.logic.deck.enums.Suit;
import com.holub.kyle.game.logic.player.Player;
import com.holub.kyle.game.logic.player.PlayerQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.holub.kyle.game.testutil.PlayerTestUtil.buildPlayerQueue;
import static org.assertj.core.api.Assertions.assertThat;

class PlaySequenceTest {
    PlaySequence playSequence;

    @BeforeEach
    void setUp() {
        playSequence = new PlaySequence();
    }

    @Test
    void fivePlayersEachHaveAnEntryInTrickMap() {
        PlayerQueue players = buildPlayerQueue(5);
        Deck deck = new Deck();
        players.getDealerQ().forEach(player -> player.giveCard(deck.drawCard()));

        Map<Player, Integer> trickMap = playSequence.update(players, 1, Suit.DIAMONDS);

        assertThat(trickMap).hasSize(5);
    }
}