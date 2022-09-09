package com.holub.kyle.game.logic.round.sequence;

import org.junit.jupiter.api.BeforeEach;

class PlaySequenceTest {
    PlaySequence playSequence;

    @BeforeEach
    void setUp() {
        playSequence = new PlaySequence();
    }

//    @Test
//    void fivePlayersEachHaveAnEntryInTrickMap() {
//        List<Player> players = PlayerTestUtil.buildPlayerList(5);
//
//        Map<Player, Integer> trickMap = playSequence.playCards(players);
//
//        assertThat(trickMap).hasSize(5);
//
//
//    }
}