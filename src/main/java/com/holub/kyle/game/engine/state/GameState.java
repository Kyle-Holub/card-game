package com.holub.kyle.game.engine.state;

import com.holub.kyle.game.engine.Updateable;
import com.holub.kyle.game.graphics.handlers.Window;

public abstract class GameState implements Updateable {
    protected GameStateManager gsm;

    public abstract void init(Window w);
    public abstract void render(Window w);
    public abstract void processInput();
}
