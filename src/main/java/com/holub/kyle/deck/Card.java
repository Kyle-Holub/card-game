package com.holub.kyle.deck;

import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@Getter
@RequiredArgsConstructor
public class Card {
    private final Rank rank;
    private final Suit suit;

    private static final Comparator<Card> RANK_COMPARATOR = Comparator.comparingInt(c -> c.getRank().getValue());

    public static Comparator<Card> getRankComparator() {
        return RANK_COMPARATOR;
    }

    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }
}
