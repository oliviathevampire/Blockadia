package net.thegaminghuskymc.sgf.fml.relauncher;

import net.thegaminghuskymc.sandboxgame.GameEngine;
import net.thegaminghuskymc.sandboxgame.launchwrapper.LaunchClassLoader;

import java.io.File;

public class FMLLaunchHandler
{
    private static FMLLaunchHandler INSTANCE;
    static GameEngine.Side side;
    private LaunchClassLoader classLoader;
    private FMLTweaker tweaker;
    private File minecraftHome;

    public static void configureForClientLaunch(LaunchClassLoader loader, FMLTweaker tweaker)
    {
        instance(loader, tweaker).setupClient();
    }

    public static void configureForServerLaunch(LaunchClassLoader loader, FMLTweaker tweaker)
    {
        instance(loader, tweaker).setupServer();
    }

    private static FMLLaunchHandler instance(LaunchClassLoader launchLoader, FMLTweaker tweaker)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new FMLLaunchHandler(launchLoader, tweaker);
        }
        return INSTANCE;

    }

    private FMLLaunchHandler(LaunchClassLoader launchLoader, FMLTweaker tweaker)
    {
        this.classLoader = launchLoader;
        this.tweaker = tweaker;
        this.minecraftHome = tweaker.getGameDir();
        this.classLoader.addClassLoaderExclusion("net.minecraftforge.fml.relauncher.");
        this.classLoader.addClassLoaderExclusion("net.minecraftforge.classloading.");
        this.classLoader.addTransformerExclusion("net.minecraftforge.fml.common.asm.transformers.");
        this.classLoader.addTransformerExclusion("net.minecraftforge.fml.common.patcher.");
        this.classLoader.addTransformerExclusion("net.minecraftforge.fml.repackage.");
        this.classLoader.addClassLoaderExclusion("org.apache.");
        this.classLoader.addClassLoaderExclusion("com.google.common.");
        this.classLoader.addClassLoaderExclusion("org.objectweb.asm.");
        this.classLoader.addClassLoaderExclusion("LZMA.");
    }

    private void setupClient()
    {
        side = Side.CLIENT;
        setupHome();
    }

    private void setupServer()
    {
        side = Side.SERVER;
        setupHome();

    }

    private void setupHome()
    {
        FMLInjectionData.build(minecraftHome, classLoader);
        FMLLog.log.info("Forge Mod Loader version {}.{}.{}.{} for Minecraft {} loading", FMLInjectionData.major, FMLInjectionData.minor,
                FMLInjectionData.rev, FMLInjectionData.build, FMLInjectionData.mccversion);
        FMLLog.log.info("Java is {}, version {}, running on {}:{}:{}, installed at {}", System.getProperty("java.vm.name"), System.getProperty("java.version"), System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version"), System.getProperty("java.home"));
        FMLLog.log.debug("Java classpath at launch is:");
        for (String path : System.getProperty("java.class.path").split(File.pathSeparator))
            FMLLog.log.debug("    {}", path);
        FMLLog.log.debug("Java library path at launch is:");
        for (String path : System.getProperty("java.library.path").split(File.pathSeparator))
            FMLLog.log.debug("    {}", path);

        try
        {
            CoreModManager.handleLaunch(minecraftHome, classLoader, tweaker);
        }
        catch (Throwable t)
        {
            throw new RuntimeException("An error occurred trying to configure the Minecraft home at " + minecraftHome.getAbsolutePath() + " for Forge Mod Loader", t);
        }
    }

    public static Side side()
    {
        return side;
    }


    private void injectPostfixTransformers()
    {
        CoreModManager.injectTransformers(classLoader);
    }

    public static void appendCoreMods()
    {
        INSTANCE.injectPostfixTransformers();
    }
}