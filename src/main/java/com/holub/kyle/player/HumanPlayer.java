package com.holub.kyle.player;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Suit;
import lombok.extern.slf4j.Slf4j;

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
    public Card playCard(Suit leadSuit, Suit trumpSuit) {
        log.info("Your cards are: " + getHand().toString());
        log.info("Enter card to play...");
        String input = scanner.nextLine();
        int index = Integer.parseInt(input);

        while (index < 0 || index > getHand().size()) {
            log.info(String.format("Index must be between 0 and %s", getHand().size()));
            input = scanner.nextLine();
            index = Integer.parseInt(input);
        }

        Card cardToPlay = getHand().get(index);
        getHand().remove(cardToPlay);
        return cardToPlay;
    }
}
