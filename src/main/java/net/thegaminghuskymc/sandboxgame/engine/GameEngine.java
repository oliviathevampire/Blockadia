package net.thegaminghuskymc.sandboxgame.engine;

import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModLoader;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.ResourcePack;
import net.thegaminghuskymc.sandboxgame.game.Main;
import net.thegaminghuskymc.sandboxgame.game.mod.DefaultMod;

import java.io.File;
import java.util.*;

public class GameEngine implements Runnable {

    /** version */
    public static final String VERSION = "0.0.1";
//    public static final String MOD_ID = "Husky's Sandbox Game";

    public static GameEngine INSTANCE;

    public enum Side {
        CLIENT, SERVER, BOTH;

        public boolean match(Side side) {
            return (this == side || side == BOTH || this == BOTH);
        }
    }

    public abstract class Callable<T> implements java.util.concurrent.Callable<T> {
        @Override
        public abstract T call() throws Exception;

        public abstract String getName();
    }

    /** the tasks to run each frames */
    private ArrayList<Callable<Taskable>> tasks;

    /** the resources directory */
    private File gameDir;
    private ArrayList<ResourcePack> assets;

    /** side of the running engine */
    protected Side side;

    /** bools */
    private boolean isRunning;
    private boolean debug;

    /** Mod loader */
    private ModLoader modLoader;


    /** Resources */
    protected ResourceManager resources;

    /** random number generator */
    private Random rng;

    /** Timer */
    private Timer timer;

    /**
     * config hashmap, key: filepath, value: config file
     */
    private HashMap<String, Config> config;

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;

    private final Window window;

    private final Thread gameLoopThread;

    private final IGameLogic gameLogic;

    private final MouseInput mouseInput;

    private double lastFps;
    
    private int fps;
    
    private String windowTitle;
    
    public GameEngine(String windowTitle, boolean vSync, Window.WindowOptions opts, IGameLogic gameLogic) throws Exception {
        this(windowTitle, 400, 400, vSync, opts, gameLogic);
    }

    public GameEngine(String windowTitle, int width, int height, boolean vSync, Window.WindowOptions opts, IGameLogic gameLogic) {
        this.windowTitle = windowTitle;
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync, opts);
        mouseInput = new MouseInput();
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    /** allocate the engine */
    public final void initialize() {

        Logger.get().log(Logger.Level.FINE, "Initializing engine...");

        this.debug(true);

        this.timer = new Timer();
        this.rng = new Random();

        // assets
        this.loadGamedir();
        this.assets = new ArrayList<ResourcePack>();


        // config
        this.config = new HashMap<String, Config>();
//        this.loadConfig(Main.MODID, R.getResPath("config.json"));

        this.modLoader = new ModLoader();
        this.tasks = new ArrayList<Callable<Taskable>>(256);

        // inject default mod
//        this.modLoader.injectMod(DefaultMod.class);

        this.onInitialized();

        Logger.get().log(Logger.Level.FINE, "Common Engine initialized!");
    }

    /** load game directory */
    private final void loadGamedir() {
        String OS = (System.getProperty("os.name")).toUpperCase();
        String gamepath;
        if (OS.contains("WIN")) {
            gamepath = System.getenv("AppData");
        } else {
            gamepath = System.getProperty("user.home");
        }

        if (!gamepath.endsWith("/")) {
            gamepath = gamepath + "/";
        }
        this.gameDir = new File(gamepath + "Husky-s-Sandbox-Game");

        Logger.get().log(Logger.Level.FINE, "Game directory is: " + this.gameDir.getAbsolutePath());
        if (!this.gameDir.exists()) {
            this.gameDir.mkdirs();
        } else if (!this.gameDir.canWrite()) {
            this.gameDir.setWritable(true);
        }
    }

    /** load config */
    private final Config loadConfig(String id, String filepath) {
        if (this.config.containsKey(id)) {
            return (this.config.get(id));
        }
        Config cfg = new Config(filepath);
        this.config.put(id, cfg);
        Logger.get().log(Logger.Level.FINE, "Loading config", filepath);
        cfg.load();
        return (cfg);
    }

    /** get a config */
    public final Config getConfig(String filepath) {
        return (this.config.get(filepath));
    }

    /** get every configs */
    public final HashMap<String, Config> getConfigs() {
        return (this.config);
    }

    protected void onInitialized() {

    };

    /** deallocate the engine properly */
    public final void deinitialize() {

        Logger.get().log(Logger.Level.FINE, "Deinitializing engine...");

        this.stopRunning();

        if (this.resources == null) {
            Logger.get().log(Logger.Level.WARNING, "Tried to stop an unstarted / already stopped engine! Cancelling");
            return;
        }

        Logger.get().log(Logger.Level.FINE, "Saving configs");
        for (Map.Entry<String, Config> entry : this.config.entrySet()) {
            Config cfg = entry.getValue();
            Logger.get().log(Logger.Level.FINE, "\t\t", entry.getKey(), cfg.getFilepath());
            cfg.save();
        }

        this.modLoader.deinitialize(this.resources);
        this.modLoader = null;

        Logger.get().log(Logger.Level.FINE, "Stopped");
    }

