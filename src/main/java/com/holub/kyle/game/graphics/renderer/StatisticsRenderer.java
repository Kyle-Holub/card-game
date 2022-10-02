package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.graphics.handlers.Window;
import org.lwjgl.nanovg.NVGColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgClosePath;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgLineTo;
import static org.lwjgl.nanovg.NanoVG.nvgMoveTo;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgStroke;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeColor;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeWidth;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.system.MemoryUtil.NULL;

public class StatisticsRenderer {

    private long nvgContext;
    private NVGColor nvgColor;

    List<SnapShot> snapShots;

    int screenWidth;
    int screenHeight;

    private static final int LINE_WIDTH = 2;
    private static final int PAD_TOP = 150;
    private static final int PAD_BOTTOM = 50;
    private static final int PAD_LEFT = 50;
    private static final int PAD_RIGHT = 50;
    private int graphX1;
    private int graphY1;
    private int graphWidth;
    private int graphHeight;

    private int numUpdates;
    private int graphYZero;
    private float widthBetweenPoints;

    public void init(Window w) {
        snapShots = new ArrayList<>();

        screenWidth = w.getWidth();
        screenHeight = w.getHeight();

        graphX1 = PAD_LEFT;
        graphY1 = PAD_TOP;
        graphWidth = screenWidth - (PAD_LEFT + PAD_RIGHT);
        graphHeight = screenHeight - (PAD_TOP + PAD_BOTTOM);

        numUpdates = 1;
        graphYZero = screenHeight - PAD_BOTTOM;

        SnapShot firstSnap = new SnapShot(0, 0);
        firstSnap.setHighestScoreYPos(graphYZero);
        firstSnap.setAverageYPos(graphYZero);
        snapShots.add(firstSnap);

        this.nvgContext = w.getOpts().isAntialiasing() ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.nvgContext == NULL) {
            throw new IllegalStateException("Could not init NanoVG");
        }

        nvgColor = NVGColor.create();
    }

    public void update(int highScore, float average) {
        numUpdates++;
        widthBetweenPoints = (float) graphWidth / (numUpdates - 1);
        SnapShot previousSnapshot = snapShots.get(snapShots.size() - 1);
        if (highScore != previousSnapshot.getHighestScore()) {
            SnapShot snapWithDiff = new SnapShot(highScore, average);
            snapWithDiff.setHighestScoreYPos(graphYZero - normalize(highScore));
            snapWithDiff.setAverageYPos(graphYZero - normalize(average));
            snapShots.add(snapWithDiff);
        } else {
            previousSnapshot.incWeight();
        }

        float currentXPos = PAD_LEFT;
        snapShots.get(0).setXPos(currentXPos);
        for (int i = 1; i < snapShots.size(); i++) {
            SnapShot snapShot = snapShots.get(i);
            currentXPos += widthBetweenPoints * snapShot.getWeight();
            snapShot.setXPos(currentXPos);
        }
    }

    public void render() {
        nvgBeginFrame(nvgContext, screenWidth, screenHeight, 1);

        nvgBeginPath(nvgContext);
        nvgRect(nvgContext, graphX1, graphY1, graphWidth, graphHeight);
        nvgFillColor(nvgContext, rgba(20, 20, 20, 255, nvgColor));
        nvgFill(nvgContext);
        nvgClosePath(nvgContext);

        if (snapShots.size() > 1) {
            AtomicReference<SnapShot> prevSnap = new AtomicReference<>(snapShots.get(0));
            snapShots.stream().skip(1).forEach(snapShot -> {
                renderHighestScoreLine(prevSnap, snapShot);
                renderAverageScoreLine(prevSnap, snapShot);
                prevSnap.set(snapShot);
            });
        }

        nvgEndFrame(nvgContext);
    }

    private void renderAverageScoreLine(AtomicReference<SnapShot> prevSnap, SnapShot snapShot) {
        nvgBeginPath(nvgContext);
        nvgMoveTo(nvgContext, prevSnap.get().getXPos(), prevSnap.get().getAverageYPos());
        nvgLineTo(nvgContext, snapShot.getXPos(), snapShot.getAverageYPos());
        nvgStrokeColor(nvgContext, rgba(60, 60, 255, 255, nvgColor));
        nvgStrokeWidth(nvgContext, LINE_WIDTH);
        nvgStroke(nvgContext);
        nvgClosePath(nvgContext);
    }

    private void renderHighestScoreLine(AtomicReference<SnapShot> prevSnap, SnapShot snapShot) {
        nvgBeginPath(nvgContext);
        nvgMoveTo(nvgContext, prevSnap.get().getXPos(), prevSnap.get().getHighestScoreYPos());
        nvgLineTo(nvgContext, snapShot.getXPos(), snapShot.getHighestScoreYPos());
        nvgStrokeColor(nvgContext, rgba(255, 60, 60, 255, nvgColor));
        nvgStrokeWidth(nvgContext, LINE_WIDTH);
        nvgStroke(nvgContext);
        nvgClosePath(nvgContext);
    }

    private float normalize(int score) {
        return (float) (5 * score);
    }

    private float normalize(double score) {
        return (float) (5 * score);
    }

    // update frequency in seconds
    // subdivide screen into sections per update
    // plot point for each section
    // draw line between points

    private NVGColor rgba(int r, int g, int b, int a, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(a / 255.0f);

        return colour;
    }
}
