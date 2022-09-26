package com.holub.kyle.game.engine;

import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.graphics.handlers.WindowOptions;
import com.holub.kyle.game.util.Timer;

public class GameEngine implements Runnable {
    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 20000;
    private final Window window;
    private final Timer timer;
    private final GameState gameLogic;
//    private final MouseInput mouseInput;
    private double lastFps;
    private int fps;
    private final String windowTitle;

    public GameEngine(String windowTitle, WindowOptions opts, GameState gameLogic) {
        this(windowTitle, 0, 0, opts, gameLogic);
    }

    public GameEngine(String windowTitle, int width, int height, WindowOptions opts, GameState gameLogic) {
        this.windowTitle = windowTitle;
        window = new Window(windowTitle, opts);
//        mouseInput = new MouseInput();
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        }
//        finally {
//            cleanup();
//        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
//        mouseInput.init(window);
        gameLogic.init(window);
        lastFps = timer.getTime();
        fps = 0;
    }

    protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

//            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if ( !window.isvSync() ) {
                sync();
            }
        }
    }

//    protected void cleanup() {
//        gameLogic.cleanup();
//    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

//    private void input() {
//        mouseInput.input();
//        gameLogic.input(window, mouseInput);
//    }

    protected void update(float interval) {
        gameLogic.update();//interval, mouseInput, window);
    }

    protected void render() {
        if ( window.getWindowOptions().isShowFps() && timer.getLastLoopTime() - lastFps > 1 ) {
            lastFps = timer.getLastLoopTime();
            window.setWindowTitle(windowTitle + " - " + fps + " FPS");
            fps = 0;
        }
        fps++;
        gameLogic.render(window);
        window.update();
    }
}
