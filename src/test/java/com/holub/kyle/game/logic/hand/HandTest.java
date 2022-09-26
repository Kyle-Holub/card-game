//package com.holub.kyle.game.logic.hand;
//
//import com.holub.kyle.game.logic.player.PlayerQueue;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import static com.holub.kyle.game.testutil.PlayerTestUtil.buildPlayerQueue;
//import static org.assertj.core.api.Assertions.assertThat;
//
//class HandTest {
//    @ParameterizedTest
//    @ValueSource(ints = {3, 4, 5, 6})
//    void canInitRoundWithCorrectNumPlayers(int numPlayers) {
//        PlayerQueue playerList = buildPlayerQueue(numPlayers);
//
//        Hand hand = new Hand(1, playerList);
//
//        assertThat(hand.getPlayers().getPlayerQ()).hasSize(numPlayers);
//    }
//
//
//    @Test
//    void canPlayCards() {
//        Hand hand = new Hand(1, buildPlayerQueue(5));
//
//        hand.executeRound();
//
//        assertThat(hand.getPlayers().getCurrentPlayer().getHand()).isEmpty();
//    }
//
////    @Test
////    void canFindWinnerOfTrickIfPlayer1OutOfTwoIsWinner() {
////        List<Player> mockPlayerList = buildMockPlayerList(2);
////        when(mockPlayerList.get(0).playCard()).thenReturn(new Card(Suit.DIAMONDS, Rank.ACE));
////        when(mockPlayerList.get(1).playCard()).thenReturn(new Card(Suit.CLUBS, Rank.TWO));
////        Round round = new Round(1, mockPlayerList);
////
////        round.executeRound();
////
////        assertThat(round.getWinner()).isEqualTo(mockPlayerList.get(0));
////    }
//
////    @Test
////    void canFindWinnerIfPlayer2OutOfTwoIsWinner() {
////        List<Player> mockPlayerList = buildMockPlayerList(2);
////        when(mockPlayerList.get(1).playCard()).thenReturn(new Card(Suit.DIAMONDS, Rank.ACE));
////        when(mockPlayerList.get(0).playCard()).thenReturn(new Card(Suit.CLUBS, Rank.TWO));
////        Round round = new Round(1, mockPlayerList);
////
////        round.executeRound();
////
////        assertThat(round.getWinner()).isEqualTo(mockPlayerList.get(1));
////    }
//
////    @Test
////    void canFindWinnerIfPlayerThreeOutOfThreeIsWinner() {
////        List<Player> mockPlayerList = buildMockPlayerList(3);
////        when(mockPlayerList.get(2).playCard()).thenReturn(new Card(Suit.DIAMONDS, Rank.ACE));
////        when(mockPlayerList.get(1).playCard()).thenReturn(new Card(Suit.CLUBS, Rank.TWO));
////        when(mockPlayerList.get(0).playCard()).thenReturn(new Card(Suit.CLUBS, Rank.THREE));
////        Round round = new Round(1, mockPlayerList);
////
////        round.executeRound();
////
////        assertThat(round.getWinner()).isEqualTo(mockPlayerList.get(2));
////    }
//}