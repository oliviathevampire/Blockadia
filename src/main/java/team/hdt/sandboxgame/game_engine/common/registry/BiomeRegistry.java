package team.hdt.sandboxgame.game_engine.common.registry;

import team.hdt.sandboxgame.game_engine.common.Identifier;
import team.hdt.sandboxgame.game_engine.common.world.gen.interfaces.IBiome;

import java.util.HashMap;
import java.util.Map;

public class BiomeRegistry implements Registry<IBiome> {

    public Map<Identifier, IBiome> registry = new HashMap<>();
    public static BiomeRegistry registries = new BiomeRegistry();

    @Override
    public void register(Identifier identifier, IBiome value) {
        registry.put(identifier, value);
    }

    @Override
    public IBiome get(Identifier identifier) {
        return registry.get(identifier);
    }
}
