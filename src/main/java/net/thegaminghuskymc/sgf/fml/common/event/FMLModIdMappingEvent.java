package net.thegaminghuskymc.sgf.fml.common.event;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FMLModIdMappingEvent extends FMLEvent
{
    public class ModRemapping
    {
        public final ResourceLocation registry;
        public final ResourceLocation key;
        public final int oldId;
        public final int newId;

        private ModRemapping(ResourceLocation registry, ResourceLocation key, int oldId, int newId)
        {
            this.registry = registry;
            this.key = key;
            this.oldId = oldId;
            this.newId = newId;
        }
    }

    private final Map<ResourceLocation, ImmutableList<ModRemapping>> remaps;
    private final ImmutableSet<ResourceLocation> keys;

    public final boolean isFrozen;
    public FMLModIdMappingEvent(Map<ResourceLocation, Map<ResourceLocation, Integer[]>> remaps, boolean isFrozen)
    {
        this.isFrozen = isFrozen;
        this.remaps = Maps.newHashMap();
        remaps.forEach((name, rm) ->
        {
            List<ModRemapping> tmp = Lists.newArrayList();
            rm.forEach((key, value) -> tmp.add(new ModRemapping(name, key, value[0], value[1])));
            tmp.sort(Comparator.comparingInt(o -> o.newId));
            this.remaps.put(name, ImmutableList.copyOf(tmp));
        });
        this.keys = ImmutableSet.copyOf(this.remaps.keySet());
    }

    public ImmutableSet<ResourceLocation> getRegistries()
    {
        return this.keys;
    }

    public ImmutableList<ModRemapping> getRemaps(ResourceLocation registry)
    {
        return this.remaps.get(registry);
    }
}