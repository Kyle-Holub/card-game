package com.holub.kyle.game.graphics;

import com.holub.kyle.game.graphics.handlers.Window;


public class GraphicsTest {

    // TODO draw stats on a canvas with openGL
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            Window.WindowOptions opts = new Window.WindowOptions();
            opts.cullFace = false;
            opts.showFps = true;
            opts.compatibleProfile = true;
            opts.antialiasing = true;
            opts.frustumCulling = false;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
