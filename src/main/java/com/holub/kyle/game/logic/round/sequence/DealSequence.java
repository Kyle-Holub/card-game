package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.Deck;
import com.holub.kyle.game.player.Player;

import java.util.List;
import java.util.stream.IntStream;

public class DealSequence {

    private final Deck deck;
    private final int numCardsToGive;
    public DealSequence(int numCardsToGive) {
        deck = new Deck();
        this.numCardsToGive = numCardsToGive;
    }
    public Card dealCards(List<Player> players) {
        players.forEach(this::giveCards);
        return deck.drawCard();
    }

    private void giveCards(Player player) {
        IntStream.range(0, numCardsToGive).forEach(i -> player.giveCard(deck.drawCard()));
    }
}
