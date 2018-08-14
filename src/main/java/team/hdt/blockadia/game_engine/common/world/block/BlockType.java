package team.hdt.blockadia.game_engine.common.world.block;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.registry.RegistryEntry;
import team.hdt.blockadia.game_engine.common.util.interfaces.Nonnull;
import team.hdt.blockadia.game_engine.common.world.block.material.BlockMaterials;
import team.hdt.blockadia.game_engine.common.world.block.material.MapColor;

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

    public BlockMaterials.Material getMaterial() {
        return material;
    }

    public MapColor getMapColor() {
        return mapColor;
    }

}