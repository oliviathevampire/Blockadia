package net.thegaminghuskymc.sandboxgame.modding;

import net.thegaminghuskymc.sandboxgame.Logger;
import net.thegaminghuskymc.sandboxgame.Taskable;
import net.thegaminghuskymc.sandboxgame.GameEngine;
import net.thegaminghuskymc.sandboxgame.managers.ResourceManager;
import net.thegaminghuskymc.sgf.fml.common.SidedProxy;
import net.thegaminghuskymc.sgf.fml.common.event.FMLInitializationEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLPostInitializationEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;

public class Mod {

    private IMod mod;
    private ModInfo modInfo;
    private SidedProxy sidedProxy;
    private ArrayList<IModResource> modResources;

    public Mod(IMod mod, ModInfo modInfo) {
        this.mod = mod;
        this.modInfo = modInfo;
        this.modResources = new ArrayList<>();
        mod.addResources(modResources.get(modResources.size()));
    }

    public IMod getMod() {
        return (this.mod);
    }

    public IModResource addResource(IModResource resource) {
        this.modResources.add(resource);
        return (resource);
    }

    /**
     * called when resources of this mod should be loaded
     */
    public void loadResources(ResourceManager manager) {
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
    public void unloadResources(ResourceManager manager) {
        Logger.get().log(Logger.Level.FINE, "Unloading resources: " + this.toString());

        for (IModResource resource : this.modResources) {
            Logger.get().log(Logger.Level.FINE, "Resource: " + resource.getClass().getSimpleName());
            resource.load(this, manager);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Mod(");
        builder.append(this.modInfo.name());
        builder.append(";");
        builder.append(this.modInfo.owner());
        builder.append(";");
        builder.append(this.modInfo.version());
        builder.append(";");
        builder.append(this.sidedProxy.clientSide());
        builder.append(";");
        builder.append(this.sidedProxy.serverSide());
        builder.append(")");
        return (builder.toString());
    }

    public void preInit(FMLPreInitializationEvent event) {
        Logger.get().log(Logger.Level.FINE, "Pre-Initializing: " + this.toString());
        this.mod.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        Logger.get().log(Logger.Level.FINE, "Initializing: " + this.toString());
        this.mod.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        Logger.get().log(Logger.Level.FINE, "Post-Initializing: " + this.toString());
        this.mod.postInit(event);
    }

    public void getTasks(ArrayList<GameEngine.Callable<Taskable>> tasks) {
    }

}