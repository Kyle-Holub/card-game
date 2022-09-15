package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.player.NpcPlayer;
import com.holub.kyle.game.player.Player;
import com.holub.kyle.game.player.PlayerQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreSequenceTest {

    ScoreSequence scoreSequence;

    @BeforeEach
    void setUp() {
        scoreSequence = new ScoreSequence();
    }

    @Nested
    class MultiplePlayerTests {
        @Test
        void givenTwoPlayers_whenBidZeroAndZeroTricks_scoreShouldBeTenEach() {
            List<Player> players = IntStream.range(0, 2).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());
            Map<Player, Integer> bids = Map.of(players.get(0), 0, players.get(1), 0);
            Map<Player, Integer> tricks = Map.of(players.get(0), 0, players.get(1), 0);
            PlayerQueue playerQueue = new PlayerQueue(players);

            scoreSequence.tallyScores(playerQueue, bids, tricks);

            assertThat(players).extracting(Player::getScore).containsExactly(10, 10);
        }

        @Test
        void givenTwoPlayers_OneBidCorrectOneBidIncorrect_expectedScoreCalculated() {
            List<Player> players = IntStream.range(0, 2).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());
            Map<Player, Integer> bids = Map.of(players.get(0), 1, players.get(1), 1);
            Map<Player, Integer> tricks = Map.of(players.get(0), 1, players.get(1), 0);
            PlayerQueue playerQueue = new PlayerQueue(players);

            scoreSequence.tallyScores(playerQueue, bids, tricks);

            assertThat(players.get(0).getScore()).isEqualTo(12);
            assertThat(players.get(1).getScore()).isEqualTo(-5);

        }
    }

    @Nested
    class SinglePlayerTests {
        @ParameterizedTest
        @CsvSource({"0, 0", "1, 1", "2, 2", "3, 3", "4, 4", "5, 5", "6, 6"})
        void whenBidCorrectNumTricks_scoreShouldBeTenPlusTwoTimesBid(int bid, int numTricks) {
            List<Player> players = IntStream.range(0, 2).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());
            Map<Player, Integer> bids = Map.of(players.get(0), bid, players.get(1), bid);
            Map<Player, Integer> tricks = Map.of(players.get(0), numTricks, players.get(1), numTricks);
            PlayerQueue playerQueue = new PlayerQueue(players);

            scoreSequence.tallyScores(playerQueue, bids, tricks);

            assertThat(players.get(0).getScore()).isEqualTo(10 + numTricks * 2);
        }

        @ParameterizedTest
        @CsvSource({"1, 0", "2, 0", "3, 0", "2, 1", "3, 1", "4, 1", "5, 2"})
        void whenBidOver_scoreShouldBeNegativeFiveForEach(int bid, int numTricks) {
            List<Player> players = IntStream.range(0, 2).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());
            Map<Player, Integer> bids = Map.of(players.get(0), bid, players.get(1), bid);
            Map<Player, Integer> tricks = Map.of(players.get(0), numTricks, players.get(1), numTricks);
            PlayerQueue playerQueue = new PlayerQueue(players);

            scoreSequence.tallyScores(playerQueue, bids, tricks);

            assertThat(players.get(0).getScore()).isEqualTo(-5 * (bid - numTricks));
        }

        @ParameterizedTest
        @CsvSource({"0, 1", "0, 2", "0, 3", "1, 2", "1, 3", "1, 4", "2, 5"})
        void whenBidUnder_scoreShouldBeNegativeFiveForEach(int bid, int numTricks) {
            List<Player> players = IntStream.range(0, 2).mapToObj(i -> new NpcPlayer()).collect(Collectors.toList());
            Map<Player, Integer> bids = Map.of(players.get(0), bid, players.get(1), bid);
            Map<Player, Integer> tricks = Map.of(players.get(0), numTricks, players.get(1), numTricks);
            PlayerQueue playerQueue = new PlayerQueue(players);

            scoreSequence.tallyScores(playerQueue, bids, tricks);

            assertThat(players.get(0).getScore()).isEqualTo(-5 * (numTricks - bid));
        }
    }

}