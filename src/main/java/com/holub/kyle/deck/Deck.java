package com.holub.kyle.deck;

import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Deck {

    private final List<Card> cards;

    public Deck() {
        this.cards = Arrays.stream(Suit.values()).flatMap(Deck::mapSuitToRanks).collect(Collectors.toList());
        Collections.shuffle(cards);
    }

    private static Stream<Card> mapSuitToRanks(Suit suit) {
        return Arrays.stream(Rank.values()).map(rank -> new Card(suit, rank));
    }

    public Card drawCard() {
        Card card = cards.get(0);
        cards.remove(card);
        return card;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
