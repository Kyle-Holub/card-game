package com.holub.kyle.game.logic.round.sequence;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.Deck;
import com.holub.kyle.game.player.Player;

import java.util.List;
import java.util.stream.IntStream;

import static com.holub.kyle.game.player.PlayerUtil.getPlayerIndexLeftOfDealer;

public class DealSequence {

    private final Deck deck;
    private final int numCardsToGive;

    public DealSequence(int numCardsToGive) {
        deck = new Deck();
        this.numCardsToGive = numCardsToGive;
    }

    public Card dealCards(List<Player> players) {
        IntStream.range(0, numCardsToGive).forEach(i -> giveEachPlayerCard(players));
        return deck.drawCard();
    }

    private void giveEachPlayerCard(List<Player> players) {
        int numPlayers = players.size();
        for (int index = getPlayerIndexLeftOfDealer(players), dealtCount = 0; dealtCount < numPlayers; index = (index + 1) % numPlayers, dealtCount++) {
            players.get(index).giveCard(deck.drawCard());
        }
    }
}
