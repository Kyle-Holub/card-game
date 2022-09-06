package com.holub.kyle.deck;

import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
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
}
