package net.thegaminghuskymc.sandboxgame.engine.modding;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;

import java.util.ArrayList;

public class Mod {

    private IMod mod;
    private ModInfo modInfo;
    private ArrayList<IModResource> modResources;

    public Mod(IMod mod, ModInfo modInfo) {
        this.mod = mod;
        this.modInfo = modInfo;
        this.modResources = new ArrayList<>();
    }

    public IMod getMod() {
        return (this.mod);
    }

    public void addResource(IModResource resource) {
        this.modResources.add(resource);
    }

    /**
     * called when resources of this mod should be loaded
     */
    void loadResources(ResourceManager manager) {
        Logger.get().log(Logger.Level.FINE, "Loading resources: " + this.toString());

        Logger.get().indent(1);
        for (IModResource resource : this.modResources) {
            Logger.get().log(Logger.Level.FINE, "Resource: " + resource.getClass().getSimpleName());
            Logger.get().indent(1);
            resource.load(this, manager);
            Logger.get().indent(-1);
        }
        Logger.get().indent(-1);
    }

    /**
     * called when resources of this mod should be loaded
     */
    void unloadResources(ResourceManager manager) {
        Logger.get().log(Logger.Level.FINE, "Unloading resources: " + this.toString());

        for (IModResource resource : this.modResources) {
            Logger.get().log(Logger.Level.FINE, "Resource: " + resource.getClass().getSimpleName());
            resource.load(this, manager);
        }
    }

    @Override
    public String toString() {
        return ("Mod(" +
                "Name: " + this.modInfo.name() + ";" +
                " Creator: " + this.modInfo.creator() + ";" +
                " Version: " + this.modInfo.version() +
                ")");
    }

    public void initialize() {
        Logger.get().log(Logger.Level.FINE, "Initializing: " + this.toString());
        this.mod.initialize(this);
    }

    public void deinitialize() {
        Logger.get().log(Logger.Level.FINE, "DeInitializing: " + this.toString());
        this.mod.deinitialize(this);
    }

}