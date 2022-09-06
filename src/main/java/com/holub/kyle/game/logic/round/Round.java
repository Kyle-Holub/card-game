package com.holub.kyle.game.logic.round;

import com.holub.kyle.game.logic.round.sequence.BidSequence;
import com.holub.kyle.game.logic.round.sequence.DealSequence;
import com.holub.kyle.game.logic.round.sequence.PlaySequence;
import com.holub.kyle.game.logic.round.sequence.ScoreSequence;
import com.holub.kyle.player.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Round {

    private final int roundNumber;
    private final List<Player> players;

    private Map<Player, Integer> bidMap;
    private Map<Player, Integer> trickMap;
    private Player winner;

    public Round(int newRoundNumber, List<Player> newPlayers) {
        roundNumber = newRoundNumber;
        players = newPlayers;
        bidMap = new HashMap<>();
        trickMap = new HashMap<>();
    }

    public void executeRound() {
        DealSequence dealSequence = new DealSequence();
        dealSequence.dealCards(players, roundNumber);

        BidSequence bidSequence = new BidSequence(players);
        bidMap = bidSequence.takeBids();

        PlaySequence playSequence = new PlaySequence();
        trickMap = playSequence.playCards(players);

        ScoreSequence scoreSequence = new ScoreSequence();
        scoreSequence.tallyScores(players, bidMap, trickMap);
    }
}
