package team.hdt.blockadia.game_engine.common.world.block;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.world.block.material.BlockMaterials;
import team.hdt.blockadia.game_engine.common.world.block.material.MapColor;

public class BlockBuilder {

    private String translationKey;
    private Identifier registryName;
    private BlockMaterials.Material material;
    private MapColor mapColor;

    public BlockBuilder withTranslationKey(String translationKey) {
        this.translationKey = translationKey;
        return this;
    }

    public BlockBuilder withRegistryName(Identifier registryName) {
        this.registryName = registryName;
        return this;
    }

    public BlockBuilder withMaterial(BlockMaterials.Material material) {
        this.material = material;
        return this;
    }

    public BlockBuilder withMapColor(MapColor mapColor) {
        this.mapColor = mapColor;
        return this;
    }

    public BlockType build() {
        return new BlockType();
    }

}
