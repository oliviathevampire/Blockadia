package net.thegaminghuskymc.sandboxgame.engine.managers;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class ResourceManager {

//	private WorldManager worldManager;

	private BlockManager blockManager;

	/*private ItemManager itemManager;

	private PacketManager packetManager;

	private EntityManager entityManager;

	private EventManager eventManager;*/

	private LangManager langManager;

	/** managers as a list */
	private ArrayList<GenericManager<?>> managers;

	private GameEngine engine;

	/** configuration */
	private Config config;

	private static ResourceManager RESOURCE_MANAGER_INSTANCE;

	public ResourceManager(GameEngine engine) {
		RESOURCE_MANAGER_INSTANCE = this;
		this.engine = engine;
	}

	protected void addManagers() {

		/*this.eventManager = new EventManager(this);
		this.addManager(this.eventManager);

		this.worldManager = new WorldManager(this);
		this.addManager(this.worldManager);*/

		this.blockManager = new BlockManager(this);
		this.addManager(this.blockManager);

		/*this.itemManager = new ItemManager(this);
		this.addManager(this.itemManager);

		this.entityManager = new EntityManager(this);
		this.addManager(this.entityManager);

		this.packetManager = new PacketManager(this);
		this.addManager(this.packetManager);*/

		this.langManager = new LangManager(this);
		this.addManager(this.langManager);
	}

	public void addManager(GenericManager<?> manager) {
		this.managers.add(manager);
	}

	/** engine on which this resource manager correspond to */
	public GameEngine getEngine() {
		return (this.engine);
	}

	public static ResourceManager instance() {
		return (RESOURCE_MANAGER_INSTANCE);
	}

	public void preInit() {

	}

	public void init() {
        Logger.get().log(Logger.Level.FINE, "* Initializing resources manager");

        this.managers = new ArrayList<GenericManager<?>>();
        this.addManagers();

        // create config
        this.config = new Config();

        for (GenericManager<?> manager : this.managers) {
            Logger.get().log(Logger.Level.FINE, manager.getClass().getSimpleName());
            manager.initialize();
        }
	}

	public void postInit() {
        Logger.get().log(Logger.Level.FINE, "* Deinitializing resources manager");
        for (int i = this.managers.size() - 1; i >= 0; i--) {
            GenericManager<?> manager = this.managers.get(i);
            Logger.get().log(Logger.Level.FINE, manager.getClass().getSimpleName());
            manager.deinitialize();
        }
        this.managers = null;
	}

	/** deinitilize every game resources */
	public final void load() {
		Logger.get().log(Logger.Level.FINE, "* Loading resources manager");
		for (GenericManager<?> manager : this.managers) {
			Logger.get().log(Logger.Level.FINE, manager.getClass().getSimpleName());
			manager.load();
		}
	}

	/** deinitilize every game resources */
	public final void unload() {
		Logger.get().log(Logger.Level.FINE, "* Unload resources manager");
		for (GenericManager<?> manager : this.managers) {
			Logger.get().log(Logger.Level.FINE, manager.getClass().getSimpleName());
			manager.deinitialize();
		}
	}

	/*public final WorldManager getWorldManager() {
		return (this.worldManager);
	}*/

	public final LangManager getLangManager() {
		return (this.langManager);
	}

	public final BlockManager getBlockManager() {
		return (this.blockManager);
	}

	/*public ItemManager getItemManager() {
		return (this.itemManager);
	}

	public final PacketManager getPacketManager() {
		return (this.packetManager);
	}

	public final EntityManager getEntityManager() {
		return (this.entityManager);
	}

	public final EventManager getEventManager() {
		return (this.eventManager);
	}*/

	/** get the resource filepath */
	public String getResourcePath(String modid, String path) {
		String assetsdir = "assets" + File.separator;
		String respath = this.getEngine().getGamedir().getAbsolutePath();
		int length = respath.length() + assetsdir.length() + modid.length() + path.length() + 1;
		StringBuilder builder = new StringBuilder(length);
		builder.append(respath);
		if (!respath.endsWith(File.separator)) {
			builder.append(File.separator);
		}
		builder.append(assetsdir);
		builder.append(modid);
		if (!modid.endsWith(File.separator)) {
			builder.append(File.separator);
		}
		builder.append(path);
		Logger.get().log(Logger.Level.FINE, builder.toString().replace("/", File.separator));
		return (builder.toString().replace("/", File.separator));
	}

	// config file constants
	private static final char COMMENT_CHAR = '#';

	public static HashMap<String, String> getConfigFile(String filepath, int defaultcapacity) {
		return (getConfigFile(filepath, new HashMap<String, String>(defaultcapacity)));
	}

	/**
	 * a function which parse the given file and return a hashmap containing
	 * 'key-values'
	 */
	private static HashMap<String, String> getConfigFile(String filepath, HashMap<String, String> map) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				++i;

				line = line.trim();
				int index = line.indexOf(COMMENT_CHAR);
				if (index == 0) {
					continue;
				}
                @NotNull String data;

				data = index == -1 ? line : line.substring(0, index - 1);
				if (data.length() > 0) {
					String[] strs = data.split("=", 0);
					if (strs.length < 2) {
						Logger.get().log(Logger.Level.DEBUG,
								"line malformation: l " + i + " : `" + line + "` in file " + filepath);
						continue;
					}
					String value = strs[strs.length - 1].trim();
					for (int j = 0; j < strs.length - 1; j++) {
						map.put(strs[j].trim(), value);
					}
				}
			}
			reader.close();

		} catch (IOException e) {
			Logger.get().log(Logger.Level.ERROR, "Couldnt read config file: " + filepath);
		}
		return (map);
	}

	private static boolean fileExists(String filepath) {
		return (new File(filepath).exists());
	}

	/** export the map to the given filepath */
	private static void exportConfigFile(String filepath, HashMap<String, String> map) {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
			ArrayList<String> keys = new ArrayList<String>(map.keySet());
			Collections.sort(keys);
			for (String key : keys) {
				writer.append(key);
				writer.append('=');
				writer.append(map.get(key));
				writer.newLine();
			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			Logger.get().log(Logger.Level.ERROR, "Couldnt read config file: " + filepath);
		}

	}

	/** get the config informations */
	public Config getConfig() {
		return (this.config);
	}

	public class Config {

		/** config file name */
		public static final String CONFIG_FILE = ".config";

		/** config **/
		private HashMap<String, String> config;

		public Config() {

			this.config = new HashMap<String, String>(1024);

			// configuration stuff
			String configpath = R.getResPath(CONFIG_FILE);
			if (ResourceManager.fileExists(configpath)) {
				ResourceManager.getConfigFile(configpath, this.config);
			} else {
				ResourceManager.exportConfigFile(configpath, this.config);
			}
		}

		/** get a config value */
		public String get(String key, String default_value) {
			String value = this.config.get(key);
			return (value == null ? default_value : value);
		}

		/** set a new config value */
		public void set(String key, String value) {
			this.config.put(key, value);
		}

		public boolean isSet(String key) {
			return (this.config.get(key) != null);
		}
	}
}
