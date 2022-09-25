package com.holub.kyle.game.graphics;

import com.holub.kyle.game.engine.GameEngine;
import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.WindowOptions;
import com.holub.kyle.game.graphics.renderer.StatsManagerState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatisticsVisualization {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            WindowOptions opts = WindowOptions.builder()
                    .cullFace(false)
                    .showFps(true)
                    .compatibleProfile(true)
                    .antialiasing(true)
                    .frustumCulling(false)
                    .build();
            GameState statsState = new StatsManagerState();
            GameEngine gameEng = new GameEngine("GAME", vSync, opts, statsState);
            gameEng.run();
        } catch (Exception ex) {
            log.error("ERROR INITIALIZING OPEN-GL CONTEXT");
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
