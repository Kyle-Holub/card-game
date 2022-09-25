package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.logic.hand.HandSeriesManager;
import lombok.extern.slf4j.Slf4j;

import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;

@Slf4j
public class StatsManagerState extends GameState {

    private static final int MAX_GAMES = 1000;
    private int highestScore;
    private int gamesRun = 0;
    private HandSeriesManager game;
    private TextRenderer text;

    @Override
    public void init(Window w) {
        game = new HandSeriesManager();
        game.init();
        text = new TextRenderer();
        try {
            text.init(w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        game.update();
        if (game.isGameOver()) {
            int score = game.getWinnerScore();
            if (score > highestScore) {
                highestScore = score;
            }
            log.info("HIGHEST SCORE:" + highestScore);
            gamesRun++;
            if (gamesRun < MAX_GAMES) {
                game = new HandSeriesManager();
                game.init();
            } else {
                log.info("done");
                System.exit(0);
            }
        }
    }

    @Override
    public void render(Window w) {
        glClear( GL_COLOR_BUFFER_BIT);
        text.renderTimestamp(w);
        text.renderText(w, "Highest score: " + highestScore, 500f, 500f);
        text.renderText(w, "Num games: " + gamesRun, 500f, 600f);
//        glfwSwapBuffers(w.getWindowHandle());
//        glfwPollEvents();
    }

    @Override
    public void processInput() {
        // not needed
    }
}
