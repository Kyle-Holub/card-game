package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.Deck;
import com.holub.kyle.game.player.PlayerQueue;

import java.util.stream.IntStream;

public class DealSequence {

    private final Deck deck;
    private final int numCardsToGive;

    public DealSequence(int numCardsToGive) {
        deck = new Deck();
        this.numCardsToGive = numCardsToGive;
    }

    public Card dealCards(PlayerQueue players) {
        IntStream.range(0, numCardsToGive).forEach(i -> giveEachPlayerCard(players));
        return deck.drawCard();
    }

    private void giveEachPlayerCard(PlayerQueue players) {
        players.getPlayerQ().forEach(player -> player.giveCard(deck.drawCard()));
    }
}
