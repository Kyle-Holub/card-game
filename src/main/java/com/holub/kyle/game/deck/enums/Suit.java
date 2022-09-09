package com.holub.kyle.game.deck.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Suit {
    CLUBS(0), SPADES(1), HEARTS(2), DIAMONDS(3);

    private final int value;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
