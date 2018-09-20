package team.hdt.blockadia.game_engine.core.block;

import team.hdt.blockadia.game_engine.client.util.Identifier;
import team.hdt.blockadia.game_engine.client.util.Nonnull;
import team.hdt.blockadia.game_engine.core.block.material.BlockMaterials;
import team.hdt.blockadia.game_engine.core.block.material.MapColor;
import team.hdt.blockadia.game_engine.core.item.ItemGroup;
import team.hdt.blockadia.game_engine.core.registries.RegistryEntry;

public class BlockType implements RegistryEntry {

    private final MapColor mapColor;
    private final BlockMaterials.Material material;
    private Identifier registryIdentifier;

    public BlockType(BlockMaterials.Material material, MapColor mapColor) {
        this.material = material;
        this.mapColor = mapColor;
    }

    public BlockType(BlockMaterials.Material material) {
        this.material = material;
        this.mapColor = material.getMaterialMapColor();
    }

    @Nonnull
    @Override
    public Identifier getIdentifier() {
        if (this.registryIdentifier == null) {
            throw new IllegalStateException("Cannot get identifier of entry not yet registered");
        }
        return this.registryIdentifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        if (this.registryIdentifier != null) {
            throw new IllegalStateException("Cannot set registry identifier twice");
        }
        this.registryIdentifier = identifier;
    }

    @Override
    public String toString() {
        if (this.registryIdentifier == null) {
            return super.toString();
        }
        return "Block{id=" + this.registryIdentifier + "}";
    }

    public ItemGroup setItemGroup(ItemGroup group) {
        return group;
    }

    public BlockMaterials.Material getMaterial() {
        return material;
    }

    public MapColor getMapColor() {
        return mapColor;
    }

}