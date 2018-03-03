package net.thegaminghuskymc.sandboxgame.client.audio;

import com.google.common.collect.Maps;
import net.thegaminghuskymc.sandboxgame.registries.RegistrySimple;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class SoundRegistry extends RegistrySimple<ResourceLocation, SoundEventAccessor> {
    /**
     * Contains all registered sound
     */
    private Map<ResourceLocation, SoundEventAccessor> soundRegistry;

    /**
     * Creates the Map we will use to map keys to their registered values.
     */
    protected Map<ResourceLocation, SoundEventAccessor> createUnderlyingMap() {
        this.soundRegistry = Maps.<ResourceLocation, SoundEventAccessor>newHashMap();
        return this.soundRegistry;
    }

    public void add(SoundEventAccessor accessor) {
        this.putObject(accessor.getLocation(), accessor);
    }

    /**
     * Reset the underlying sound map (Called on resource manager reload)
     */
    public void clearMap() {
        this.soundRegistry.clear();
    }
}