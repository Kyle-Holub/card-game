package com.holub.kyle.game.logic.round;

import com.holub.kyle.game.player.NpcPlayer;
import com.holub.kyle.game.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class RoundTest {
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6})
    void canInitRoundWithCorrectNumPlayers(int numPlayers) {
        List<Player> playerList = buildPlayerList(numPlayers);

        Round round = new Round(1, playerList);

        assertThat(round.getPlayers()).hasSize(numPlayers);
    }


    @Test
    void canPlayCards() {
        Round round = new Round(1, buildPlayerList(5));

        round.executeRound();

        assertThat(round.getPlayers().get(0).getHand()).isEmpty();
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
}