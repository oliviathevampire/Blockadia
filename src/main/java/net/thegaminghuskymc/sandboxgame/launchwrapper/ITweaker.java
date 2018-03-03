package net.thegaminghuskymc.sandboxgame.launchwrapper;

import net.thegaminghuskymc.sandboxgame.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.util.List;

public interface ITweaker {

    void acceptOptions(List<String> args, File gameDir, final File assetsDir, String profile);

    void injectIntoClassLoader(LaunchClassLoader classLoader);

    String getLaunchTarget();

    String[] getLaunchArguments();

}