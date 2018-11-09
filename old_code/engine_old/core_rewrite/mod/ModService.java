package team.hdt.blockadia.old_engine_code_1.core_rewrite.mod;

import team.hdt.blockadia.old_engine_code_1.core_rewrite.Blockadia;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * This is a dummy class, for testing purposes. Do not touch.
 * 
 * @author Hypeirochus
 *
 */
public class ModService {

    private static ModService service;
    private ServiceLoader<IMod> loader;

    private ModService() {
    }

    public static synchronized ModService getInstance() {
        if (service == null) {
            service = new ModService();
        }
        return service;
    }
    
    protected static void setLoader(ServiceLoader<IMod> loader) {
    	ModService.getInstance().loader = loader;
    }

    public void preInit() {
    	int counter = 0;
        try {
            Iterator<IMod> mods = loader.iterator();
            Blockadia.logger().info("Pre initializing mods...");
            while (mods.hasNext()) {
            	IMod mod = mods.next();
            	mod.preInit();
            	counter++;
            }
            Blockadia.logger().info(counter + " mod(s) pre initialized.");
        } catch (ServiceConfigurationError serviceError) {
            Blockadia.logger().error("Failed to load a mod during pre initialization.");
            serviceError.printStackTrace();
        }
    }

    public void init() {
    	int counter = 0;
        try {
            Iterator<IMod> mods = loader.iterator();
            Blockadia.logger().info("Initializing mods...");
            while (mods.hasNext()) {
            	IMod mod = mods.next();
            	mod.init();
            	counter++;
            }
            Blockadia.logger().info(counter + " mod(s) initialized.");
        } catch (ServiceConfigurationError serviceError) {
            Blockadia.logger().error("Failed to load a mod during initialization.");
            serviceError.printStackTrace();
        }
    }
    
    public void postInit() {
    	int counter = 0;
        try {
            Iterator<IMod> mods = loader.iterator();
            Blockadia.logger().info("Post initializing mods...");
            while (mods.hasNext()) {
            	IMod mod = mods.next();
            	mod.postInit();
            	counter++;
            }
            Blockadia.logger().info(counter + " mod(s) post initialized.");
        } catch (ServiceConfigurationError serviceError) {
            Blockadia.logger().error("Failed to load a mod during post initialization.");
            serviceError.printStackTrace();
        }
    }
}