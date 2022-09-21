package com.holub.kyle.game.graphics;

import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.graphics.handlers.WindowOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatisticsVisualization {

    // TODO draw stats on a canvas with openGL
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
            Window window = new Window("Graphics Test", vSync, opts);
            window.init();
        } catch (Exception ex) {
            log.error("ERROR INITIALIZING OPEN-GL CONTEXT");
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
