package team.hdt.blockadia.test.engine;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import team.hdt.blockadia.game_engine_old.client.rendering.GameConfiguration;
import team.hdt.blockadia.game_engine_old.client.rendering.GlStateManager;
import team.hdt.blockadia.game_engine_old.client.rendering.OpenGlHelper;
import team.hdt.blockadia.game_engine_old.client.rendering.VirtualScreen;
import team.hdt.blockadia.game_engine_old.common.GameSettings;
import team.hdt.blockadia.game_engine_old.common.chrash.CrashReport;
import team.hdt.blockadia.game_engine_old.common.chrash.CrashReportCategory;
import team.hdt.blockadia.game_engine_old.common.chrash.ReportedException;
import team.hdt.blockadia.game_engine_old.common.init.Bootstrap;
import team.hdt.blockadia.game_engine_old.common.util.DefaultUncaughtExceptionHandler;
import team.hdt.blockadia.game_engine_old.common.util.Util;
import team.hdt.blockadia.test.game.DummyGame;
import team.hdt.blockadia.test.game.IGameLogic;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class GameEngine implements Runnable {

    private Logger LOGGER = LogManager.getLogger();

    public static final int WIDTH = 856, HEIGHT = 480;

    public static byte[] memoryReserve;
    private static int cachedMaximumTextureSize;
    private final File fileResourcepacks;
    private VirtualScreen virtualScreen;
    public Window mainWindow;
    private boolean hasCrashed;
    private CrashReport crashReporter;

    public static final int TARGET_FPS = 75;

    private volatile boolean running = true;
    public GameSettings gameSettings;
    private final GameConfiguration.DisplayInformation displayInfo;
    public final File gameDir;
    private final File fileAssets;
    private final IGameLogic gameLogic;
    private final String launchedVersion;
    private final String versionType;
    public static final int TARGET_UPS = 30;
    private final Timer timer;
    private final Queue<FutureTask<?>> scheduledTasks = Queues.newConcurrentLinkedQueue();
    private final Thread thread = Thread.currentThread();
    private final MouseInput mouseInput;
    private double lastFps;
    private int fps;

    public GameEngine(GameConfiguration p_i45547_1_) {
        this.gameLogic = new DummyGame();
        this.displayInfo = p_i45547_1_.displayInfo;
        this.gameDir = p_i45547_1_.folderInfo.gameDir;
        this.fileAssets = p_i45547_1_.folderInfo.assetsDir;
        this.fileResourcepacks = p_i45547_1_.folderInfo.resourcePacksDir;
        this.launchedVersion = p_i45547_1_.gameInfo.version;
        this.versionType = p_i45547_1_.gameInfo.versionType;
        mouseInput = new MouseInput();
        timer = new Timer();
        Bootstrap.register();
    }

    public void run() {
        this.running = true;

        CrashReport lvt_2_2_;
        try {
            this.init();
        } catch (Throwable var10) {
            lvt_2_2_ = CrashReport.makeCrashReport(var10, "Initializing game");
            lvt_2_2_.makeCategory("Initialization");
            this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(lvt_2_2_));
            return;
        }

        try {
            try {
                while(this.running) {
                    if (this.hasCrashed && this.crashReporter != null) {
                        this.displayCrashReport(this.crashReporter);
                        return;
                    }
                    try {
                        this.runGameLoop();
                    } catch (OutOfMemoryError var9) {
                        this.freeMemory();
                        System.gc();
                    }
                }
                return;
            } catch (ReportedException var11) {
                this.addGraphicsAndWorldToCrashReport(var11.getCrashReport());
                this.freeMemory();
                LOGGER.fatal("Reported exception thrown!", var11);
                this.displayCrashReport(var11.getCrashReport());
            } catch (Throwable var12) {
                lvt_2_2_ = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var12));
                this.freeMemory();
                LOGGER.fatal("Unreported exception thrown!", var12);
                this.displayCrashReport(lvt_2_2_);
            }

        } finally {
            this.shutdownMinecraftApplet();
        }
    }

    private void init() {
        try {
            gameLogic.init(mainWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.gameSettings = new GameSettings(this, this.gameDir);
        this.startTimerHackThread();
        LOGGER.info("LWJGL Version: {}", Version.getVersion());
        GameConfiguration.DisplayInformation lvt_1_1_ = this.displayInfo;
        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
            lvt_1_1_ = new GameConfiguration.DisplayInformation(this.gameSettings.overrideWidth, this.gameSettings.overrideHeight, lvt_1_1_.fullscreenWidth, lvt_1_1_.fullscreenHeight, lvt_1_1_.fullscreen);
        }

        this.checkForGLFWInitError();
        this.virtualScreen = new VirtualScreen(this);
        this.mainWindow = this.virtualScreen.createWindow(this, lvt_1_1_, "");
        OpenGlHelper.initializeTextures();
        this.mainWindow.setRenderPhase("Startup");
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7425);
        GlStateManager.clearDepth(1.0D);
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.cullFace(GlStateManager.CullFace.BACK);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        this.mainWindow.setRenderPhase("Post startup");
        GlStateManager.viewport(0, 0, Window.getWidth(), Window.getHeight());
        GLFW.glfwSetErrorCallback(this::disableVSyncAfterGlError).free();
        if (this.gameSettings.fullScreen && !this.mainWindow.isFullscreen()) {
            this.mainWindow.toggleFullscreen();
        }

        this.mainWindow.updateVsyncFromGameSettings();
        this.mainWindow.setLogOnGlError();
    }

    private void disableVSyncAfterGlError(int p_disableVSyncAfterGlError_1_, long p_disableVSyncAfterGlError_2_) {
        this.gameSettings.enableVsync = false;
        this.gameSettings.saveOptions();
    }

    private void checkForGLFWInitError() {
        Window.func_211162_a((p_211108_0_, p_211108_1_) -> {
            throw new IllegalStateException(String.format("GLFW error before init: [0x%X]%s", p_211108_0_, p_211108_1_));
        });
        List<String> lvt_1_1_ = Lists.newArrayList();
        GLFWErrorCallback lvt_2_1_ = GLFW.glfwSetErrorCallback((p_211100_1_, p_211100_2_) -> {
            lvt_1_1_.add(String.format("GLFW error during init: [0x%X]%s", p_211100_1_, p_211100_2_));
        });
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join(lvt_1_1_));
        } else {
            Util.nanoTimeSupplier = () -> (long)(GLFW.glfwGetTime() * 1.0E9D);
            Iterator var3 = lvt_1_1_.iterator();

            while(var3.hasNext()) {
                String lvt_4_1_ = (String)var3.next();
                LOGGER.error("GLFW error collected during initialization: {}", lvt_4_1_);
            }

            GLFW.glfwSetErrorCallback(lvt_2_1_).free();
        }
    }

    public void crashed(CrashReport p_crashed_1_) {
        this.hasCrashed = true;
        this.crashReporter = p_crashed_1_;
    }

    public GameEngine getGameEngine() {
        return this;
    }

    public void displayCrashReport(CrashReport p_displayCrashReport_1_) {
        File lvt_2_1_ = new File(getGameEngine().gameDir, "crash-reports");
        File lvt_3_1_ = new File(lvt_2_1_, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
        Bootstrap.printToSYSOUT(p_displayCrashReport_1_.getCompleteReport());
        if (p_displayCrashReport_1_.getFile() != null) {
            Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + p_displayCrashReport_1_.getFile());
            System.exit(-1);
        } else if (p_displayCrashReport_1_.saveToFile(lvt_3_1_)) {
            Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + lvt_3_1_.getAbsolutePath());
            System.exit(-1);
        } else {
            Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }

    private void initMainWindow() {
        this.mainWindow.setupOverlayRendering();
    }

    public void shutdownMinecraftApplet() {
        try {
            LOGGER.info("Stopping!");
        } finally {
            this.virtualScreen.close();
            this.mainWindow.close();
            if (!this.hasCrashed) {
                System.exit(0);
            }
        }
        System.gc();
    }

    private void runGameLoop() {
        this.mainWindow.setRenderPhase("Pre render");
        Util.nanoTime();
        if (GLFW.glfwWindowShouldClose(this.mainWindow.getWindowHandle())) {
            this.shutdown();
        }
        this.mainWindow.setRenderPhase("Render");
        GLFW.glfwPollEvents();
        GlStateManager.pushMatrix();
        gameLogic.render(mainWindow);
        GlStateManager.clear(16640);
        GlStateManager.enableTexture2D();
        Thread.yield();
        this.mainWindow.setRenderPhase("Post render");
    }

    public void freeMemory() {
        try {
            memoryReserve = new byte[0];
        } catch (Throwable var3) {
        }

        try {
            System.gc();
        } catch (Throwable var2) {
        }

        System.gc();
    }

    public void shutdown() {
        this.running = false;
        gameLogic.cleanup();
    }

    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport p_addGraphicsAndWorldToCrashReport_1_) {
        CrashReportCategory lvt_2_1_ = p_addGraphicsAndWorldToCrashReport_1_.getCategory();
        lvt_2_1_.addDetail("Launched Version", () -> this.launchedVersion);
        lvt_2_1_.addDetail("LWJGL", Version::getVersion);
        lvt_2_1_.addDetail("OpenGL", () -> GLFW.glfwGetCurrentContext() == 0L ? "NO CONTEXT" : GlStateManager.glGetString(7937) + " GL version " + GlStateManager.glGetString(7938) + ", " + GlStateManager.glGetString(7936));
        lvt_2_1_.addDetail("GL Caps", OpenGlHelper::getLogText);
        lvt_2_1_.addDetail("Using VBOs", () -> this.gameSettings.useVbo ? "Yes" : "No");
        lvt_2_1_.addCrashSection("Type", "Client (map_client.txt)");
        lvt_2_1_.addDetail("Resource Packs", () -> {
            StringBuilder lvt_1_1_ = new StringBuilder();

            for (Object lvt_3_1_ : this.gameSettings.resourcePacks) {
                if (lvt_1_1_.length() > 0) {
                    lvt_1_1_.append(", ");
                }

                lvt_1_1_.append(lvt_3_1_);
                if (this.gameSettings.incompatibleResourcePacks.contains(lvt_3_1_)) {
                    lvt_1_1_.append(" (incompatible)");
                }
            }

            return lvt_1_1_.toString();
        });
        lvt_2_1_.addDetail("CPU", OpenGlHelper::getCpu);

        return p_addGraphicsAndWorldToCrashReport_1_;
    }

    public File getFileResourcePacks() {
        return this.fileResourcepacks;
    }

    public <V> ListenableFuture<V> addScheduledTask(Callable<V> p_addScheduledTask_1_) {
        Validate.notNull(p_addScheduledTask_1_);
        if (this.isCallingFromMinecraftThread()) {
            try {
                return Futures.immediateFuture(p_addScheduledTask_1_.call());
            } catch (Exception var3) {
                return Futures.immediateFailedCheckedFuture(var3);
            }
        } else {
            ListenableFutureTask<V> lvt_2_2_ = ListenableFutureTask.create(p_addScheduledTask_1_);
            this.scheduledTasks.add(lvt_2_2_);
            return lvt_2_2_;
        }
    }

    public ListenableFuture<Object> addScheduledTask(Runnable p_addScheduledTask_1_) {
        Validate.notNull(p_addScheduledTask_1_);
        return this.addScheduledTask(Executors.callable(p_addScheduledTask_1_));
    }

    public boolean isCallingFromMinecraftThread() {
        return Thread.currentThread() == this.thread;
    }

    static {
        memoryReserve = new byte[10485760];
        cachedMaximumTextureSize = -1;
    }

    private void startTimerHackThread() {
        Thread lvt_1_1_ = new Thread("Timer hack thread") {
            public void run() {
                while(GameEngine.this.running) {
                    try {
                        Thread.sleep(2147483647L);
                    } catch (InterruptedException ignored) {

                    }
                }

            }
        };
        lvt_1_1_.setDaemon(true);
        lvt_1_1_.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        lvt_1_1_.start();
    }

}