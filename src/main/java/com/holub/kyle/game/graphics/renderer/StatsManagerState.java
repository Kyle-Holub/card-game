package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.logic.hand.HandSeriesManager;
import com.holub.kyle.game.util.SecondTimer;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

@Slf4j
public class StatsManagerState extends GameState {

    private static final int UPDATE_FREQ_IN_MILLIS = 500;

    private final int maxGames;
    private int highestScore;
    private float average;
    private int gamesRun = 0;
    private HandSeriesManager game;
    private TextRenderer text;
    private StatisticsRenderer statsRenderer;
    private LocalDateTime startTime;

    private SecondTimer timer;

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
        statsRenderer = new StatisticsRenderer();
        try {
            text.init(w);
            statsRenderer.init(w);
        } catch (Exception e) {
            e.printStackTrace();
        }

        timer = new SecondTimer();
        timer.start();
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

        if (timer.getElapsedTimeInSeconds() > UPDATE_FREQ_IN_MILLIS) {
            statsRenderer.update(highestScore, average);
            timer.start();
        }
    }

    private void updateRollingAverage(double newSample) {
        if (gamesRun > 0) {
            average = (float) (average * (gamesRun - 1) / gamesRun + newSample / gamesRun);
        }
    }

    @Override
    public void render(Window w) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        int TEXT_MARGIN_LEFT = 50;
        statsRenderer.render();
        text.renderTimeElapsed(w, startTime);
        text.renderText(w, "Games Completed: " + gamesRun, TEXT_MARGIN_LEFT, 15f);
        text.renderText(w, "Highest Score: " + highestScore, TEXT_MARGIN_LEFT, 60f);
        text.renderText(w, "Average Score: " + df.format(average), TEXT_MARGIN_LEFT, 105f);
    }

    @Override
    public void processInput() { /* not needed */ }
}
