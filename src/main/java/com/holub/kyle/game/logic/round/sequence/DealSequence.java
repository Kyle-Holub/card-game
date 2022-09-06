package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.deck.Deck;
import com.holub.kyle.player.Player;

import java.util.List;
import java.util.stream.IntStream;

public class DealSequence {

    private final Deck deck = new Deck();

    public void dealCards(List<Player> players, int roundNumber) {

        players.forEach(player -> giveCards(player, roundNumber));
    }

    private void giveCards(Player player, int roundNumber) {
        IntStream.range(0, roundNumber).forEach(i -> player.giveCard(deck.drawCard()));
    }
}
