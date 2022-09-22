package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.engine.state.GameState;
import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.logic.round.RoundManager;
import lombok.extern.slf4j.Slf4j;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_LINES;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glLineWidth;
import static org.lwjgl.opengl.GL11C.glPointSize;

@Slf4j
public class StatsManagerState extends GameState {

    private int highestScore;
    private final int numGames = 1000;
    private int gamesRun = 0;
    private RoundManager game;

    @Override
    public void init() {
        game = new RoundManager();
        game.manageRounds(1);
    }

    @Override
    public void update() {
        if (game.isRoundOver()) {
            highestScore = game.getHighestScore();
            gamesRun++;
            if (gamesRun < numGames) {
                game = new RoundManager();
                game.manageRounds(1);
            }
        }
    }

    @Override
    public void render(Window w) {
        log.info("rendering");
        glfwMakeContextCurrent(w.getWindowHandle());
//        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glMatrixMode(GL_MODELVIEW); //Switch to the drawing perspective
        glLoadIdentity(); //Reset the drawing perspective

        glColor3f(0.1f, 1.0f, 1.0f);
//        glClearColor ( 1.0f, 1.0f, 1.0f, 1.0f );
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glPointSize(10);
        glLineWidth(2.5f);
        glColor3f(1.0f, 0.0f, 0.0f);
        glBegin(GL_LINES);
        glVertex3f(10.0f,10.0f,0.0f);
        glVertex3f(200.0f,200.0f,0.0f);
        glEnd();
//        glfwSwapBuffers(w.getWindowHandle());
    }

    @Override
    public void processInput() {
        // not needed
    }
}
