package team.hdt.sandboxgame.game_engine.common.world.block;

public class BlockBuilder {
    // TODO: Placeholder
    private String name;

    public BlockBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public BlockType build() {
        return new BlockType();
    }
}
