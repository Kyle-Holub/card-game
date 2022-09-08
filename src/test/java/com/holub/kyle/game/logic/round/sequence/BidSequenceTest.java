package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.player.NpcPlayer;
import com.holub.kyle.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BidSequenceTest {

    @Test
    void canTakeBids() {
        List<Player> players = buildMockPlayerList(5);
        BidSequence bidSequence = new BidSequence(players);

        Map<Player, Integer> bidMap = bidSequence.takeBids();

        assertThat(bidMap).containsValue(1);
    }

    private List<Player> buildMockPlayerList(int numPlayers) {
        return IntStream.range(0, numPlayers).mapToObj(this::newMockPlayer).collect(Collectors.toList());

    }

    private NpcPlayer newMockPlayer(int i) {
        NpcPlayer mockPlayer = mock(NpcPlayer.class);
        when(mockPlayer.getName()).thenReturn("Mock-" + i);
        when(mockPlayer.getBid()).thenReturn(1);
        return mockPlayer;
    }
}