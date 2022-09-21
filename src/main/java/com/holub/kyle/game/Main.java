package com.holub.kyle.game;

import com.holub.kyle.game.logic.round.RoundManager;

public class Main {
    // runs 10,000 games and outputs the highest score
    public static void main(String[] args) {
        RoundManager game = new RoundManager();
        game.manageRounds(10000);
    }
}