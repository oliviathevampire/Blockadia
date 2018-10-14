package team.hdt.blockadia.engine.core.world.biomes;

import team.hdt.blockadia.engine.core.block.BlockType;
import team.hdt.blockadia.engine.core.init.Biomes;
import team.hdt.blockadia.engine.core.init.Blocks;
import team.hdt.blockadia.engine.core.util.Identifier;
import team.hdt.blockadia.engine.core.world.gen.interfaces.IBiome;
import team.hdt.blockadia.engine.core.world.gen.interfaces.ILayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BiomePlains extends Biome implements IBiome {

    public Identifier identifier = new Identifier("plains");

    public BiomePlains() {
        super((new Biome.BiomeProperties("Plains")).baseHeight(0.125F).heightVariation(0.05F).temperature(0.8F).rainfall(0.4F));
    }

    @Override
    public ILayer getLayer(int y, Random random) {
        return y < 50 ? new LayerSolid(Blocks.ROCK) : new ILayer.LayerAir(32, 32);
    }

    /**
     * Get's the chancemap for the chance of getting a {@link IBiome} generated next to this biome
     *
     * @return a {@link HashMap} with all the possible neighbour biomes and their chances.
     */
    @Override
    public Map<IBiome, Integer> getChanceMap() {
        HashMap<IBiome, Integer> chanceMap = new HashMap<>();
        chanceMap.put(Biomes.PLAINS, 1);
        chanceMap.put(Biomes.DESERT, 1);
        return chanceMap;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    private class LayerSolid implements ILayer {

        BlockType type;

        public LayerSolid(BlockType type) {
            this.type = type;
        }

        @Override
        public int getWidth() {
            return 32;
        }

        @Override
        public int getHeight() {
            return 32;
        }

        @Override
        public BlockType getBlockAtPos(int x, int z) {
            return type;
        }
    }
}
