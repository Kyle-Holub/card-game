package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.util.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
public class TextRenderer {

    private static final String FONT_NAME = "BOLD";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private long nvgContext;
    private NVGColor colorAlloc;
    private DoubleBuffer posX;
    private DoubleBuffer posY;

    public void init(Window window) throws IOException {
        this.nvgContext = window.getOptions().isAntialiasing() ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.nvgContext == NULL) {
            throw new IllegalStateException("Could not init NanoVG");
        }

        ByteBuffer fontBuffer = ResourceUtils.ioResourceToByteBuffer("/fonts/OpenSans-Bold.ttf", 150 * 1024);
        int font = nvgCreateFontMem(nvgContext, FONT_NAME, fontBuffer, 0);
        if (font == -1) {
            throw new IllegalStateException("Could not add font");
        }
        colorAlloc = NVGColor.create();

        posX = MemoryUtil.memAllocDouble(1);
        posY = MemoryUtil.memAllocDouble(1);
    }

    public void renderTimestamp(Window window) {
        nvgBeginFrame(nvgContext, window.getWidth(), window.getHeight(), 1);

        nvgBeginPath(nvgContext);
        nvgRect(nvgContext, 100, window.getHeight() - 200, window.getWidth() - 200, 100);
        nvgFillColor(nvgContext, setColor(0x23, 0xd1, 0xf1, 200, colorAlloc));
        nvgFill(nvgContext);

        // Render hour text
        nvgFontSize(nvgContext, 60.0f);
        nvgFontFace(nvgContext, FONT_NAME);
        nvgTextAlign(nvgContext, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(nvgContext, setColor(0xe6, 0xea, 0xed, 255, colorAlloc));
        nvgText(nvgContext, (window.getWidth() / 2) - 100, window.getHeight() - 175, DATE_FORMAT.format(new Date()));

        nvgEndFrame(nvgContext);

        window.restoreState();
    }

    public void renderText(Window window, String text, float posX, float posY) {
        nvgBeginFrame(nvgContext, window.getWidth(), window.getHeight(), 1);

        nvgFontSize(nvgContext, 60.0f);
        nvgFontFace(nvgContext, FONT_NAME);
        nvgTextAlign(nvgContext, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(nvgContext, setColor(0xe6, 0xea, 0xed, 255, colorAlloc));
        nvgText(nvgContext, posX, posY, text);

        nvgEndFrame(nvgContext);
        window.restoreState();
    }

    private NVGColor setColor(int r, int g, int b, int a, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(a / 255.0f);
        return colour;
    }

    public void cleanup() {
        nvgDelete(nvgContext);
        if (posX != null) {
            MemoryUtil.memFree(posX);
        }
        if (posY != null) {
            MemoryUtil.memFree(posY);
        }
    }
}
