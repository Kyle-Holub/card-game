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

    private int graphYZero;

    public void init(Window w) {
        snapShots = new ArrayList<>();

        screenWidth = w.getWidth();
        screenHeight = w.getHeight();

        graphX1 = PAD_LEFT;
        graphY1 = PAD_TOP;
        graphWidth = screenWidth - (PAD_LEFT + PAD_RIGHT);
        graphHeight = screenHeight - (PAD_TOP + PAD_BOTTOM);

        graphYZero = screenHeight - PAD_BOTTOM;

        this.nvgContext = w.getOpts().isAntialiasing() ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.nvgContext == NULL) {
            throw new IllegalStateException("Could not init NanoVG");
        }

        nvgColor = NVGColor.create();
    }

    public void update(SnapShot snapShot) {
        snapShots.add(snapShot);
    }

    public void render() {
        nvgBeginFrame(nvgContext, screenWidth, screenHeight, 1);

        nvgBeginPath(nvgContext);
        nvgRect(nvgContext, graphX1, graphY1, graphWidth, graphHeight);
        nvgFillColor(nvgContext, rgba(20, 20, 20, 255, nvgColor));
        nvgFill(nvgContext);
        nvgClosePath(nvgContext);

        if (snapShots.size() > 1) {
            float widthBetweenPoints = graphWidth / (snapShots.size() - 1);

            AtomicReference<SnapShot> prevSnap = new AtomicReference<>(snapShots.get(0));
            final float[] xPos = {PAD_LEFT};
            snapShots.stream().skip(1).forEach(snapShot -> {
                renderHighestScoreLine(prevSnap, xPos, snapShot, xPos[0] + widthBetweenPoints);
                renderAverageScoreLine(prevSnap, xPos, snapShot, xPos[0] + widthBetweenPoints);
                xPos[0] += widthBetweenPoints;
                prevSnap.set(snapShot);
            });
        }

        nvgEndFrame(nvgContext);
    }

    private void renderAverageScoreLine(AtomicReference<SnapShot> prevSnap, float[] xPos, SnapShot snapShot, float nextXPos) {
        nvgBeginPath(nvgContext);
        nvgMoveTo(nvgContext, xPos[0], graphYZero - normalize(prevSnap.get().getAverage()));
        nvgLineTo(nvgContext, nextXPos, graphYZero - normalize(snapShot.getAverage()));
        nvgStrokeColor(nvgContext, rgba(60, 60, 255, 255, nvgColor));
        nvgStrokeWidth(nvgContext, LINE_WIDTH);
        nvgStroke(nvgContext);
        nvgClosePath(nvgContext);
    }

    private void renderHighestScoreLine(AtomicReference<SnapShot> prevSnap, float[] xPos, SnapShot snapShot, float nextXPos) {
        nvgBeginPath(nvgContext);
        nvgMoveTo(nvgContext, xPos[0], graphYZero - normalize(prevSnap.get().getHighestScore()));
        nvgLineTo(nvgContext, nextXPos, graphYZero - normalize(snapShot.getHighestScore()));
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
