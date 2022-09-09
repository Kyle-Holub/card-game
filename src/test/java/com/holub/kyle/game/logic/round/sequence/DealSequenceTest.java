package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InOrder;

import java.util.List;

import static com.holub.kyle.game.testutil.PlayerTestUtil.buildPlayerList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DealSequenceTest {

    @ParameterizedTest
    @CsvSource({"1,3", "1,4", "1,5", "2,5", "3,5", "4,5"})
    void canDealCorrectNumberOfCardsBasedOnNumCardsToGive(int numCardsToGive, int numPlayers) {
        DealSequence dealSequence = new DealSequence(numCardsToGive);
        List<Player> players = buildPlayerList(numPlayers);

        dealSequence.dealCards(players);

        assertThat(players).flatExtracting(Player::getHand).hasSize(numCardsToGive * numPlayers);
    }

    @Test
    void dealerGivenCardsLast() {
        DealSequence dealSequence = new DealSequence(1);
        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);
        when(mockPlayer2.isDealer()).thenReturn(true);
        List<Player> playerList = List.of(mockPlayer1, mockPlayer2);
        InOrder dealerOrder = inOrder(mockPlayer1, mockPlayer2);

        dealSequence.dealCards(playerList);

        dealerOrder.verify(mockPlayer1).giveCard(any());
        dealerOrder.verify(mockPlayer2).giveCard(any());
    }

    @Test
    void givenFiveCardsToGive_eachPlayerDealtFiveCards() {
        DealSequence dealSequence = new DealSequence(5);
        List<Player> players = buildPlayerList(5);

        dealSequence.dealCards(players);

        assertThat(players.get(0).getHand()).hasSize(5);
        assertThat(players.get(1).getHand()).hasSize(5);
        assertThat(players.get(2).getHand()).hasSize(5);
        assertThat(players.get(3).getHand()).hasSize(5);
        assertThat(players.get(4).getHand()).hasSize(5);
    }
}