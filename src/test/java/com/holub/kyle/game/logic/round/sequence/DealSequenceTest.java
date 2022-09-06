package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static com.holub.kyle.game.testutil.PlayerTestUtil.buildPlayerList;
import static org.assertj.core.api.Assertions.assertThat;

class DealSequenceTest {

    DealSequence dealSequence;

    @BeforeEach
    void setUp() {
        dealSequence = new DealSequence();
    }

    @ParameterizedTest
    @CsvSource({"1,3", "1,4", "1,5", "2,5", "3,5", "4,5"})
    void canDealCorrectNumberOfCardsBasedOnRoundNumber(int roundNumber, int numPlayers) {
        List<Player> players = buildPlayerList(numPlayers);

        dealSequence.dealCards(players, roundNumber);

        assertThat(players).flatExtracting(Player::getHand).hasSize(roundNumber * numPlayers);
    }

    @Test
    void eachPlayerDealt5CardsForRound5() {
        List<Player> players = buildPlayerList(5);

        dealSequence.dealCards(players, 5);

        assertThat(players.get(0).getHand()).hasSize(5);
        assertThat(players.get(1).getHand()).hasSize(5);
        assertThat(players.get(2).getHand()).hasSize(5);
        assertThat(players.get(3).getHand()).hasSize(5);
        assertThat(players.get(4).getHand()).hasSize(5);
    }


}