package team.hdt.blockadia.game_engine.common.registry;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.world.gen.interfaces.IBiome;

import java.util.HashMap;
import java.util.Map;

public class BiomeRegistry implements Registry<IBiome> {

    public static BiomeRegistry registries = new BiomeRegistry();
    public Map<Identifier, IBiome> registry = new HashMap<>();

    @Override
    public void register(Identifier identifier, IBiome value) {
        registry.put(identifier, value);
        System.out.println(String.format("Registering a biome called %s", identifier.getNamespace() + ":" + identifier.getPath()));
    }

    @Override
    public IBiome get(Identifier identifier) {
        return registry.get(identifier);
    }

}
