package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.graphics.handlers.Window;
import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.system.MemoryUtil.NULL;

public class StatisticsRenderer {

    private long nvgContext;
    private NVGColor nvgColor;

    int screenWidth;
    int screenHeight;

    private static final int PAD_TOP = 150;
    private static final int PAD_BOTTOM = 50;
    private static final int PAD_LEFT = 50;
    private static final int PAD_RIGHT = 50;
    private int graphX1;
    private int graphY1;
    private int graphWidth;
    private int graphHeight;

    public void init(Window w) {
        screenWidth = w.getWidth();
        screenHeight = w.getHeight();

        graphX1 = PAD_LEFT;
        graphY1 = PAD_TOP;
        graphWidth = screenWidth - (PAD_LEFT + PAD_RIGHT);
        graphHeight = screenHeight - (PAD_TOP + PAD_BOTTOM);

        this.nvgContext = w.getOpts().isAntialiasing() ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.nvgContext == NULL) {
            throw new IllegalStateException("Could not init NanoVG");
        }

        nvgColor = NVGColor.create();
    }

    public void render(Window w) {
        nvgBeginFrame(nvgContext, screenWidth, screenHeight, 1);

        nvgBeginPath(nvgContext);
        nvgRect(nvgContext, graphX1, graphY1, graphWidth, graphHeight);
        nvgFillColor(nvgContext, rgba(20, 20, 20, 255, nvgColor));
        nvgFill(nvgContext);

        nvgEndFrame(nvgContext);
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
