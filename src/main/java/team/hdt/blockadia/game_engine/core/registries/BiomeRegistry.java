package team.hdt.blockadia.game_engine.core.registries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine_old.common.Identifier;
import team.hdt.blockadia.game_engine.core.world.gen.interfaces.IBiome;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BiomeRegistry implements Registry<IBiome> {

    public static BiomeRegistry registries = new BiomeRegistry();
    public Map<Identifier, IBiome> registry = new HashMap<>();
    private Logger LOGGER = LogManager.getLogger();

    @Override
    public void register(Identifier identifier, IBiome value) {
        if(identifier != null && value != null) {
            registry.put(identifier, value);
            LOGGER.info(String.format("Registering a biome called %s", Objects.requireNonNull(identifier).getNamespace() + ":" + identifier.getPath()));
        } else {
            LOGGER.error(String.format("Could not register a biome called %s", Objects.requireNonNull(identifier).getNamespace() + ":" + identifier.getPath()));
        }
    }

    @Override
    public IBiome get(Identifier identifier) {
        return registry.get(identifier);
    }

}
