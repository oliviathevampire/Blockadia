package team.hdt.blockadia.game_engine.common.world.gen.factory;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.registry.BiomeRegistry;
import team.hdt.blockadia.game_engine.common.util.interfaces.Nullable;
import team.hdt.blockadia.game_engine.common.world.Chunk;
import team.hdt.blockadia.game_engine.common.world.gen.interfaces.IBiome;
import team.hdt.blockadia.game_engine.common.world.gen.interfaces.ILayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChunkFactory {

    public ChunkFactory(){}

    public static Random random = new Random();
    private IBiome IBiome;
    static int currentY;

     public void initFactory(@Nullable IBiome biome, boolean chooseOwn){
         if(chooseOwn) {
             Map<Identifier, IBiome> biomes = BiomeRegistry.registries.registry;
             Map<Integer, IBiome> biomeList = new HashMap<>();
             biomes.forEach((identifier, IBiome) -> {
                 int i = biomeList.size();
                 biomeList.put(i, IBiome);
             });
             if (biomeList.size() > 1)
             IBiome = biomeList.get(random.nextInt(biomeList.size()-1));
             else{
                 IBiome = biomeList.get(biomeList.size()-1);
             }
         } else {
             this.IBiome = biome;
         }
    }

    public Chunk getChunk(){
         Chunk chunk = new Chunk(Chunk.CHUNK_SIZE,300,Chunk.CHUNK_SIZE,Chunk.ChunkType.HORIZONTAL, IBiome);
         do {
             ILayer layer = IBiome.getLayer(currentY, random);
             for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++){
                    chunk.setBlock(x, currentY, z, layer.getBlockAtPos(x, z));
                }
             }
             currentY++;
         } while (currentY < 300);
         return chunk;
    }

    public void getNeighbourFactory(){
        Map<IBiome, Integer> map = IBiome.getChanceMap();
        Map<Integer, IBiome> list = new HashMap<>();
        map.forEach((iBiome, i) -> {
            int index = list.size();
            for(int index2 = index; index2 < i; index2++) {
                list.put(index2, IBiome);
            }
        });
        if(list.size() > 1){
            IBiome = list.get(random.nextInt(list.size()-1));
        } else {
            IBiome = list.get(list.size()-1);
        }
    }

}
