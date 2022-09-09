package com.holub.kyle.game.logic.round;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.logic.round.sequence.BidSequence;
import com.holub.kyle.game.logic.round.sequence.DealSequence;
import com.holub.kyle.game.logic.round.sequence.PlaySequence;
import com.holub.kyle.game.logic.round.sequence.ScoreSequence;
import com.holub.kyle.game.player.Player;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class Round {

    private final int numTricks;
    private final List<Player> players;

    private Map<Player, Integer> bidMap;
    private Map<Player, Integer> trickMap;

    public Round(int numTricks, List<Player> newPlayers) {
        this.numTricks = numTricks;
        players = newPlayers;
        bidMap = new HashMap<>();
        trickMap = new HashMap<>();
    }

    public void executeRound() {
        log.info(String.format("Starting round with %s cards", numTricks));
        DealSequence dealSequence = new DealSequence();
        Card trumpCard = dealSequence.dealCards(players, numTricks);

        BidSequence bidSequence = new BidSequence(players);
        bidMap = bidSequence.takeBids();

        PlaySequence playSequence = new PlaySequence();
        trickMap = playSequence.playCards(players, numTricks, trumpCard);

        ScoreSequence scoreSequence = new ScoreSequence();
        scoreSequence.tallyScores(players, bidMap, trickMap);
    }
}
