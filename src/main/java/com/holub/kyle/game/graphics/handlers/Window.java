package com.holub.kyle.game.graphics.handlers;

import lombok.extern.slf4j.Slf4j;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_COMPAT_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
public class Window {

    // Field of View in Radians
    public static final float FOV = (float) Math.toRadians(60.0f);

    // Distance to the near plane
    public static final float Z_NEAR = 0.01f;

    // Distance to the far plane
    public static final float Z_FAR = 1000.f;

    private final String title;

    private int width;
    private int height;
    private long windowHandle;
    private boolean resized;
    private boolean vSync;

    private final WindowOptions opts;

    private final Matrix4f projectionMatrix;

    public Window(String title, boolean vSync, WindowOptions opts) {
        this.title = title;
        this.vSync = vSync;
        this.opts = opts;
        projectionMatrix = new Matrix4f();
    }

    public void init() {
        try (GLFWErrorCallback error = GLFWErrorCallback.createPrint()) {
            error.set();

            initGLFW();
            setWindowHints();
            createWindow();
            setupResizeWindowCallback();
            setupKeyboardCallback();
            glfwMakeContextCurrent(windowHandle);
            enableVsync();
            glfwShowWindow(windowHandle);
            GL.createCapabilities();

            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_STENCIL_TEST);

//            glViewport( 0, 0, 400, 400 );
//            glMatrixMode( GL_PROJECTION );
//            glLoadIdentity();

            // see https://www.opengl.org/sdk/docs/man2/xhtml/glOrtho.xml
            glOrtho(0.0,400.0,0.0,400.0,0.0,1.0); // this creates a canvas you can do 2D drawing on

            enableRemainingWindowOpts();
        }
    }

    private void enableRemainingWindowOpts() {
        if (opts.isShowTriangles()) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }

        if (opts.isCullFace()) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_BACK);
        }

        if (opts.isAntialiasing()) {
            glfwWindowHint(GLFW_SAMPLES, 4);
        }
    }

    private void setWindowHints() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden until ready
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        if (opts.isCompatibleProfile()) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }
    }

    private static void initGLFW() {
        if (!glfwInit()) { // Initialize GLFW - GLFW functions will not work before doing this.
            throw new IllegalStateException("Unable to initialize GLFW");
        }
    }

    private void enableVsync() {
        if (isvSync()) {
            glfwSwapInterval(1);
        }
    }

    private void setupKeyboardCallback() {
        glfwSetKeyCallback(windowHandle, (window, key, scanCode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });
    }

    private void setupResizeWindowCallback() {
        glfwSetFramebufferSizeCallback(windowHandle, (window, windowWidth, windowHeight) -> resizeWindow(windowWidth, windowHeight));
    }

    private void resizeWindow(int windowWidth, int windowHeight) {
        this.width = windowWidth;
        this.height = windowHeight;
        this.setResized(true);
    }

    private void createWindow() {
        long primaryMonitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidMode = glfwGetVideoMode(primaryMonitor);
        assert vidMode != null;
        width = vidMode.width();
        height = vidMode.height();
        windowHandle = glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(), NULL);
        if (windowHandle == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }
    }

    public void restoreState() {
//        glOrtho(0.0,400.0,0.0,400.0,0.0,1.0); // this creates a canvas you can do 2D drawing on

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (opts.isCullFace()) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_BACK);
        }
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public String getWindowTitle() {
        return title;
    }

    public void setWindowTitle(String title) {
        glfwSetWindowTitle(windowHandle, title);
    }

    public WindowOptions getWindowOptions() {
        return opts;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f updateProjectionMatrix() {
        float aspectRatio = (float) width / (float) height;
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    public static Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
        float aspectRatio = (float) width / (float) height;
        return matrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    public WindowOptions getOptions() {
        return opts;
    }
}
