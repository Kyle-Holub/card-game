package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.player.Player;
import com.holub.kyle.game.player.PlayerQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InOrder;

import java.util.LinkedList;
import java.util.List;

import static com.holub.kyle.game.testutil.PlayerTestUtil.buildPlayerQueue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

class DealSequenceTest {

    @ParameterizedTest
    @CsvSource({"1,3", "1,4", "1,5", "2,5", "3,5", "4,5"})
    void canDealCorrectNumberOfCardsBasedOnNumCardsToGive(int numCardsToGive, int numPlayers) {
        DealSequence dealSequence = new DealSequence(numCardsToGive);
        PlayerQueue players = buildPlayerQueue(numPlayers);

        dealSequence.dealCards(players);

        assertThat(players.getPlayerQ()).flatExtracting(Player::getHand).hasSize(numCardsToGive * numPlayers);
    }

    @Test
    void dealerGivenCardsLast() {
        DealSequence dealSequence = new DealSequence(1);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        List<Player> playerList = List.of(player1, player2);
        PlayerQueue playerQueue = new PlayerQueue(playerList);
        playerQueue.setPlayerQ(new LinkedList<>(playerList));
        InOrder dealerOrder = inOrder(player1, player2);

        dealSequence.dealCards(playerQueue);

        dealerOrder.verify(player1).giveCard(any());
        dealerOrder.verify(player2).giveCard(any());
    }

    @Test
    void givenFiveCardsToGive_eachPlayerDealtFiveCards() {
        DealSequence dealSequence = new DealSequence(5);
        PlayerQueue players = buildPlayerQueue(5);

        dealSequence.dealCards(players);

        players.getDealerQ().forEach(player -> assertThat(player.getHand()).hasSize(5));
    }
}