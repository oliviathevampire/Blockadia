package net.thegaminghuskymc.sandboxgame.world;

import net.thegaminghuskymc.sandboxgame.profiler.Profiler;
import net.thegaminghuskymc.sandboxgame.world.storage.ISaveHandler;
import net.thegaminghuskymc.sandboxgame.world.storage.WorldInfo;
import net.thegaminghuskymc.sandboxgame.server.MinecraftServer;

public class WorldServerDemo extends WorldServer {
    private static final long DEMO_WORLD_SEED = (long) "North Carolina".hashCode();
    public static final WorldSettings DEMO_WORLD_SETTINGS = (new WorldSettings(DEMO_WORLD_SEED, GameType.SURVIVAL, true, false, WorldType.DEFAULT)).enableBonusChest();

    public WorldServerDemo(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo worldInfoIn, int dimensionId, Profiler profilerIn) {
        super(server, saveHandlerIn, worldInfoIn, dimensionId, profilerIn);
        this.worldInfo.populateFromWorldSettings(DEMO_WORLD_SETTINGS);
    }
}