package com.holub.kyle.game.player;

import com.holub.kyle.game.deck.Card;
import com.holub.kyle.game.deck.enums.Suit;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class HumanPlayer extends Player {

    private final Scanner scanner;

    HumanPlayer(Scanner newScanner) {
        scanner = newScanner;
    }

    @Override
    public int getBid() {
        String input = scanner.nextLine();

        return Integer.parseInt(input);
    }

    @Override
    public Card playCardEnforced(List<Card> cards, Suit leadSuit, Suit trumpSuit) {
        log.info("Your cards are: " + getHand().toString());
        log.info("Cards you can play: " + cards.toString());
        log.info("Enter card to play...");
        String input = scanner.nextLine();
        int index = Integer.parseInt(input);

        while (index < 0 || index > cards.size()) {
            log.info(String.format("Index must be between 0 and %s", cards.size()));
            input = scanner.nextLine();
            index = Integer.parseInt(input);
        }

        Card cardToPlay = cards.get(index);
        getHand().remove(cardToPlay);
        return cardToPlay;
    }
}
