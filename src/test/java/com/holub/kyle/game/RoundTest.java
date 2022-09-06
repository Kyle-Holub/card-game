package com.holub.kyle.game;

import com.holub.kyle.game.logic.round.Round;
import com.holub.kyle.player.NpcPlayer;
import com.holub.kyle.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class RoundTest {
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6})
    void canInitRoundWithCorrectNumPlayers(int numPlayers) {
        List<Player> playerList = buildPlayerList(numPlayers);

        Round round = new Round(1, playerList);

        assertThat(round.getPlayers()).hasSize(numPlayers);
    }

    @ParameterizedTest
    @CsvSource({"1,3", "1,4", "1,5", "2,5", "3,5", "4,5"})
    void canDealCorrectNumberOfCardsBasedOnRoundNumber(int roundNumber, int numPlayers) {
        Round round = new Round(roundNumber, buildPlayerList(numPlayers));

        round.executeRound();

        assertThat(round.getPlayers()).flatExtracting(Player::getHand).hasSize(roundNumber * numPlayers);
    }

    @Test
    void eachPlayerDealt5CardsForRound5() {
        Round round = new Round(5, buildPlayerList(5));

        round.executeRound();

        assertThat(round.getPlayers().get(0).getHand()).hasSize(5);
        assertThat(round.getPlayers().get(1).getHand()).hasSize(5);
        assertThat(round.getPlayers().get(2).getHand()).hasSize(5);
        assertThat(round.getPlayers().get(3).getHand()).hasSize(5);
        assertThat(round.getPlayers().get(4).getHand()).hasSize(5);
    }

    @Test
    void canTakeBids() {
        Round round = new Round(1, buildMockPlayerList(5));

        round.executeRound();

        assertThat(round.getBidMap()).containsValue(1);
    }

    @Test
    void canPlayCards() {
        Round round = new Round(1, buildPlayerList(5));

        round.executeRound();

        assertThat(round.getPlayedCardsMap()).hasSize(5);
    }

//    @Test
//    void canFindWinnerOfTrickIfPlayer1OutOfTwoIsWinner() {
//        List<Player> mockPlayerList = buildMockPlayerList(2);
//        when(mockPlayerList.get(0).playCard()).thenReturn(new Card(Suit.DIAMONDS, Rank.ACE));
//        when(mockPlayerList.get(1).playCard()).thenReturn(new Card(Suit.CLUBS, Rank.TWO));
//        Round round = new Round(1, mockPlayerList);
//
//        round.executeRound();
//
//        assertThat(round.getWinner()).isEqualTo(mockPlayerList.get(0));
//    }

//    @Test
//    void canFindWinnerIfPlayer2OutOfTwoIsWinner() {
//        List<Player> mockPlayerList = buildMockPlayerList(2);
//        when(mockPlayerList.get(1).playCard()).thenReturn(new Card(Suit.DIAMONDS, Rank.ACE));
//        when(mockPlayerList.get(0).playCard()).thenReturn(new Card(Suit.CLUBS, Rank.TWO));
//        Round round = new Round(1, mockPlayerList);
//
//        round.executeRound();
//
//        assertThat(round.getWinner()).isEqualTo(mockPlayerList.get(1));
//    }

//    @Test
//    void canFindWinnerIfPlayerThreeOutOfThreeIsWinner() {
//        List<Player> mockPlayerList = buildMockPlayerList(3);
//        when(mockPlayerList.get(2).playCard()).thenReturn(new Card(Suit.DIAMONDS, Rank.ACE));
//        when(mockPlayerList.get(1).playCard()).thenReturn(new Card(Suit.CLUBS, Rank.TWO));
//        when(mockPlayerList.get(0).playCard()).thenReturn(new Card(Suit.CLUBS, Rank.THREE));
//        Round round = new Round(1, mockPlayerList);
//
//        round.executeRound();
//
//        assertThat(round.getWinner()).isEqualTo(mockPlayerList.get(2));
//    }

    private List<Player> buildPlayerList(int numPlayers) {
        return IntStream.range(0, numPlayers).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());
    }

    private List<Player> buildMockPlayerList(int numPlayers) {
        return IntStream.range(0, numPlayers).mapToObj(RoundTest::newMockPlayer).collect(Collectors.toList());

    }

    private static NpcPlayer newMockPlayer(int i) {
        NpcPlayer mockPlayer = new NpcPlayer();
        mockPlayer.setName("Mock-" + i);
        mockPlayer = spy(mockPlayer);
        when(mockPlayer.getBid()).thenReturn(1);
        return mockPlayer;
    }
}