package com.holub.kyle.game.main;

import com.holub.kyle.game.engine.GameEngine;
import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.WindowOptions;
import com.holub.kyle.game.graphics.renderer.StatsManagerState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatisticsVisualization {

    private static final int MAX_GAMES = 10000;

    public static void main(String[] args) {
        try {
            WindowOptions opts = WindowOptions.builder()
                    .cullFace(false)
                    .showFps(true)
                    .compatibleProfile(true)
                    .antialiasing(true)
                    .frustumCulling(false)
                    .isFullScreen(true)
                    .enableVsync(true)
                    .build();
            GameState statsState = new StatsManagerState(MAX_GAMES);
            GameEngine gameEng = new GameEngine("GAME", opts, statsState);
            gameEng.run();
        } catch (Exception ex) {
            log.error("ERROR INITIALIZING OPEN-GL CONTEXT");
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
