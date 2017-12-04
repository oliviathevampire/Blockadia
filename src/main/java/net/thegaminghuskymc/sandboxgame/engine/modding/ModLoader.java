package net.thegaminghuskymc.sandboxgame.engine.modding;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModLoader {

    /**
     * every mods
     */
    private ArrayList<Mod> mods;

    public ModLoader() {
        this.mods = new ArrayList<>();
    }

    public ArrayList<Mod> getMods() {
        return (this.mods);
    }

    /**
     * find mods into the given folder and try to load it
     */
    public void injectMods(String filepath) {
        File folder = new File(filepath);

        if (!folder.exists()) {
            Logger.get().log(Logger.Level.WARNING, "Mod folder doesnt exists: " + filepath);
            return;
        }

        if (!folder.isDirectory()) {
            Logger.get().log(Logger.Level.WARNING, "Mod folder ... isnt a folder? " + filepath);
            return;
        }

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                this.injectMods(file.getAbsolutePath());
                continue;
            }

            try {
                this.loadMod(file);
            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace(Logger.get().getPrintStream());
            }
        }
    }

    /**
     * load a mod from the given file (which should be a JarFile)
     */
    @SuppressWarnings({"resource"})
    private void loadMod(File file) throws ClassNotFoundException, IOException {
        JarFile jar = new JarFile(file.getAbsolutePath());
        Enumeration<JarEntry> entries = jar.entries();
        URL[] urls = {new URL("jar:file:" + file.getAbsolutePath() + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.getName().endsWith(".class")) {
                String clazzname = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                Class<?> modClass = cl.loadClass(clazzname);
                this.injectMod(modClass);
            }
        }
    }

    /**
     * INJECT A MOD INTO THE ENGINE;
     *
     * @param modClass : the mod class, which should implement the interface
     *                 "IMod.class" and the annotation "ModInfo.class"
     * @return true if the mod could be injected
     * @throws ClassNotFoundException
     */
    public boolean injectMod(Class<?> modClass) {

        Class<?>[] interfaces = modClass.getInterfaces();
        boolean isMod = false;
        for (Class<?> inter : interfaces) {
            if (inter == IMod.class) {
                isMod = true;
                break;
            }
        }

        if (!isMod) {
            Logger.get().log(Logger.Level.ERROR, modClass.getSimpleName(),
                    "doesn't implement IMod interface! It can't be registered has a mod.");
            return (false);
        }

        if (!modClass.isAnnotationPresent(ModInfo.class)) {
            Logger.get().log(Logger.Level.ERROR, modClass.getSimpleName(),
                    "doesn't implement the ModInfo annotation. It can't be registered has a mod.");
            return (false);
        }

        ModInfo modInfo = modClass.getAnnotation(ModInfo.class);
        GameEngine.Side engineSide = GameEngine.instance().getSide();

        if (!modInfo.clientProxy().isEmpty() && engineSide.match(GameEngine.Side.CLIENT)) {
            try {
                modClass = Class.forName(modInfo.clientProxy());
            } catch (ClassNotFoundException e) {
                e.printStackTrace(Logger.get().getPrintStream());
                Logger.get().log(Logger.Level.ERROR, "Couldn't find client proxy class file", modInfo.clientProxy());
            }
        } else if (!modInfo.serverProxy().isEmpty() && engineSide.match(GameEngine.Side.SERVER)) {
            // LOAD IT SERVER SIDE
            try {
                modClass = Class.forName(modInfo.serverProxy());
            } catch (ClassNotFoundException e) {
                e.printStackTrace(Logger.get().getPrintStream());
                Logger.get().log(Logger.Level.ERROR, "Couldn't find server proxy class file", modInfo.serverProxy());
            }
        }

        try {
            IMod imod = (IMod) modClass.newInstance();
            Mod mod = new Mod(imod, modInfo);
            mod.initialize();
            this.mods.add(mod);
            Logger.get().log(Logger.Level.FINE, "Adding mod", mod);
            return (true);
        } catch (Exception e) {
            e.printStackTrace(Logger.get().getPrintStream());
            return (false);
        }
    }

    /**
     * deinitialize every mods and clean the mod list
     */
    public void deinitialize(ResourceManager manager) {
        for (Mod mod : this.mods) {
            mod.deinitialize();
        }
        this.mods.clear();
    }

    /**
     * load every mod resources
     */
    public void load(ResourceManager manager) {
        for (Mod mod : this.mods) {
            mod.loadResources(manager);
        }
    }

    public void unload(ResourceManager manager) {
        for (Mod mod : this.mods) {
            mod.unloadResources(manager);
        }
    }

}
