package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.logic.round.RoundManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatsManagerState extends GameState {

    private int highestScore;
    private final int numGames = 1000;
    private int gamesRun = 0;
    private RoundManager game;
    private TextRenderer text;

    @Override
    public void init(Window w) {
        game = new RoundManager();
        text = new TextRenderer();
        try {
            text.init(w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (game.isRoundOver()) {
            highestScore = game.getHighestScore();
            gamesRun++;
            if (gamesRun < numGames) {
                game = new RoundManager();
                game.manageRounds(1);
            }
        }
    }

    @Override
    public void render(Window w) {
        text.render(w);
    }

    @Override
    public void processInput() {
        // not needed
    }
}
