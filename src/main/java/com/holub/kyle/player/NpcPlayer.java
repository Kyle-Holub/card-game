package com.holub.kyle.player;

import com.holub.kyle.deck.Card;
import com.holub.kyle.deck.enums.Suit;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

@Slf4j
@EqualsAndHashCode(callSuper = true)
public class NpcPlayer extends Player {

    private static final Random RAND = new Random();

    public NpcPlayer() {
        super();
        this.setName(genRandomName());
    }

    @Override
    public int getBid() {
        int bound = getHand().size() / 2;
        int bid = 0;
        if (bound > 0) {
            bid = RAND.nextInt(bound);
        }
        log.info(String.format("%s bid %s", this, bid));
        return bid;
    }

    @Override
    public Card playCard(Suit leadSuit, Suit trumpSuit) {
        int numCardsInHand = getHand().size();
        Card cardToPlay = getHand().get(RAND.nextInt(numCardsInHand));
        getHand().remove(cardToPlay);
        log.info(String.format("%s played %s", this, cardToPlay));
        return cardToPlay;
    }

    private String genRandomName() {
        String firstName = Arrays.asList(NpcName.FirstNames.values()).get(RAND.nextInt(NpcName.FirstNames.values().length)).toString();
        String lastNamePrefix = Arrays.asList(NpcName.LastNamePrefix.values()).get(RAND.nextInt(NpcName.LastNamePrefix.values().length)).toString();
        String lastName = Arrays.asList(NpcName.LastName.values()).get(RAND.nextInt(NpcName.LastName.values().length)).toString();
        return String.format("%s %s%s", firstName, lastNamePrefix, lastName);
    }
}
