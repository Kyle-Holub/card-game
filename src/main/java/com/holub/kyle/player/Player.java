package com.holub.kyle.player;

import com.holub.kyle.deck.Card;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Player {

    private final List<Card> hand = new ArrayList<>();

    @Setter
    private String name;

    public abstract int getBid();

    public abstract Card playCard();

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
}
