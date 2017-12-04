package net.thegaminghuskymc.sandboxgame.engine;

import net.thegaminghuskymc.sandboxgame.engine.Logger.Level;
import net.thegaminghuskymc.sandboxgame.engine.events.*;
import net.thegaminghuskymc.sandboxgame.engine.events.EventListener;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.ModLoader;
import net.thegaminghuskymc.sandboxgame.engine.packets.INetwork;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.ResourcePack;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.game.mod.DefaultMod;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class GameEngine {

    /**
     * version
     */
    public static final String VERSION = "0.0.1";
    public static final String MOD_ID = "game_engine";

    /**
     * singleton
     */
    private static GameEngine INSTANCE;
    /**
     * side of the running engine
     */
    protected Side side;
    /**
     * Resources
     */
    protected ResourceManager resources;
    /**
     * the tasks to run each frames
     */
    private ArrayList<Callable<Taskable>> tasks;

    /**
     * executor service
     */
    private ExecutorService executor;

    /**
     * the resources directory
     */
    private File gameDir;
    private ArrayList<ResourcePack> assets;
    /**
     * bools
     */
    private boolean isRunning;
    private boolean debug;
    /**
     * Mod loader
     */
    private ModLoader modLoader;
    /**
     * networking
     */
    private INetwork network;
    /**
     * random number generator
     */
    private Random rng;
    /**
     * Timer
     */
    private Timer timer;
    /**
     * events
     */
    private EventPreLoop eventPreLoop;
    private EventOnLoop eventOnLoop;
    private EventPostLoop eventPostLoop;
    /**
     * loaded worlds
     */
    private ArrayList<World> loadedWorlds;
    /**
     * config hashmap, key: filepath, value: config file
     */
    private HashMap<String, Config> config;

    public GameEngine(Side side) {

        Logger.get().log(Level.FINE, "Starting common Engine!");

        INSTANCE = this;
        this.side = side;
    }

    public static GameEngine instance() {
        return (INSTANCE);
    }

    /**
     * allocate the engine
     */
    public final void initialize() {

        Logger.get().log(Level.FINE, "Initializing engine...");

        this.debug(true);

        this.timer = new Timer();
        this.rng = new Random();

        // assets
        this.loadGamedir();
        this.assets = new ArrayList<>();

        this.resources = this.instanciateResourceManager();
        this.resources.initialize();

        // config
        this.config = new HashMap<>();
        this.loadConfig(MOD_ID, R.getResPath("config.json"));

        this.modLoader = new ModLoader();
        this.tasks = new ArrayList<>(256);

        // inject default mod
        this.modLoader.injectMod(DefaultMod.class);

        // events
        this.eventPreLoop = new EventPreLoop(this);
        this.eventOnLoop = new EventOnLoop(this);
        this.eventPostLoop = new EventPostLoop(this);

        // worlds
        this.loadedWorlds = new ArrayList<>();

        this.onInitialized();

        Logger.get().log(Level.FINE, "Common Engine initialized!");
    }

    /**
     * load game directory
     */
    private void loadGamedir() {
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
        this.gameDir = new File(gamepath + "Game Engine");

        Logger.get().log(Level.FINE, "Game directory is: " + this.gameDir.getAbsolutePath());
        if (!this.gameDir.exists()) {
            this.gameDir.mkdirs();
        } else if (!this.gameDir.canWrite()) {
            this.gameDir.setWritable(true);
        }
    }

    /**
     * load config
     */
    private Config loadConfig(String id, String filepath) {
        if (this.config.containsKey(id)) {
            return (this.config.get(id));
        }
        Config cfg = new Config(filepath);
        this.config.put(id, cfg);
        Logger.get().log(Level.FINE, "Loading config", filepath);
        cfg.load();
        return (cfg);
    }

    /**
     * get a config
     */
    public final Config getConfig(String filepath) {
        return (this.config.get(filepath));
    }

    /**
     * get every configs
     */
    public final HashMap<String, Config> getConfigs() {
        return (this.config);
    }

    protected abstract void onInitialized();

    /**
     * deallocate the engine properly
     */
    public final void deinitialize() {

        Logger.get().log(Level.FINE, "Deinitializing engine...");

        this.stopRunning();

        if (this.resources == null) {
            Logger.get().log(Level.WARNING, "Tried to stop an unstarted / already stopped engine! Cancelling");
            return;
        }

        Logger.get().log(Level.FINE, "Saving configs");
        for (Map.Entry<String, Config> entry : this.config.entrySet()) {
            Config cfg = entry.getValue();
            Logger.get().log(Level.FINE, "\t\t", entry.getKey(), cfg.getFilepath());
            cfg.save();
        }

        this.stopExecutor();
        this.resources.deinitialize();
        this.resources = null;

        this.modLoader.deinitialize(this.resources);
        this.modLoader = null;

        if (this.network != null) {
            this.network.stop();
            this.network = null;
        }
        Logger.get().log(Level.FINE, "Stopped");

        this.onDeinitialized();
    }

    protected abstract void onDeinitialized();

    protected abstract ResourceManager instanciateResourceManager();

    /**
     * load resources
     */
    public void load() {
        this.loadResources("./mods", "./mod", "./plugin", "./plugins");
    }

    /**
     * reload every game resources
     */
    public final void reload(String... folders) {
        this.unload();
        this.load();
    }

    private void unload() {
        this.resources.unload();
        this.modLoader.unload(this.getResourceManager());
    }

    private void loadResources(String... folders) {

        for (String folder : folders) {
            this.modLoader.injectMods(folder);
        }

        this.modLoader.load(this.getResourceManager());
        this.resources.load();
    }

    /**
     * make the engine loop
     *
     * @throws InterruptedException
     */
    public final void loop() throws InterruptedException {

        this.isRunning = true;

        this.executor = Executors.newFixedThreadPool(8);
        this.invokeEvent(this.eventPreLoop);

        while (this.isRunning()) {
            this.timer.update();
            this.invokeEvent(this.eventOnLoop);
            this.updateTasks();
        }

        this.invokeEvent(this.eventPostLoop);
    }

    private void updateTasks() {

        // clear tasks
        this.tasks.clear();
        for (World world : this.loadedWorlds) {
            world.getTasks(this, this.tasks);
        }
        this.invokeEvent(new EventGetTasks(this, this.tasks));
        this.runTasks();
    }

    private void runTasks() {

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
                        Logger.get().log(Level.ERROR, "Task cancelled (timeout): ", task);
                    }
                } catch (Exception e) {
                    // if get() failed, then an error occured
                    Logger.get().log(Level.ERROR, "Exception occured when executing task", task);
                    e.printStackTrace(Logger.get().getPrintStream());
                }
            }
        }
    }

    /**
     * stop the thread executor
     */
    private void stopExecutor() {
        if (this.executor == null) {
            return;
        }
        Logger.get().log(Level.DEBUG, "Stopping thread executor...");
        this.executor.shutdown();
        try {
            this.executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Logger.get().log(Level.DEBUG, "Thread executor timeout: " + e.getLocalizedMessage());
        }
    }

    public final Timer getTimer() {
        return (this.timer);
    }

    protected void invokeEvent(Event event) {
        this.getResourceManager().getEventManager().invokeEvent(event);
    }

    protected void registerEventCallback(EventListener<?> eventCallback) {
        this.getResourceManager().getEventManager().addListener(eventCallback);
    }

    /**
     * request the game to stop
     */
    public void stopRunning() {
        this.isRunning = false;
    }

    /**
     * return the main resource manager
     */
    public abstract <T extends ResourceManager> T getResourceManager();

    /**
     * get the mod loader
     */
    public ModLoader getModLoader() {
        return (this.modLoader);
    }

    /**
     * run the garbage collector
     */
    public final void runGC() {
        Runtime.getRuntime().gc();
    }

    /**
     * return true if the voxel engine is running
     */
    private boolean isRunning() {
        return (this.isRunning);
    }

    /**
     * get the rng
     */
    public final Random getRNG() {
        return (this.rng);
    }

    /**
     * get the side on which the engine is running
     */
    public final Side getSide() {
        return (instance().side);
    }

    /**
     * enable or disable debug (note: debug on may influence performances)
     */
    public void debug(boolean value) {
        this.debug = value;
    }

    public boolean debug() {
        return (this.debug);
    }

    /**
     * add a world to the game logic loop
     */
    public final World loadWorld(int worldID) {
        World world = this.getResourceManager().getWorldManager().getWorld(worldID);
        if (world == null) {
            Logger.get().log(Level.ERROR, "Tried to load an unknown world, with id", worldID);
            return (null);
        }
        if (this.loadedWorlds.contains(world)) {
            Logger.get().log(Level.ERROR, "Tried to load an already-loaded world, with id", worldID);
            return (null);
        }
        this.loadedWorlds.add(world);
        world.load();
        return (world);
    }

    /**
     * remove a world from the game logic loop
     */
    public final void unloadWorld(int worldID) {
        World world = this.getResourceManager().getWorldManager().getWorld(worldID);
        if (world == null) {
            Logger.get().log(Level.ERROR, "Tried to unload an unknown world, with id", worldID);
            return;
        }
        this.loadedWorlds.remove(world);
    }

    /**
     * get the current world of the client (can be null)
     */
    public World getWorld(int worldID) {
        return (this.getResourceManager().getWorldManager().getWorld(worldID));
    }

    /**
     * return the game main directory
     */
    public final File getGamedir() {
        return (this.gameDir);
    }

    /**
     * add an assets pack (zip file) to be added to the game
     */
    public ResourcePack putAssets(ResourcePack pack) {
        for (ResourcePack tmppack : this.assets) {
            if (tmppack.getModID().equals(pack.getModID())) {
                Logger.get().log(Level.ERROR,
                        "Tried to put an assets pack which already exists. Canceling operation. ModID: "
                                + pack.getModID());
                return (null);
            }
        }
        this.assets.add(pack);
        pack.extract();
        return (pack);
    }

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

}
