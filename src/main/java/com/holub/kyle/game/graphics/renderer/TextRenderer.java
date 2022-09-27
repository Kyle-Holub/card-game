package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.util.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.nanovg.NVGColor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
public class TextRenderer {

    private static final String FONT_NAME = "REGULAR";

    private long nvgContext;
    private NVGColor nvgColor;

    @SuppressWarnings("FieldCanBeLocal") // ignore - else app explodes
    private ByteBuffer fontBuffer;

    public void init(Window window) throws IOException {
        this.nvgContext = window.getOpts().isAntialiasing() ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.nvgContext == NULL) {
            throw new IllegalStateException("Could not init NanoVG");
        }

        fontBuffer = ResourceUtils.ioResourceToByteBuffer("/fonts/OpenSans-Bold.ttf", 150 * 1024);
        int font = nvgCreateFontMem(nvgContext, FONT_NAME, fontBuffer, 0);
        if (font == -1) {
            throw new IllegalStateException("Could not init font");
        }
        nvgColor = NVGColor.create();
    }

    public void renderTimeElapsed(Window window, LocalDateTime startTime) {
        nvgFontSize(nvgContext, 40.0f);
        nvgFontFace(nvgContext, FONT_NAME);
        nvgTextAlign(nvgContext, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(nvgContext, setRgbColor(0xe6, 0xea, 0xed, nvgColor));
        nvgText(nvgContext, window.getWidth() - 200f, window.getHeight() - 95f, getTimeElapsed(startTime));
    }

    private static String getTimeElapsed(LocalDateTime startTime) {
        long millis = Duration.between(startTime, LocalDateTime.now()).toMillis();
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public void renderText(Window window, String text, float posX, float posY) {
        nvgBeginFrame(nvgContext, window.getWidth(), window.getHeight(), 1);

        nvgFontSize(nvgContext, 60.0f);
        nvgFontFace(nvgContext, FONT_NAME);
        nvgTextAlign(nvgContext, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(nvgContext, setRgbColor(255, 255, 255, nvgColor));
        nvgText(nvgContext, posX, posY, text);

        nvgEndFrame(nvgContext);
    }

    private NVGColor setRgbColor(int r, int g, int b, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(255 / 255.0f);

        return colour;
    }
}
