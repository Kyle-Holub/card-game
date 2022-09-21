package com.holub.kyle.game.neural.observation;

// input to neural network
public class Observation {

    // is bid stage (0,1)
    private int isBidStage;
    // num available tricks in round (1-8)
    private int numAvailableTricks;
    // trump suit (0-3)
    private int trumpSuit;
    // total bid by all players (0-inf)
    private int totalBids;
    // agent bid (0-8)
    private int agentBid;

    // is dealer (0,1)
    private int isDealer;
    // is lead player (0,1)
    private int isLeadPlayer;
    // lead suit (0-4 where 4 no lead suit)
    private int leadSuit;

    // agent cards in hand (52 cards)
    private double[] cardsPlayed;

    /* OPPONENT INFO */

    // all opponent played cards for a hand (52 cards)
    private double[] opponentPlayedCards;
}
