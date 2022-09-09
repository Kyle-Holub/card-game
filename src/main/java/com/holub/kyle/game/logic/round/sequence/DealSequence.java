package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.Deck;
import com.holub.kyle.game.player.Player;

import java.util.List;
import java.util.stream.IntStream;

public class DealSequence {

    private final Deck deck = new Deck();

    public Card dealCards(List<Player> players, int numCardsToGive) {
        players.forEach(player -> giveCards(player, numCardsToGive));
        return deck.drawCard();
    }

    private void giveCards(Player player, int numCardsToGive) {
        IntStream.range(0, numCardsToGive).forEach(i -> player.giveCard(deck.drawCard()));
    }
}
