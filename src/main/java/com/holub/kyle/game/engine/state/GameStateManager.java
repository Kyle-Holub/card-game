package com.holub.kyle.game.engine.state;

import com.holub.kyle.game.graphics.handlers.Window;

import java.util.Map;

public class GameStateManager {

    private final Map<State, GameState> gameStates;
    private State currentState;

    public GameStateManager() {
        gameStates = Map.of(State.WELCOME, new WelcomeState(this));
        currentState = State.WELCOME;
    }

    public void setState(State state) {
        currentState = state;
//        gameStates.get(currentState).init(w);
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void render(Window w) {
        gameStates.get(currentState).render(w);
    }

    public void processInput() {
        gameStates.get(currentState).processInput();
    }

}
