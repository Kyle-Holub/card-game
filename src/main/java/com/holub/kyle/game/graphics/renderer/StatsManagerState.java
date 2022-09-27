package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.logic.hand.HandSeriesManager;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

@Slf4j
public class StatsManagerState extends GameState {

    private final int maxGames;
    private int highestScore;
    private double average;
    private int gamesRun = 0;
    private HandSeriesManager game;
    private TextRenderer text;
    private LocalDateTime startTime;

    DecimalFormat df = new DecimalFormat("###");

    public StatsManagerState(int maxGames) {
        this.maxGames = maxGames;
    }

    @Override
    public void init(Window w) {
        startTime = LocalDateTime.now();
        game = new HandSeriesManager();
        game.init();
        average = 0;
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
            updateRollingAverage(score);
            if (score > highestScore) {
                highestScore = score;
            }
            gamesRun++;
            if (gamesRun < maxGames) {
                game = new HandSeriesManager();
                game.init();
            } else {
                System.exit(0);
            }
        }
    }

    private void updateRollingAverage(double newSample) {
        if (gamesRun > 0) {
            average = average * (gamesRun - 1) / gamesRun + newSample / gamesRun;
        }
    }

    @Override
    public void render(Window w) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        text.renderTimeElapsed(w, startTime);
        text.renderText(w, "Games Completed: " + gamesRun, 500f, 500f);
        text.renderText(w, "Highest Score: " + highestScore, 500f, 600f);
        text.renderText(w, "Average Score: " + df.format(average), 500f, 700f);
    }

    @Override
    public void processInput() { /* not needed */ }
}