    /** load resources */
    public void load() {
        this.loadResources("./mods", "./mod", "./plugin", "./plugins");
    }

    /** reload every game resources */
    public final void reload(String... folders) {
        this.unload();
        this.load();
    }

    private final void unload() {
        this.resources.unload();
    }

    private final void loadResources(String... folders) {

        for (String folder : folders) {
            this.modLoader.injectMods(folder);
        }

        this.resources.load();
    }

    /**
     * make the engine loop
     *
     * @throws InterruptedException
     */
    public final void loop() throws InterruptedException {

        this.isRunning = true;

//        this.executor = Executors.newFixedThreadPool(8);
//        this.invokeEvent(this.eventPreLoop);

        while (this.isRunning()) {
//            this.timer.update();
//            this.invokeEvent(this.eventOnLoop);
//            this.updateTasks();
        }

//        this.invokeEvent(this.eventPostLoop);
    }

    /*private final void updateTasks() {

        // clear tasks
        this.tasks.clear();
        for (World world : this.loadedWorlds) {
            world.getTasks(this, this.tasks);
        }
        this.invokeEvent(new EventGetTasks(this, this.tasks));
        this.runTasks();
    }

    private final void runTasks() {

        // run tasks and get their results
        List<Future<Taskable>> results;
        try {
            results = this.executor.invokeAll(this.tasks, 2, TimeUnit.SECONDS);
        } catch (InterruptedException e1) {
            return;
        }

        // check that each tasks worked properly (none crashed basically)
        if (this.debug()) {

            // for each tasks
            int i;
            for (i = 0; i < results.size(); i++) {
                Future<Taskable> result = results.get(i);
                String task = this.tasks.get(i).getName();
                try {
                    // try to get it
                    if (!result.isCancelled() && result.get() != null) {
                        // task ran properly
                    } else {
                        Logger.get().log(Logger.Level.ERROR, "Task cancelled (timeout): ", task);
                    }
                } catch (Exception e) {
                    // if get() failed, then an error occured
                    Logger.get().log(Logger.Level.ERROR, "Exception occured when executing task", task);
                    e.printStackTrace(Logger.get().getPrintStream());
                }
            }
        }
    }*/

    public final Timer getTimer() {
        return (this.timer);
    }

    /*protected final void invokeEvent(Event event) {
        this.getResourceManager().getEventManager().invokeEvent(event);
    }

    protected final void registerEventCallback(EventListener<?> eventCallback) {
        this.getResourceManager().getEventManager().addListener(eventCallback);
    }*/

    /** request the game to stop */
    public final void stopRunning() {
        this.isRunning = false;
    }

    /** get the mod loader */
    public final ModLoader getModLoader() {
        return (this.modLoader);
    }

    /** run the garbage collector */
    public final void runGC() {
        Runtime.getRuntime().gc();
    }

    /** return true if the voxel engine is running */
    public final boolean isRunning() {
        return (this.isRunning);
    }

    /** get the rng */
    public final Random getRNG() {
        return (this.rng);
    }

    public static GameEngine instance() {
        return (INSTANCE);
    }

    /** get the side on which the engine is running */
    public final Side getSide() {
        return (instance().side);
    }

    /** enable or disable debug (note: debug on may influence performances) */
    public void debug(boolean value) {
        this.debug = value;
    }

    public boolean debug() {
        return (this.debug);
    }

    /** return the game main directory */
    public final File getGamedir() {
        return (this.gameDir);
    }

    /** add an assets pack (zip file) to be added to the game */
    public ResourcePack putAssets(ResourcePack pack) {
        for (ResourcePack tmppack : this.assets) {
            if (tmppack.getModID().equals(pack.getModID())) {
                Logger.get().log(Logger.Level.ERROR,
                        "Tried to put an assets pack which already exists. Canceling operation. ModID: "
                                + pack.getModID());
                return (null);
            }
        }
        this.assets.add(pack);
//        pack.extract();
        return (pack);
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        mouseInput.init(window);
        gameLogic.init(window);
        lastFps = timer.getTime();
        fps = 0;
    }

    protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if ( !window.isvSync() ) {
                sync();
            }
        }
    }

    protected void cleanup() {
        gameLogic.cleanup();
    }
    
    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    protected void input() {
        mouseInput.input(window);
        gameLogic.input(window, mouseInput);
    }

    protected void update(float interval) {
        gameLogic.update(interval, mouseInput, window);
    }

    protected void render() {
        if ( window.getWindowOptions().showFps && timer.getLastLoopTime() - lastFps > 1 ) {
            lastFps = timer.getLastLoopTime();
            window.setWindowTitle(windowTitle + " - " + fps + " FPS");
            fps = 0;
        }
        fps++;
        gameLogic.render(window);
        window.update();
    }

}
