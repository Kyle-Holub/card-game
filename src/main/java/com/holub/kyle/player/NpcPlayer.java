package com.holub.kyle.player;

import com.holub.kyle.deck.Card;
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
        log.info(String.format("%s bid 0", this));
        return 0;
    }

    @Override
    public Card playCard() {
        Card cardToPlay = getHand().get(0);
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
