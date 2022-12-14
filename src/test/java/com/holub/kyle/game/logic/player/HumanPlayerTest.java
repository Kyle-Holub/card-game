package com.holub.kyle.game.logic.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class HumanPlayerTest extends AbstractPlayerTest {

    HumanPlayer player;

    @BeforeEach
    void setup() {
        player = new HumanPlayer(new Scanner(System.in));
    }

    @Override
    Player getPlayer() {
        return player;
    }

    @Test
    void humanPlayerCanSubmitABidOf0() {
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        player = new HumanPlayer(new Scanner(System.in));

        int bid = player.getBid();

        assertThat(bid).isZero();
    }
}