package team.hdt.blockadia.game_engine.core.registries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine.client.util.Identifier;
import team.hdt.blockadia.game_engine.core.block.BlockType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockRegistry implements Registry<BlockType> {

    public static BlockRegistry registries = new BlockRegistry();
    public Map<Identifier, BlockType> registry = new HashMap<>();
    private Logger LOGGER = LogManager.getLogger();

    @Override
    public void register(Identifier identifier, BlockType value) {
        if(identifier != null && value != null) {
            registry.put(identifier, value);
            LOGGER.info(String.format("Registering a biome called %s", Objects.requireNonNull(identifier).getNamespace() + ":" + identifier.getPath()));
        } else {
            LOGGER.error(String.format("Could not register a biome called %s", Objects.requireNonNull(identifier).getNamespace() + ":" + identifier.getPath()));
        }
    }

    @Override
    public BlockType get(Identifier identifier) {
        return registry.get(identifier);
    }

}