package com.holub.kyle.game.logic;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.enums.Rank;
import com.holub.kyle.game.deck.enums.Suit;
import com.holub.kyle.game.logic.trick.CardComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.holub.kyle.assertions.CardAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardComparatorTest {
    CardComparator comparator;

    @BeforeEach()
    void setUp() {
        comparator = new CardComparator();
    }

    @Nested
    class RankTests {
        @Test
        void aceIsHigherThanKing() {
            Card ace = new Card(Rank.ACE, Suit.CLUBS);
            Card king = new Card(Rank.KING, Suit.CLUBS);

            Card winner = comparator.compare(List.of(ace, king), Suit.HEARTS);

            assertThat(winner).hasRank(Rank.ACE);
        }

        @Test
        void kingIsHigherThanQueen() {
            Card king = new Card(Rank.KING, Suit.CLUBS);
            Card queen = new Card(Rank.QUEEN, Suit.CLUBS);

            Card winner = comparator.compare(List.of(king, queen), Suit.DIAMONDS);

            assertThat(winner).hasRank(Rank.KING);
        }

        @Test
        void kingHigherThanQueenAsSecondListItem() {
            Card queen = new Card(Rank.QUEEN, Suit.CLUBS);
            Card king = new Card(Rank.KING, Suit.CLUBS);

            Card winner = comparator.compare(List.of(queen, king), Suit.DIAMONDS);

            assertThat(winner).hasRank(Rank.KING);
        }

        @Test
        void kingIsHigherThanTwo() {
            Card king = new Card(Rank.KING, Suit.CLUBS);
            Card two = new Card(Rank.TWO, Suit.CLUBS);

            Card winner = comparator.compare(List.of(king, two), Suit.DIAMONDS);

            assertThat(winner).hasRank(Rank.KING);
        }

        @Test
        void firstCardWinsGivenOnlySuitWithNoTrumpSuits() {
            Card jackOfClubs = new Card(Rank.JACK, Suit.CLUBS);
            Card queenOfDiamonds = new Card(Rank.QUEEN, Suit.DIAMONDS);
            Card aceOfSpades = new Card(Rank.ACE, Suit.SPADES);

            Card highestCard = comparator.compare(List.of(jackOfClubs, queenOfDiamonds, aceOfSpades), Suit.HEARTS);

            assertThat(highestCard).hasRank(Rank.JACK).hasSuit(Suit.CLUBS);
        }
    }

    @Nested
    class TrumpTests {
        @Test
        void singleTrumpCardWillWinComparison() {
            Card nonTrumpSuitCard = new Card(Rank.ACE, Suit.HEARTS);
            Card trumpSuitCard = new Card(Rank.THREE, Suit.SPADES);

            Card highestCard = comparator.compare(List.of(nonTrumpSuitCard, trumpSuitCard), Suit.SPADES);

            assertThat(highestCard).hasRank(Rank.THREE).hasSuit(Suit.SPADES);
        }

        @Test
        void highestTrumpWinsComparison() {
            Card regularCard = new Card(Rank.TWO, Suit.DIAMONDS);
            Card secondHighestTrumpSuitCard = new Card(Rank.TEN, Suit.SPADES);
            Card highestTrumpSuitCard = new Card(Rank.ACE, Suit.SPADES);
            List<Card> cardList = List.of(regularCard, highestTrumpSuitCard, secondHighestTrumpSuitCard);

            Card highestCard = comparator.compare(cardList, Suit.SPADES);

            assertThat(highestCard).hasRank(Rank.ACE).hasSuit(Suit.SPADES);
        }

        @Test
        void leadTrumpWinsComparison() {
            Card highestTrumpSuitCard = new Card(Rank.QUEEN, Suit.HEARTS);
            Card regularCard = new Card(Rank.TWO, Suit.DIAMONDS);
            Card secondHighestTrumpSuitCard = new Card(Rank.TEN, Suit.HEARTS);
            List<Card> cardList = List.of(secondHighestTrumpSuitCard, regularCard, highestTrumpSuitCard);

            Card highestCard = comparator.compare(cardList, Suit.HEARTS);

            assertThat(highestCard).hasRank(Rank.QUEEN).hasSuit(Suit.HEARTS);
        }
    }

    @Nested
    class ExceptionTests {
        @Test
        void identicalCardsThrowsException() {
            List<Card> duplicateCardList = List.of(new Card(Rank.TWO, Suit.DIAMONDS), new Card(Rank.TWO, Suit.DIAMONDS));

            assertThatThrownBy(() -> comparator.compare(duplicateCardList, Suit.DIAMONDS))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(new Card(Rank.TWO, Suit.DIAMONDS).toString());
        }
    }
}