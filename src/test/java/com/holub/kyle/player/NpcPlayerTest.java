package com.holub.kyle.player;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class NpcPlayerTest {

    @Test
    void npcPlayerNameIsInToString() {
        NpcPlayer player = new NpcPlayer();

        player.setName("Bob");

        log.info(player.toString());
        assertThat(player.toString()).isEqualTo("Bob");
    }

    @Test
    void npcPlayerHasRandomName() {
        List<NpcPlayer> players = IntStream.range(0, 20).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());

        players.forEach(player -> log.info(player.toString()));
        assertThat(players).extracting(Player::toString).isNotEmpty();
    }

}