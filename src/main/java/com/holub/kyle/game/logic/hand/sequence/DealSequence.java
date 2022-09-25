package com.holub.kyle.game.logic.hand.sequence;

import com.holub.kyle.game.engine.Updateable;
import com.holub.kyle.game.logic.deck.Card;
import com.holub.kyle.game.logic.deck.Deck;
import com.holub.kyle.game.logic.player.PlayerQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DealSequence implements Updateable {

    private final Deck deck;
    private final PlayerQueue players;
    private final int numTricks;

    @Getter
    private boolean isDealing;
    private int numCardsDealt;

    public DealSequence(PlayerQueue players, int numTricks) {
        deck = new Deck();
        this.players = players;
        this.numTricks = numTricks;
        isDealing = true;
        log.info(String.format("Starting round with %s cards", numTricks));
    }

    public void update() {
        if (isDealing) {
            if (numCardsDealt < numTricks * players.numPlayers()) {
                players.getCurrentPlayer().giveCard(deck.drawCard());
                numCardsDealt++;
                players.nextPlayer();
            } else {
                isDealing = false;
            }
        }
    }

    public Card getTrumpCard() {
        return deck.drawCard();
    }
}
