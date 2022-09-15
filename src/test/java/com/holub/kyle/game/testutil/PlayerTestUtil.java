package com.holub.kyle.game.testutil;

import com.holub.kyle.game.player.NpcPlayer;
import com.holub.kyle.game.player.PlayerQueue;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerTestUtil {

    public static PlayerQueue buildPlayerQueue(int numPlayers) {
        return new PlayerQueue(IntStream.range(0, numPlayers).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList()));
    }
}
