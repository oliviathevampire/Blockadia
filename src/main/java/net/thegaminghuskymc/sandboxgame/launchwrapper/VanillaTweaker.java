package net.thegaminghuskymc.sandboxgame.launchwrapper;

import net.thegaminghuskymc.sandboxgame.launchwrapper.ITweaker;

import java.io.File;
import java.util.List;

public class VanillaTweaker implements ITweaker {
    private List<String> args;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args = args;
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.registerTransformer("net.thegaminghuskymc.sandboxgame.launchwrapper.injector.VanillaTweakInjector");
    }

    @Override
    public String getLaunchTarget() {
        return "net.thegaminghuskymc.sandboxgame.testmod.client.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return args.toArray(new String[args.size()]);
    }
}