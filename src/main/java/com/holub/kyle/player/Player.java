package com.holub.kyle.player;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Suit;
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

    public abstract Card playCard(Suit leadSuit, Suit trumpSuit);

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
}
