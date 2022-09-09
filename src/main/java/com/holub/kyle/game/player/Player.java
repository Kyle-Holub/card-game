package com.holub.kyle.game.player;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.enums.Suit;
import com.holub.kyle.game.logic.trick.CardComparator;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Player {

    private final List<Card> hand = new ArrayList<>();
    private int score;
    @Setter
    private String name;

    public abstract int getBid();

    protected abstract Card playCardEnforced(List<Card> playableCards, Suit leadSuit, Suit trumpSuit);

    public Card playCard(Suit leadSuit, Suit trumpSuit) {
        return playCardEnforced(getPlayableCards(leadSuit), leadSuit, trumpSuit);
    }

    public void giveCard(Card newCard) {
        hand.add(newCard);
    }

    public void giveCards(List<Card> expectedCards) {
        hand.addAll(expectedCards);
    }

    @Override
    public String toString() {
        return name;
    }

    public void addScore(int score) {
        this.score += score;
    }

    protected List<Card> getPlayableCards(Suit leadSuit) {
        List<Card> cardsMatchingLeadSuit = CardComparator.filterSuit(hand, leadSuit);
        if (cardsMatchingLeadSuit.isEmpty()) {
            return hand;
        } else {
            return cardsMatchingLeadSuit;
        }
    }
}
