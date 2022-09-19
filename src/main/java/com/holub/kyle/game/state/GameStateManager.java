package com.holub.kyle.game.state;

import java.awt.*;
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
        gameStates.get(currentState).init();
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw(Graphics g) {
        gameStates.get(currentState).draw(g);
    }

    public void processInput() {
        gameStates.get(currentState).processInput();
    }

}
