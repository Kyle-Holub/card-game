//package com.holub.kyle.game.logic.hand.sequence;
//
//import com.holub.kyle.game.logic.player.NpcPlayer;
//import com.holub.kyle.game.logic.player.Player;
//import com.holub.kyle.game.logic.player.PlayerQueue;
//import org.junit.jupiter.api.Test;
//
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class BidSequenceTest {
//
//    @Test
//    void canTakeBids() {
//        PlayerQueue players = buildMockPlayerQueue(5);
//        BidSequence bidSequence = new BidSequence(players, 8);
//
//        Map<Player, Integer> bidMap = bidSequence.update();
//
//        assertThat(bidMap).containsValue(1);
//    }
//
//    @Test
//    void lastPlayerHasCatch() {
//        PlayerQueue players = buildMockPlayerQueue(3);
//        BidSequence bidSequence = new BidSequence(players, 3);
//
//        Map<Player, Integer> bidMap = bidSequence.update();
//
//        assertThat(bidMap).containsValues(1, 1, 0);
//    }
//
//    private PlayerQueue buildMockPlayerQueue(int numPlayers) {
//        return new PlayerQueue(IntStream.range(0, numPlayers).mapToObj(this::newMockPlayer).collect(Collectors.toList()));
//    }
//
//    private NpcPlayer newMockPlayer(int i) {
//        NpcPlayer mockPlayer = mock(NpcPlayer.class);
//        when(mockPlayer.getName()).thenReturn("Mock-" + i);
//        when(mockPlayer.getBid()).thenReturn(1);
//        return mockPlayer;
//    }
//}