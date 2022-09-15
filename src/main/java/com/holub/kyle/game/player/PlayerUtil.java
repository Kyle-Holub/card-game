package com.holub.kyle.game.player;

import java.util.List;
import java.util.stream.IntStream;

public class PlayerUtil {

    public static int getPlayerIndexLeftOfDealer(List<Player> players) {
        int dealerIndex = IntStream.range(0, players.size())
                .filter(i -> players.get(i).isDealer())
                .findFirst().orElse(0);
        return (dealerIndex + 1) % players.size();
    }
}
