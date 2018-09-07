package team.hdt.blockadia.test.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import team.hdt.blockadia.game_engine_old.client.rendering.*;
import team.hdt.blockadia.game_engine_old.common.Identifier;
import team.hdt.blockadia.game_engine_old.common.util.Util;
import team.hdt.blockadia.game_engine_old.common.util.interfaces.Nullable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;
import java.util.function.BiConsumer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Window {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final Logger LOGGER = LogManager.getLogger();
    private final GLFWErrorCallback loggingErrorCallback = GLFWErrorCallback.create(this::logGlError);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final VirtualScreen virtualScreen;
    private Monitor monitor;
    private int prevWindowX;
    private int prevWindowY;
    private int prevWindowWidth;
    private int prevWindowHeight;
    private Optional<VideoMode> videoMode;

    private static int width;
    private static int height;
    public static long windowHandle;
    private boolean fullscreen;
    private boolean lastFullscreen;
    private int windowX;
    private int windowY;
    private static int windowWidth;
    private static int windowHeight;
    private int scaledWidth;
    private int scaledHeight;
    private double guiScaleFactor;

    private boolean resized;

    private boolean vSync;

    private WindowOptions opts;

    private Matrix4f projectionMatrix;
    private GameEngine gameEngine;
    private String renderPhase = "";
    private boolean videoModeChanged;
    private double field_198139_z = 4.9E-324D;
    public static float one_pixel_size = 2.0f / Math.min(windowWidth, windowHeight);

    public Window(GameEngine gameEngine, VirtualScreen p_i47667_2_, GameConfiguration.DisplayInformation p_i47667_3_, String p_i47667_4_) {
        this.virtualScreen = p_i47667_2_;
        this.setThrowExceptionOnGlError();
        this.setRenderPhase("Pre startup");
        this.gameEngine = gameEngine;
        Optional<VideoMode> lvt_5_1_ = VideoMode.parseFromSettings(p_i47667_4_);
        if (lvt_5_1_.isPresent()) {
            this.videoMode = lvt_5_1_;
        } else if (p_i47667_3_.fullscreenWidth.isPresent() && p_i47667_3_.fullscreenHeight.isPresent()) {
            this.videoMode = Optional.of(new VideoMode(p_i47667_3_.fullscreenWidth.get(), p_i47667_3_.fullscreenHeight.get(), 8, 8, 8, 60));
        } else {
            this.videoMode = Optional.empty();
        }

        this.lastFullscreen = this.fullscreen = p_i47667_3_.fullscreen;
        this.monitor = p_i47667_2_.getMonitor(GLFW.glfwGetPrimaryMonitor());
        VideoMode lvt_6_1_ = this.monitor.getVideoModeOrDefault(this.fullscreen ? this.videoMode : Optional.empty());
        this.prevWindowWidth = this.windowWidth = p_i47667_3_.width > 0 ? p_i47667_3_.width : 1;
        this.prevWindowHeight = this.windowHeight = p_i47667_3_.height > 0 ? p_i47667_3_.height : 1;
        this.prevWindowX = this.windowX = this.monitor.getVirtualPosX() + lvt_6_1_.getWidth() / 2 - this.windowWidth / 2;
        this.prevWindowY = this.windowY = this.monitor.getVirtualPosY() + lvt_6_1_.getHeight() / 2 - this.windowHeight / 2;
        GLFW.glfwDefaultWindowHints();
        this.windowHandle = GLFW.glfwCreateWindow(this.windowWidth, this.windowHeight, "Blockadia", this.fullscreen ? this.monitor.getMonitorPointer() : 0L, 0L);
        this.updateWindowMonitor();
        GLFW.glfwMakeContextCurrent(this.windowHandle);
        GL.createCapabilities();
        this.updateVideoMode();
        this.updateFramebufferSize();
//        this.loadIcon();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public boolean isFullscreen() {
        return this.fullscreen;
    }

    public int getWindowWidth() {
        return this.windowWidth;
    }

    public int getWindowHeight() {
        return this.windowHeight;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int getScaledHeight() {
        return this.scaledHeight;
    }

    public int getWindowX() {
        return this.windowX;
    }

    public int getWindowY() {
        return this.windowY;
    }

    public double getGuiScaleFactor() {
        return this.guiScaleFactor;
    }

    void setRenderPhase(String p_setRenderPhase_1_) {
        this.renderPhase = p_setRenderPhase_1_;
    }

    private void setThrowExceptionOnGlError() {
        GLFW.glfwSetErrorCallback(Window::throwExceptionForGlError);
    }

    private static void throwExceptionForGlError(int p_throwExceptionForGlError_0_, long p_throwExceptionForGlError_1_) {
        throw new IllegalStateException("GLFW error " + p_throwExceptionForGlError_0_ + ": " + MemoryUtil.memUTF8(p_throwExceptionForGlError_1_));
    }

    void logGlError(int p_logGlError_1_, long p_logGlError_2_) {
        String lvt_4_1_ = MemoryUtil.memUTF8(p_logGlError_2_);
        LOGGER.error("########## GL ERROR ##########");
        LOGGER.error("@ {}", this.renderPhase);
        LOGGER.error("{}: {}", p_logGlError_1_, lvt_4_1_);
    }

    void setLogOnGlError() {
        GLFW.glfwSetErrorCallback(this.loggingErrorCallback).free();
    }

    public void updateVsyncFromGameSettings() {
        GLFW.glfwSwapInterval(this.gameEngine.gameSettings.enableVsync ? 1 : 0);
    }

    public void close() {
        Util.nanoTimeSupplier = System::nanoTime;
        Callbacks.glfwFreeCallbacks(this.windowHandle);
        GLFW.glfwDestroyWindow(this.windowHandle);
        GLFW.glfwTerminate();
    }

    private void updateWindowMonitor() {
        this.monitor = this.virtualScreen.getWindowMonitor(this);
    }

    private void onWindowPosUpdate(long p_onWindowPosUpdate_1_, int p_onWindowPosUpdate_3_, int p_onWindowPosUpdate_4_) {
        this.windowX = p_onWindowPosUpdate_3_;
        this.windowY = p_onWindowPosUpdate_4_;
        this.updateWindowMonitor();
    }

    private void updateFramebufferSize() {
        int[] lvt_1_1_ = new int[1];
        int[] lvt_2_1_ = new int[1];
        GLFW.glfwGetFramebufferSize(this.windowHandle, lvt_1_1_, lvt_2_1_);
        width = lvt_1_1_[0];
        height = lvt_2_1_[0];
    }

    private void onWindowSizeUpdate(long p_onWindowSizeUpdate_1_, int p_onWindowSizeUpdate_3_, int p_onWindowSizeUpdate_4_) {
        this.windowWidth = p_onWindowSizeUpdate_3_;
        this.windowHeight = p_onWindowSizeUpdate_4_;
        this.updateWindowMonitor();
    }

    public Optional<VideoMode> getVideoMode() {
        return this.videoMode;
    }

    public int getVideoModeIndex() {
        return this.videoMode.isPresent() ? this.monitor.getVideoModeOrDefaultIndex(this.videoMode) + 1 : 0;
    }

    public String getVideoModeString(int p_getVideoModeString_1_) {
        if (this.monitor.getVideoModeCount() <= p_getVideoModeString_1_) {
            p_getVideoModeString_1_ = this.monitor.getVideoModeCount() - 1;
        }

        return this.monitor.getVideoModeFromIndex(p_getVideoModeString_1_).toString();
    }

    public void setVideoModeFromIndex(int p_setVideoModeFromIndex_1_) {
        Optional<VideoMode> lvt_2_1_ = this.videoMode;
        if (p_setVideoModeFromIndex_1_ == 0) {
            this.videoMode = Optional.empty();
        } else {
            this.videoMode = Optional.of(this.monitor.getVideoModeFromIndex(p_setVideoModeFromIndex_1_ - 1));
        }

        if (!this.videoMode.equals(lvt_2_1_)) {
            this.videoModeChanged = true;
        }

    }

    public void func_198097_f() {
        if (this.fullscreen && this.videoModeChanged) {
            this.videoModeChanged = false;
            this.updateVideoMode();
        }

    }

    private void updateVideoMode() {
        boolean lvt_1_1_ = GLFW.glfwGetWindowMonitor(this.windowHandle) != 0L;
        VideoMode lvt_2_1_;
        if (this.fullscreen) {
            lvt_2_1_ = this.monitor.getVideoModeOrDefault(this.videoMode);
            if (!lvt_1_1_) {
                this.prevWindowX = this.windowX;
                this.prevWindowY = this.windowY;
                this.prevWindowWidth = this.windowWidth;
                this.prevWindowHeight = this.windowHeight;
            }

            this.windowX = 0;
            this.windowY = 0;
            this.windowWidth = lvt_2_1_.getWidth();
            this.windowHeight = lvt_2_1_.getHeight();
            GLFW.glfwSetWindowMonitor(this.windowHandle, this.monitor.getMonitorPointer(), this.windowX, this.windowY, this.windowWidth, this.windowHeight, lvt_2_1_.getRefreshRate());
        } else {
            lvt_2_1_ = this.monitor.getDefaultVideoMode();
            this.windowX = this.prevWindowX;
            this.windowY = this.prevWindowY;
            this.windowWidth = this.prevWindowWidth;
            this.windowHeight = this.prevWindowHeight;
            GLFW.glfwSetWindowMonitor(this.windowHandle, 0L, this.windowX, this.windowY, this.windowWidth, this.windowHeight, -1);
        }

    }

    public void toggleFullscreen() {
        this.fullscreen = !this.fullscreen;
        this.gameEngine.gameSettings.fullScreen = this.fullscreen;
    }

    public long getWindowHandle() {
        return windowHandle;
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

    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
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

    public static class WindowOptions {

        public boolean cullFace;

        public boolean showTriangles;

        public boolean showFps;

        public boolean compatibleProfile;

        public boolean antialiasing;

        public boolean frustumCulling;
    }

    public static void func_211162_a(BiConsumer<Integer, String> p_211162_0_) {
        MemoryStack lvt_1_1_ = MemoryStack.stackPush();
        Throwable var2 = null;

        try {
            PointerBuffer lvt_3_1_ = lvt_1_1_.mallocPointer(1);
            int lvt_4_1_ = GLFW.glfwGetError(lvt_3_1_);
            if (lvt_4_1_ != 0) {
                long lvt_5_1_ = lvt_3_1_.get();
                String lvt_7_1_ = lvt_5_1_ != 0L ? MemoryUtil.memUTF8(lvt_5_1_) : "";
                p_211162_0_.accept(lvt_4_1_, lvt_7_1_);
            }
        } catch (Throwable var15) {
            var2 = var15;
            throw var15;
        } finally {
            if (lvt_1_1_ != null) {
                if (var2 != null) {
                    try {
                        lvt_1_1_.close();
                    } catch (Throwable var14) {
                        var2.addSuppressed(var14);
                    }
                } else {
                    lvt_1_1_.close();
                }
            }

        }

    }

    public void setupOverlayRendering() {
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double) getWidth() / this.getGuiScaleFactor(), (double) getHeight() / this.getGuiScaleFactor(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }

    private void loadIcon() {
        try {
            MemoryStack lvt_1_1_ = MemoryStack.stackPush();
            Throwable var2 = null;

            try {
                InputStream lvt_3_1_ = new FileInputStream(Utils.loadResource(new Identifier("icons/icon_16x16.png").toString()));
                Throwable var4 = null;

                try {
                    InputStream lvt_5_1_ = new FileInputStream(Utils.loadResource(new Identifier("icons/icon_32x32.png").toString()));
                    Throwable var6 = null;

                    try {

                        IntBuffer lvt_7_1_ = lvt_1_1_.mallocInt(1);
                        IntBuffer lvt_8_1_ = lvt_1_1_.mallocInt(1);
                        IntBuffer lvt_9_1_ = lvt_1_1_.mallocInt(1);
                        GLFWImage.Buffer lvt_10_1_ = GLFWImage.mallocStack(2, lvt_1_1_);
                        ByteBuffer lvt_11_1_ = this.func_198111_a(lvt_3_1_, lvt_7_1_, lvt_8_1_, lvt_9_1_);
                        if (lvt_11_1_ == null) {
                            throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
                        }

                        lvt_10_1_.position(0);
                        lvt_10_1_.width(lvt_7_1_.get(0));
                        lvt_10_1_.height(lvt_8_1_.get(0));
                        lvt_10_1_.pixels(lvt_11_1_);
                        ByteBuffer lvt_12_1_ = this.func_198111_a(lvt_5_1_, lvt_7_1_, lvt_8_1_, lvt_9_1_);
                        if (lvt_12_1_ == null) {
                            throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
                        }

                        lvt_10_1_.position(1);
                        lvt_10_1_.width(lvt_7_1_.get(0));
                        lvt_10_1_.height(lvt_8_1_.get(0));
                        lvt_10_1_.pixels(lvt_12_1_);
                        lvt_10_1_.position(0);
                        org.lwjgl.glfw.GLFW.glfwSetWindowIcon(this.windowHandle, lvt_10_1_);
                        STBImage.stbi_image_free(lvt_11_1_);
                        STBImage.stbi_image_free(lvt_12_1_);
                    } catch (Throwable var58) {
                        var6 = var58;
                        throw var58;
                    } finally {
                        if (lvt_5_1_ != null) {
                            if (var6 != null) {
                                try {
                                    lvt_5_1_.close();
                                } catch (Throwable var57) {
                                    var6.addSuppressed(var57);
                                }
                            } else {
                                lvt_5_1_.close();
                            }
                        }

                    }
                } catch (Throwable var60) {
                    var4 = var60;
                    throw var60;
                } finally {
                    if (lvt_3_1_ != null) {
                        if (var4 != null) {
                            try {
                                lvt_3_1_.close();
                            } catch (Throwable var56) {
                                var4.addSuppressed(var56);
                            }
                        } else {
                            lvt_3_1_.close();
                        }
                    }

                }
            } catch (Throwable var62) {
                var2 = var62;
                throw var62;
            } finally {
                if (lvt_1_1_ != null) {
                    if (var2 != null) {
                        try {
                            lvt_1_1_.close();
                        } catch (Throwable var55) {
                            var2.addSuppressed(var55);
                        }
                    } else {
                        lvt_1_1_.close();
                    }
                }

            }
        } catch (Exception var64) {
            LOGGER.error("Couldn't set icon", var64);
        }

    }

    @Nullable
    private ByteBuffer func_198111_a(InputStream p_198111_1_, IntBuffer p_198111_2_, IntBuffer p_198111_3_, IntBuffer p_198111_4_) throws IOException {
        ByteBuffer lvt_5_1_ = null;

        ByteBuffer var6;
        try {
            lvt_5_1_ = func_195724_a(p_198111_1_);
            lvt_5_1_.rewind();
            var6 = STBImage.stbi_load_from_memory(lvt_5_1_, p_198111_2_, p_198111_3_, p_198111_4_, 0);
        } finally {
            if (lvt_5_1_ != null) {
                MemoryUtil.memFree(lvt_5_1_);
            }

        }

        return var6;
    }

    public static ByteBuffer func_195724_a(InputStream p_195724_0_) throws IOException {
        ByteBuffer lvt_1_2_;
        if (p_195724_0_ instanceof FileInputStream) {
            FileInputStream lvt_2_1_ = (FileInputStream)p_195724_0_;
            FileChannel lvt_3_1_ = lvt_2_1_.getChannel();
            lvt_1_2_ = MemoryUtil.memAlloc((int)lvt_3_1_.size() + 1);

            while(true) {
                if (lvt_3_1_.read(lvt_1_2_) != -1) {
                    continue;
                }
            }
        } else {
            lvt_1_2_ = MemoryUtil.memAlloc(8192);
            ReadableByteChannel lvt_2_2_ = Channels.newChannel(p_195724_0_);

            while(lvt_2_2_.read(lvt_1_2_) != -1) {
                if (lvt_1_2_.remaining() == 0) {
                    lvt_1_2_ = MemoryUtil.memRealloc(lvt_1_2_, lvt_1_2_.capacity() * 2);
                }
            }
        }

        return lvt_1_2_;
    }

}