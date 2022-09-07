package com.holub.kyle.deck;

import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Card {
    private final Suit suit;
    private final Rank rank;

    public int getSuitValue() {
        return suit.getValue();
    }

    public int getRankValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }
}
