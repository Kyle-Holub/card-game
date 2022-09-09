package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static com.holub.kyle.game.testutil.PlayerTestUtil.buildPlayerList;
import static org.assertj.core.api.Assertions.assertThat;

class DealSequenceTest {

    DealSequence dealSequence;

    @ParameterizedTest
    @CsvSource({"1,3", "1,4", "1,5", "2,5", "3,5", "4,5"})
    void canDealCorrectNumberOfCardsBasedOnRoundNumber(int numCardsToGive, int numPlayers) {
        dealSequence = new DealSequence(numCardsToGive);
        List<Player> players = buildPlayerList(numPlayers);

        dealSequence.dealCards(players);

        assertThat(players).flatExtracting(Player::getHand).hasSize(numCardsToGive * numPlayers);
    }

    @Test
    void eachPlayerDealt5CardsForRound5() {
        dealSequence = new DealSequence(5);
        List<Player> players = buildPlayerList(5);

        dealSequence.dealCards(players);

        assertThat(players.get(0).getHand()).hasSize(5);
        assertThat(players.get(1).getHand()).hasSize(5);
        assertThat(players.get(2).getHand()).hasSize(5);
        assertThat(players.get(3).getHand()).hasSize(5);
        assertThat(players.get(4).getHand()).hasSize(5);
    }
}