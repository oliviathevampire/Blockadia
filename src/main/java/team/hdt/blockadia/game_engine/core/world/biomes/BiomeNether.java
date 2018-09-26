package team.hdt.blockadia.game_engine.core.world.biomes;

import team.hdt.blockadia.game_engine.core.block.BlockType;
import team.hdt.blockadia.game_engine.core.init.Biomes;
import team.hdt.blockadia.game_engine.core.init.Blocks;
import team.hdt.blockadia.game_engine.core.world.gen.interfaces.IBiome;
import team.hdt.blockadia.game_engine.core.world.gen.interfaces.ILayer;
import team.hdt.blockadia.game_engine.client.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BiomeNether extends Biome implements IBiome {

    private Identifier identifier = new Identifier("biome_nether");

    public BiomeNether() {
        super((new BiomeProperties("Nether")).baseHeight(0.125F).heightVariation(0.05F).temperature(2.0F).rainfall(0.0F).rainDisabled());
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
        chanceMap.put(Biomes.DESERT, 0);
        chanceMap.put(Biomes.PLAINS, 0);
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