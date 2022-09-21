package com.holub.kyle.game.logic.round;

import com.holub.kyle.game.logic.player.PlayerQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.holub.kyle.game.testutil.PlayerTestUtil.buildPlayerQueue;
import static org.assertj.core.api.Assertions.assertThat;

class RoundTest {
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6})
    void canInitRoundWithCorrectNumPlayers(int numPlayers) {
        PlayerQueue playerList = buildPlayerQueue(numPlayers);

        Round round = new Round(1, playerList);

        assertThat(round.getPlayers().getPlayerQ()).hasSize(numPlayers);
    }


    @Test
    void canPlayCards() {
        Round round = new Round(1, buildPlayerQueue(5));

        round.executeRound();

        assertThat(round.getPlayers().getCurrentPlayer().getHand()).isEmpty();
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
}