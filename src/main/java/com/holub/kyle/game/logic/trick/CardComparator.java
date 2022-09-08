package com.holub.kyle.game.logic.trick;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Suit;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CardComparator {
    public Card compare(List<Card> cards, Suit trumpSuit) {
        validateComparisonInput(cards);
        List<Card> trumpSuitCards = cards.stream().filter(isSuit(trumpSuit)).collect(Collectors.toList());
        if (trumpSuitCards.isEmpty()) {
            Suit initialSuit = cards.get(0).getSuit();
            List<Card> trickSuitCards = cards.stream().filter(isSuit(initialSuit)).collect(Collectors.toList());
            return compareAgainstSuitAndReturn(trickSuitCards);
        } else {
            return compareAgainstSuitAndReturn(trumpSuitCards);
        }
    }

    private static Card compareAgainstSuitAndReturn(List<Card> trumpSuitCards) {
        Optional<Card> winningCard = trumpSuitCards.stream().max(Card.getRankComparator());
        if (winningCard.isPresent()) {
            return winningCard.get();
        }
        throw new IllegalArgumentException(String.format("Unknown error - no winning card from: %s", trumpSuitCards));
    }

    private Predicate<Card> isSuit(Suit suit) {
        return card -> card.getSuit().equals(suit);
    }

    private static void validateComparisonInput(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalArgumentException("Null/empty card list unacceptable");
        }
        List<Card> duplicates = cards.stream()
                .collect(Collectors.groupingBy(card -> card.getRank() + "-" + card.getSuit(), Collectors.toList()))
                .values()
                .stream()
                .filter(i -> i.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        if (!duplicates.isEmpty()) {
            throw new IllegalArgumentException(String.format("Cards cannot be identical: %s", duplicates));
        }
    }
}
