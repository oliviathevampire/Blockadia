package team.hdt.sandboxgame.game_engine.common.world.block;

import team.hdt.sandboxgame.game_engine.common.Identifier;
import team.hdt.sandboxgame.game_engine.common.registry.DefaultedHashIdRegistry;
import team.hdt.sandboxgame.game_engine.common.registry.IdRegistry;

public class BlockTypes {
    public static final BlockType AIR = new BlockBuilder().build();
    public static final BlockType ROCK = new BlockBuilder().build();

    public static final IdRegistry<BlockType> REGISTRY = new DefaultedHashIdRegistry<BlockType>()
            .withDefaultEntry(AIR);

    public static void register() {
        REGISTRY.register(new Identifier("air"), AIR, 0);
        REGISTRY.register(new Identifier("rock"), ROCK, 1);
    }
}
