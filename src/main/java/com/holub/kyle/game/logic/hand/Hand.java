package com.holub.kyle.game.logic.hand;

import com.holub.kyle.game.engine.Updateable;
import com.holub.kyle.game.logic.deck.Card;
import com.holub.kyle.game.logic.hand.sequence.BidSequence;
import com.holub.kyle.game.logic.hand.sequence.DealSequence;
import com.holub.kyle.game.logic.hand.sequence.PlaySequence;
import com.holub.kyle.game.logic.hand.sequence.ScoreSequence;
import com.holub.kyle.game.logic.player.Player;
import com.holub.kyle.game.logic.player.PlayerQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class Hand implements Updateable {

    private final PlayerQueue players;
    private int numTricks;

    private Card trumpCard;
    private Map<Player, Integer> bidMap;
    private Map<Player, Integer> trickMap;

    private boolean isHandOver;
    private DealSequence dealSequence;
    private BidSequence bidSequence;
    private PlaySequence playSequence;
    private ScoreSequence scoreSequence;

    public Hand(PlayerQueue newPlayers) {
        players = newPlayers;
        newHand(1);
    }

    @Override
    public void update() {
        if (dealSequence.isDealing()) {
            dealSequence.quickUpdate();
            if (!dealSequence.isDealing()) {
                log.info("dealing complete");
                trumpCard = dealSequence.getTrumpCard();
                playSequence.setTrumpSuit(trumpCard.getSuit());
            }
        } else if (bidSequence.isBidding()) {
            bidSequence.quickUpdate();
            if (!bidSequence.isBidding()) {
                log.info("bidding complete");
                bidMap = bidSequence.getBidMap();
            }
        } else if (playSequence.isPlaying()) {
            playSequence.update();
            if (!playSequence.isPlaying()) {
                log.info("playing complete");
                trickMap = playSequence.getTrickMap();
            }
        } else {
            scoreSequence.updateScores(players, bidMap, trickMap);
            isHandOver = true;
        }
    }

    public void newHand(int newNumTricks) {
        isHandOver = false;
        numTricks = newNumTricks;
        bidMap = new HashMap<>();
        trickMap = new HashMap<>();
        dealSequence = new DealSequence(players, newNumTricks);
        bidSequence = new BidSequence(players, newNumTricks);
        playSequence = new PlaySequence(players, newNumTricks);
        scoreSequence = new ScoreSequence();
    }
}
