package com.holub.kyle.deck.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {
    ACE(12), TWO(0), THREE(1), FOUR(2), FIVE(3), SIX(4), SEVEN(5),
    EIGHT(6), NINE(7), TEN(8), JACK(9), QUEEN(10), KING(11);

    private final int value;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
