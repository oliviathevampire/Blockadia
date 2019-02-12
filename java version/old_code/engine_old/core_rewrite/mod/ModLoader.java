package team.hdt.blockadia.engine.core_rewrite.mod;

import team.hdt.blockadia.engine.core_rewrite.Blockadia;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ModLoader {

	public static void loadMods() {
		File modsFolder = new File("data/mods");
		if (!modsFolder.exists()) {
			modsFolder.mkdirs();
		}

		List<URL> modLocations = new ArrayList<URL>();
		File[] modFiles = modsFolder.listFiles();
		for (int i = 0; i < modFiles.length; i++) {
			try {
				loadLibrary(modFiles[i]);
				modLocations.add(modFiles[i].toURI().toURL());
			} catch (MalformedURLException e) {
				Blockadia.logger().warn("Could not load mod file " + modFiles[i]);
				e.printStackTrace();
			}
		}

		ServiceLoader<IMod> mods = ServiceLoader.load(IMod.class, new URLClassLoader(modLocations.toArray(new URL[0])));
		for (IMod mod : mods) {
			if (!isModCompadable(mod)) {
				Blockadia.logger().fatal("Mod \'" + mod.getModName() + " v" + mod.getModVersion() + "\' is not compabable with \'" + Blockadia.TITLE + " v" + Blockadia.VERSION + "\'");
				System.exit(-1);
			}
			Blockadia.logger().info("Successfully Loaded mod " + mod.getModName() + " v" + mod.getModVersion());
		}
		ModService.setLoader(mods);
	}

	private static synchronized void loadLibrary(java.io.File jar) {
		try {
			/* We are using reflection here to circumvent encapsulation; addURL is not public */
			URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			URL url = jar.toURI().toURL();
			/* Disallow if already loaded */
			for (URL it : loader.getURLs()) {
				if (it.equals(url)) {
					return;
				}
			}
			java.lang.reflect.Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true); /* promote the method to public access */
			method.invoke(loader, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isModCompadable(IMod mod) {
		if (mod == null)
			return false;
		String[] thisParts = Blockadia.VERSION.split("\\.");
		String[] thatParts = mod.getGameVersionForMod().split("\\.");
		for (int i = 0; i < 2; i++) {
			int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
			int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
			if (thisPart < thatPart)
				return false;
			if (thisPart > thatPart)
				return false;
		}
		return true;
	}
}