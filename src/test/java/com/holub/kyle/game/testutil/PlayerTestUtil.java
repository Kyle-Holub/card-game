package com.holub.kyle.game.testutil;

import com.holub.kyle.game.player.NpcPlayer;
import com.holub.kyle.game.player.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerTestUtil {

    public static List<Player> buildPlayerList(int numPlayers) {
        return IntStream.range(0, numPlayers).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());
    }
}
