package com.holub.kyle.game.graphics.renderer;

import com.holub.kyle.game.graphics.handlers.Window;
import com.holub.kyle.game.util.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
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
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
public class TextRenderer {

    private static final String FONT_NAME = "BOLD";

    private long vg;

    private NVGColor colour;

    private ByteBuffer fontBuffer;

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private DoubleBuffer posx;

    private DoubleBuffer posy;

    private int counter;

    public void init(Window window) throws Exception {
        this.vg = window.getOptions().isAntialiasing() ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.vg == NULL) {
            throw new Exception("Could not init nanovg");
        }

        fontBuffer = ResourceUtils.ioResourceToByteBuffer("/fonts/OpenSans-Bold.ttf", 150 * 1024);
        int font = nvgCreateFontMem(vg, FONT_NAME, fontBuffer, 0);
        if (font == -1) {
            throw new Exception("Could not add font");
        }
        colour = NVGColor.create();

        posx = MemoryUtil.memAllocDouble(1);
        posy = MemoryUtil.memAllocDouble(1);

        counter = 0;
    }

    public void renderTimeElapsed(Window window, LocalDateTime startTime) {
        // Render hour text
        nvgFontSize(vg, 40.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, rgba(0xe6, 0xea, 0xed, 255, colour));
        long millis = Duration.between(startTime, LocalDateTime.now()).toMillis();
        String timestampString = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        nvgText(vg, window.getWidth() - 200, window.getHeight() - 95, timestampString);
    }

    public void render(Window window) {
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);

//        // Upper ribbon
//        nvgBeginPath(vg);
//        nvgRect(vg, 0, window.getHeight() - 100, window.getWidth(), 50);
//        nvgFillColor(vg, rgba(0x23, 0xa1, 0xf1, 200, colour));
//        nvgFill(vg);
//
//        // Lower ribbon
//        nvgBeginPath(vg);
//        nvgRect(vg, 0, window.getHeight() - 50, window.getWidth(), 10);
//        nvgFillColor(vg, rgba(0xc1, 0xe3, 0xf9, 200, colour));
//        nvgFill(vg);
//
//        glfwGetCursorPos(window.getWindowHandle(), posx, posy);
//        int xcenter = 50;
//        int ycenter = window.getHeight() - 75;
//        int radius = 20;
//        int x = (int) posx.get(0);
//        int y = (int) posy.get(0);
//        boolean hover = Math.pow(x - xcenter, 2) + Math.pow(y - ycenter, 2) < Math.pow(radius, 2);
//
//        // Circle
//        nvgBeginPath(vg);
//        nvgCircle(vg, xcenter, ycenter, radius);
//        nvgFillColor(vg, rgba(0xc1, 0xe3, 0xf9, 200, colour));
//        nvgFill(vg);
//
//        // Clicks Text
//        nvgFontSize(vg, 25.0f);
//        nvgFontFace(vg, FONT_NAME);
//        nvgTextAlign(vg, NVG_ALIGN_CENTER | NVG_ALIGN_TOP);
//        if (hover) {
//            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, colour));
//        } else {
//            nvgFillColor(vg, rgba(0x23, 0xa1, 0xf1, 255, colour));
//
//        }
//        nvgText(vg, 50, window.getHeight() - 87, String.format("%02d", counter));

        // Render hour text
        nvgFontSize(vg, 40.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, rgba(0xe6, 0xea, 0xed, 255, colour));
        nvgText(vg, window.getWidth() - 200, window.getHeight() - 95, dateFormat.format(new Date()));
        
//        nvgFontSize(vg, 60.0f);
//        nvgFontFace(vg, FONT_NAME);
//        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
//        nvgFillColor(vg, rgba(0xe6, 0xea, 0xed, 255, colour));
//        nvgText(vg, 500, 500, "highest bullshit");
        

        nvgEndFrame(vg);

        // Restore state
        window.restoreState();
    }

    public void renderText(Window window, String text, float posX, float posY) {
//        nvgReset(vg);
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);
//        nvgSave(vg);

        nvgFontSize(vg, 60.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, rgba(0xe6, 0xea, 0xed, 255, colour));
        nvgText(vg, posX, posY, text);

//        nvgRestore(vg);
        nvgEndFrame(vg);
//        window.restoreState();
    }

    public void incCounter() {
        counter++;
        if (counter > 99) {
            counter = 0;
        }
    }

    private NVGColor rgba(int r, int g, int b, int a, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(a / 255.0f);

        return colour;
    }

    public void cleanup() {
        nvgDelete(vg);
        if (posx != null) {
            MemoryUtil.memFree(posx);
        }
        if (posy != null) {
            MemoryUtil.memFree(posy);
        }
    }
}
