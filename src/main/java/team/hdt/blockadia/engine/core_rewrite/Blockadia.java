package team.hdt.blockadia.engine.core_rewrite;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.engine.core_rewrite.game.entity.EntityBat;
import team.hdt.blockadia.engine.core_rewrite.game.entity.EntityFirefly;
import team.hdt.blockadia.engine.core_rewrite.game.entity.EntityPlayer;
import team.hdt.blockadia.engine.core_rewrite.game.entity.item.EntityItem;
import team.hdt.blockadia.engine.core_rewrite.game.state.GameStateManager;
import team.hdt.blockadia.engine.core_rewrite.gfx.GlWrapper;
import team.hdt.blockadia.engine.core_rewrite.gfx.post.PostProcessing;
import team.hdt.blockadia.engine.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.engine.core_rewrite.gfx.texture.TextureManager;
import team.hdt.blockadia.engine.core_rewrite.handler.Items;
import team.hdt.blockadia.engine.core_rewrite.handler.Tiles;
import team.hdt.blockadia.engine.core_rewrite.keybind.KeybindManager;
import team.hdt.blockadia.engine.core_rewrite.mod.EntityRegistry;
import team.hdt.blockadia.engine.core_rewrite.mod.ModLoader;
import team.hdt.blockadia.engine.core_rewrite.mod.ModService;
import team.hdt.blockadia.engine.core_rewrite.object.Camera;
import team.hdt.blockadia.engine.core_rewrite.util.Identifier;
import team.hdt.blockadia.engine.core_rewrite.util.Loader;
import team.hdt.blockadia.engine.core_rewrite.util.LoadingUtils;
import team.hdt.blockadia.engine.core_rewrite.util.Timer;
import team.hdt.blockadia.engine.core_rewrite.util.thread.ThreadPool;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Blockadia implements Runnable {

    public static final String TITLE = "Blockadia";
    public static final String VERSION = "0.0.1";

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;

    private static Logger logger = LogManager.getLogger("Blockadia");
    private static Blockadia instance = new Blockadia();
    private static final Timer TIMER = new Timer(60);
    private static int lastFPS = 0;

    private TextureManager textureManager;
    private MasterRenderer renderer;
    private Camera camera;
    private ThreadPool pool;

    private boolean running;

    private Blockadia() {
    }

    /**
     * Sets the game's running status to true.
     */
    public synchronized void start() {
        if (running)
            return;

        logger.info("Running game...");
        running = true;
    }

    /**
     * Sets the game's running status to false.
     */
    public synchronized void stop() {
        if (running)
            return;

        running = false;
    }

    /**
     * Initializes the game.
     *
     * @throws Exception
     *             May be unable to load missing world files.
     */
    private void init() {
        logger.info("Creating display...");
        Display.createDisplay(TITLE + " v" + VERSION, WIDTH, HEIGHT);
        Display.setIcon(LoadingUtils.loadImage("icon", new Identifier("icons/32.png").getInputStream()));

        GlWrapper.enableDepth();
        GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        PostProcessing.init();

        textureManager = new TextureManager();
        renderer = new MasterRenderer();
        camera = new Camera();
        pool = new ThreadPool(4);

        ModLoader.loadMods();
        ModService.getInstance().preInit();

        /**Zerra Pre-Initialization*/
        EntityRegistry.register(new Identifier("player"), EntityPlayer.class);
        EntityRegistry.register(new Identifier("firefly"), EntityFirefly.class);
        EntityRegistry.register(new Identifier("item"), EntityItem.class);
        EntityRegistry.register(new Identifier("bat"), EntityBat.class);

        ModService.getInstance().init();
        Tiles.registerTiles();
        Items.registerItems();

        /**Zerra initialization*/
        GameStateManager.init();

        ModService.getInstance().postInit();

        logger.info("Loading game...");
        /** This is where game loading is first called. */
        load();
    }

    /**
     * This is the GAME LOOP. This regulates the game cycling. DO NOT TOUCH.
     */
    @Override
    public void run() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long timer = System.currentTimeMillis();
        int frames = 0;

        while (true) {
            try {
                while (running) {
                    if (!Display.isCloseRequested())
                        Display.update();
                    else
                        running = false;

                    TIMER.updateTimer();

                    for (int i = 0; i < Math.min(10, TIMER.elapsedTicks); ++i) {
                        update();
                    }

                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                    render();
                    frames++;

                    if (System.currentTimeMillis() - timer > 1000) {
                        timer += 1000;
                        // logger.info("World Finished Loading: " + Game.worldFinishedLoading + " --- fps: " + frames);
                        Display.setTitle(TITLE + " v" + VERSION + " | fps: " + frames);
                        if (frames - lastFPS < -30) {
                            logger.warn("Lag spike detected in the game!");
                        }
                        Blockadia.lastFPS = frames;
                        frames = 0;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                stop();
            }
            cleanUp();
            break;
        }
    }

    /**
     * Updates game logic and events.
     */
    private void update() {
        GameStateManager.update();
        camera.update();

        if (Display.isKeyPressed(GLFW.GLFW_KEY_MINUS)) {
            renderer.setScale(MasterRenderer.scale - 1f);
        }

        if (Display.isKeyPressed(GLFW.GLFW_KEY_EQUAL)) {
            renderer.setScale(MasterRenderer.scale + 1f);
        }
        if (MasterRenderer.scale < 3)
            renderer.setScale(3);

        this.updateJoystick();
    }

    /**
     * Renders game.
     */
    private void render() {
        GameStateManager.render(renderer, camera, Display.getMouseX() / MasterRenderer.scale, Display.getMouseY() / MasterRenderer.scale, TIMER.renderPartialTicks);
        renderer.render(camera, Display.getMouseX() / MasterRenderer.scale, Display.getMouseY() / MasterRenderer.scale, TIMER.renderPartialTicks);
    }

    /**
     * Does all the updating and detection necessary for joystick support.
     */
    private void updateJoystick() {
        if (Display.isJoystickPresent()) {
            FloatBuffer axes = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_1);
            if (Objects.requireNonNull(axes).capacity() >= 2) {
                float xDir = axes.get(0);
                float yDir = axes.get(1);
                this.onJoystickMoved(xDir, -yDir);
            }
        }
    }

    /**
     * Called when a key is pressed.
     *
     * @param keyCode
     *            The code of the key pressed
     */
    public void onKeyPressed(int keyCode) {
        KeybindManager.onKeyPressed(keyCode);
        GameStateManager.onKeyPressed(keyCode);
    }

    /**
     * Called when a key is released.
     *
     * @param keyCode
     *            The code of the key released
     */
    public void onKeyReleased(int keyCode) {
        KeybindManager.onKeyReleased(keyCode);
        GameStateManager.onKeyReleased(keyCode);
    }

    /**
     * Called when a mouse button is pressed.
     *
     * @param mouseX
     *            The x position of the mouse
     * @param mouseY
     *            The y position of the mouse
     * @param mouseButton
     *            The button pressed
     */
    public void onMousePressed(double mouseX, double mouseY, int mouseButton) {
        GameStateManager.onMousePressed(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when a mouse button is released.
     *
     * @param mouseX
     *            The x position of the mouse
     * @param mouseY
     *            The y position of the mouse
     * @param mouseButton
     *            The button released
     */
    public void onMouseReleased(double mouseX, double mouseY, int mouseButton) {
        GameStateManager.onMouseReleased(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when the mouse is scrolled.
     *
     * @param mouseX
     *            The x position of the mouse
     * @param mouseY
     *            The y position of the mouse
     * @param scroll
     *            The amount scrolled
     */
    public void onMouseScrolled(double mouseX, double mouseY, double scroll) {
        GameStateManager.onMouseScrolled(mouseX, mouseY, scroll);
    }

    /**
     * Called when the joystick is moved if one is present.
     *
     * @param xDirection
     *            The x axes of the joystick
     * @param yDirection
     *            The y axes of the joystick
     */
    public void onJoystickMoved(double xDirection, double yDirection) {
        GameStateManager.onJoystickMoved(xDirection, yDirection);
    }

    /**
     * @return The logger for the game.
     */
    public static Logger logger() {
        return logger;
    }

    /**
     * @return The instance for the game.
     */
    public static Blockadia getInstance() {
        return instance;
    }

    /**
     * Cleans up the game before the game loop has closed.
     */
    private void cleanUp() {
        this.addTask(this::save);
        Display.destroy();
        logger.info("Closing Current Processes");
        StopWatch watch = StopWatch.createStarted();
        pool.join();
        logger.info("Closed Processes in " + watch.getTime(TimeUnit.MILLISECONDS) + "ms");
        logger.info("Cleaning up resources");
        PostProcessing.cleanUp();
        Loader.cleanUp();
        renderer.cleanUp();
        logger.info("Closing game...");
        System.exit(0);
    }

    /**
     * Saves current game.
     */
    private void save() {
        File saveFolder = new File("run");
        if (!saveFolder.exists()) {
            saveFolder.mkdirs();
        }
        GameStateManager.save(saveFolder);
    }

    /**
     * Loads up the game.
     */
    private void load() {
        File saveFolder = new File("run");
        if (saveFolder.exists()) {
            GameStateManager.load(saveFolder);
        } else {
            saveFolder.mkdirs();
        }
    }

    /**
     * Adds a task to the thread pool.
     *
     * @param task
     *            The task to add.
     */
    public void addTask(Runnable task) {
        pool.addScheduledTask(task);
    }

    /**
     * @return The partial ticks of the game. Useful for smooth rendering.
     */
    public float getRenderPartialTicks() {
        return TIMER.renderPartialTicks;
    }

    /**
     * @return The texture manager for the game.
     */
    public TextureManager getTextureManager() {
        return textureManager;
    }

    /**
     * @return The master renderer for the game.
     */
    public MasterRenderer getRenderer() {
        return renderer;
    }

    /**
     * @return The camera for the game.
     */
    public Camera getCamera() {
        return camera;
    }


    /**
     * The main method. This is where it all began...
     *
     * @param args
     *            You know what these do... or don't. Don't think it really matters, to be honest.
     */
    public static void main(String[] args) {
        logger.info("Setting up game...");
        new Thread(Blockadia.getInstance(), "main").start();
        Blockadia.getInstance().start();
    }

}
