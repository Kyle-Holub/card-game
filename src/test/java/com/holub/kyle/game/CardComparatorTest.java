package com.holub.kyle.game;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Rank;
import com.holub.kyle.deck.enums.Suit;
import com.holub.kyle.game.logic.trick.CardComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
        void aceIsHigherThanKingAsFirstArg() {
            Card ace = new Card(Suit.CLUBS, Rank.ACE);
            Card king = new Card(Suit.CLUBS, Rank.KING);

            Card winner = comparator.compare(ace, king);

            assertThat(winner.getRank()).isEqualTo(Rank.ACE);
        }

        @Test
        void aceIsHigherThanKingAsSecondArg() {
            Card ace = new Card(Suit.CLUBS, Rank.ACE);
            Card king = new Card(Suit.CLUBS, Rank.KING);

            Card winner = comparator.compare(king, ace);

            assertThat(winner.getRank()).isEqualTo(Rank.ACE);
        }

        @Test
        void kingIsHigherThanTwo() {
            Card king = new Card(Suit.CLUBS, Rank.KING);
            Card two = new Card(Suit.CLUBS, Rank.TWO);


            Card winner = comparator.compare(king, two);

            assertThat(winner.getRank()).isEqualTo(Rank.KING);
        }
    }

    @Nested
    class SuitTests {
        @Test
        void diamondsIsHigherThanHeartsAsFirstArg() {
            Card diamond = new Card(Suit.DIAMONDS, Rank.TWO);
            Card heart = new Card(Suit.HEARTS, Rank.TWO);

            Card winner = comparator.compare(diamond, heart);

            assertThat(winner.getSuit()).isEqualTo(Suit.DIAMONDS);
        }

        @Test
        void diamondsIsHigherThanHeartsAsSecondArg() {
            Card diamond = new Card(Suit.DIAMONDS, Rank.TWO);
            Card heart = new Card(Suit.HEARTS, Rank.TWO);

            Card winner = comparator.compare(heart, diamond);

            assertThat(winner.getSuit()).isEqualTo(Suit.DIAMONDS);
        }

        @Test
        void spadesGraterThanClubs() {
            Card spade = new Card(Suit.SPADES, Rank.TWO);
            Card club = new Card(Suit.CLUBS, Rank.TWO);

            Card winner = comparator.compare(spade, club);

            assertThat(winner.getSuit()).isEqualTo(Suit.SPADES);
        }
    }

    @Nested
    class ExceptionTests {
        @Test
        void identicalCardsThrowsException() {
            Card card = new Card(Suit.DIAMONDS, Rank.TWO);
            Card duplicateCard = new Card(Suit.DIAMONDS, Rank.TWO);

            assertThatThrownBy(() -> comparator.compare(card, duplicateCard))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(card.toString());
        }
    }
}