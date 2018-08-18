package team.hdt.blockadia.game_engine.core.world.gen.factory;

import team.hdt.blockadia.game_engine_old.common.Identifier;
import team.hdt.blockadia.game_engine.core.registries.BiomeRegistry;
import team.hdt.blockadia.game_engine_old.common.util.interfaces.Nullable;
import team.hdt.blockadia.game_engine.core.world.chunk.Chunk;
import team.hdt.blockadia.game_engine.core.world.gen.interfaces.IBiome;
import team.hdt.blockadia.game_engine.core.world.gen.interfaces.ILayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChunkFactory {

    public static Random random = new Random();
    private IBiome IBiome;
    public ChunkFactory() {
    }

    public void initFactory(@Nullable IBiome biome, boolean chooseOwn) {
        if (chooseOwn) {
            Map<Identifier, IBiome> biomes = BiomeRegistry.registries.registry;
            Map<Integer, IBiome> biomeList = new HashMap<>();
            biomes.forEach((identifier, IBiome) -> {
                int i = biomeList.size();
                biomeList.put(i, IBiome);
            });
            if (biomeList.size() > 1)
                IBiome = biomeList.get(random.nextInt(biomeList.size() - 1));
            else {
                IBiome = biomeList.get(biomeList.size() - 1);
            }
        } else {
            this.IBiome = biome;
        }
    }

    public Chunk getChunk() {
        Chunk chunk = new Chunk(Chunk.CHUNK_SIZE, 100, Chunk.CHUNK_SIZE, Chunk.ChunkType.HORIZONTAL, IBiome);

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < 100; y++) {
                ILayer layer = IBiome.getLayer(y, random);
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    chunk.setBlock(x, y, z, layer.getBlockAtPos(x, z));
                }
            }
        }
        return chunk;
    }

    public void getNeighbourFactory() {
        Map<IBiome, Integer> map = IBiome.getChanceMap();
        Map<Integer, IBiome> list = new HashMap<>();
        map.forEach((iBiome, i) -> {
            int index = list.size();
            for (int index2 = index; index2 < i; index2++) {
                list.put(index2, IBiome);
            }
        });
        if (list.size() > 1) {
            IBiome = list.get(random.nextInt(list.size() - 1));
        } else {
            IBiome = list.get(list.size() - 1);
        }
    }

}
