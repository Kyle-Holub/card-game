package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.Window;

public class StatsManagerState extends GameState {

    StatisticsRenderer renderer;

    @Override
    public void init() {
        renderer = new StatisticsRenderer();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Window w) {

    }

    @Override
    public void processInput() {

    }
}
