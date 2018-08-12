package team.hdt.sandboxgame.game_engine.common.world.block;

import team.hdt.sandboxgame.game_engine.common.Identifier;

public class BlockBuilder {

    // TODO: Placeholder
    private String translationKey;
    private Identifier registryName;

    public BlockBuilder withTranslationKey(String translationKey) {
        this.translationKey = translationKey;
        return this;
    }

    public BlockBuilder withRegistryName(Identifier registryName) {
        this.registryName = registryName;
        return this;
    }

    public BlockType build() {
        return new BlockType();
    }

}
