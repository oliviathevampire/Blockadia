package net.thegaminghuskymc.sandboxgame;

import net.thegaminghuskymc.sandboxgame.Config;
import net.thegaminghuskymc.sandboxgame.Logger;
import net.thegaminghuskymc.sandboxgame.Logger.Level;
import net.thegaminghuskymc.sandboxgame.Taskable;
import net.thegaminghuskymc.sandboxgame.Timer;
import net.thegaminghuskymc.sandboxgame.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.modding.ModLoader;
import net.thegaminghuskymc.sandboxgame.packets.INetwork;
import net.thegaminghuskymc.sandboxgame.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.resourcepacks.ResourcePack;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.game.mod.DefaultMod;
import net.thegaminghuskymc.sgf.fml.common.Loader;
import net.thegaminghuskymc.sgf.fml.common.eventhandler.EventBus;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class GameEngine {

    public static final EventBus EVENT_BUS = new EventBus();
    public static final EventBus TERRAIN_GEN_BUS = new EventBus();
    public static final EventBus ORE_GEN_BUS = new EventBus();
    public static final String MC_VERSION = Loader.MC_VERSION;
    private static final String MOD_ID = "sandbox_game";
    private static GameEngine INSTANCE;
    protected Side side;
    protected ResourceManager resources;
    private ArrayList<Callable<Taskable>> tasks;
    private ExecutorService executor;
    private File gameDir;
    private ArrayList<ResourcePack> assets;
    private boolean isRunning;
    private boolean debug;
    private ModLoader modLoader;
    private INetwork network;
    private net.thegaminghuskymc.sandboxgame.Timer timer;
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

        this.timer = new net.thegaminghuskymc.sandboxgame.Timer();

        // assets
        this.loadGamedir();
        this.assets = new ArrayList<>();

        this.resources = this.instanciateResourceManager();
        this.resources.initialize();

        // config
        Config config1 = new Config(R.getResPath(MOD_ID, "config.json"));
        config1.getString("graphicMode", "fancy");
        this.config = new HashMap<>();
        this.config.getOrDefault("graphicMode", config1);

        this.modLoader = new ModLoader();
        this.tasks = new ArrayList<>(256);

        // inject default mod
        this.modLoader.injectMod(DefaultMod.class);

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
        this.gameDir = new File(gamepath + "Husky's Sandbox Game");

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

    public String getModId() {
        return MOD_ID;
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
        this.loadResources("./mods", "./mod", "./plugin", "./plugins", "./resource_packs");
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

        while (this.isRunning()) {
            this.timer.update();
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
     * return true if the voxel engine is running
     */
    private boolean isRunning() {
        return (this.isRunning);
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
    public final void loadWorld(int worldID) {
        World world = this.getResourceManager().getWorldManager().getWorld(worldID);
        if (world == null) {
            Logger.get().log(Level.ERROR, "Tried to load an unknown world, with id", worldID);
            return;
        }
        if (this.loadedWorlds.contains(world)) {
            Logger.get().log(Level.ERROR, "Tried to load an already-loaded world, with id", worldID);
            return;
        }
        this.loadedWorlds.add(world);
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

    public abstract class Callable<T> implements java.util.concurrent.Callable<T> {
        @Override
        public abstract T call() throws Exception;

        public abstract String getName();
    }

}
