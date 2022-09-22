package com.holub.kyle.game.logic.trick;

import com.holub.kyle.game.logic.deck.Card;
import com.holub.kyle.game.logic.deck.enums.Suit;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CardComparator {
    public Card compare(List<Card> cards, Suit trumpSuit) {
        validateComparisonInput(cards);
        List<Card> trumpSuitCards = filterSuit(cards, trumpSuit);
        if (trumpSuitCards.isEmpty()) {
            Suit initialSuit = cards.get(0).getSuit();
            List<Card> trickSuitCards = filterSuit(cards, initialSuit);
            return compareAgainstSuitAndReturn(trickSuitCards);
        } else {
            return compareAgainstSuitAndReturn(trumpSuitCards);
        }
    }

    public static List<Card> filterSuit(List<Card> cardList, Suit leadSuit) {
        return cardList.stream().filter(CardComparator.filterSuit(leadSuit)).collect(Collectors.toList());
    }

    private static Predicate<Card> filterSuit(Suit suit) {
        return card -> card.getSuit().equals(suit);
    }

    private static Card compareAgainstSuitAndReturn(List<Card> trumpSuitCards) {
        Optional<Card> winningCard = trumpSuitCards.stream().max(Card.getRankComparator());
        if (winningCard.isPresent()) {
            return winningCard.get();
        }
        throw new IllegalArgumentException(String.format("Unknown error - no winning card from: %s", trumpSuitCards));
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
