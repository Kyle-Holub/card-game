package com.holub.kyle.game.neural.netcore;

import java.util.Random;
import java.util.UUID;

public class RandomGenerator {
    private static final Random random = new Random();
    private static final int MULTIPLIER = 1;

    private RandomGenerator() {
        /* private constructor */
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static double getRandom() {
        return MULTIPLIER * (random.nextDouble() * 2 - 1);
    }
}
