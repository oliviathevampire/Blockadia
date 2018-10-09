package team.hdt.blockadia.engine.core.block;

import team.hdt.blockadia.engine.client.util.Identifier;
import team.hdt.blockadia.engine.core.block.material.BlockMaterials;
import team.hdt.blockadia.engine.core.block.material.MapColor;
import team.hdt.blockadia.engine.core.item.ItemGroup;

public class BlockBuilder {

    private String translationKey;
    private Identifier registryName;
    private BlockMaterials.Material material;
    private MapColor mapColor;
    private ItemGroup itemGroup;

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

    public BlockBuilder withItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
        if (itemGroup == null) {
            this.itemGroup = ItemGroup.BASIC_BLOCKS;
        }
        return this;
    }

    public BlockType build() {
        BlockType blockType = new BlockType(material, mapColor);
        blockType.setItemGroup(itemGroup);
        return blockType;
    }

}
