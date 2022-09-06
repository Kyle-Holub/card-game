package com.holub.kyle.deck;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.Deck;
import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void deckHasAllFourSuits() {
        List<Card> cards = IntStream.range(0, 52).mapToObj(i -> deck.drawCard()).collect(Collectors.toList());

        assertThat(cards).extracting(Card::getSuit).contains(Suit.values());
    }

    @Test
    void deckHasAllRankValues() {
        List<Card> cards = IntStream.range(0, 52).mapToObj(i -> deck.drawCard()).collect(Collectors.toList());

        assertThat(cards).extracting(Card::getRank).contains(Rank.values());
    }

    @Test
    void drawCard52TimesRemovesAllCards() {
        IntStream.range(0, 52).forEach(i -> deck.drawCard());

        assertThat(deck.isEmpty()).isTrue();
    }

    @Test
    void drawingMoreThan52CardsThrowsAnError() {
        IntStream range = IntStream.range(0, 53);

        assertThrows(IndexOutOfBoundsException.class, () -> range.forEach(i -> deck.drawCard()));
    }
}