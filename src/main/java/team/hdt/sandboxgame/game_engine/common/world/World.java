package team.hdt.sandboxgame.game_engine.common.world;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import team.hdt.sandboxgame.game_engine.common.world.gen.factory.ChunkFactory;
import team.hdt.sandboxgame.game_engine.common.world.gen.factory.WorldFactory;
import team.hdt.sandboxgame.game_engine.common.world.gen.interfaces.IBiome;
import team.hdt.sandboxgame.game_engine.common.world.storage.ChunkHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    protected static final Logger LOGGER = LogManager.getLogger();

    public Map<ChunkPos, Chunk> CHUNKS = new HashMap<>();

    public Document toXML(){
        Element root = new Element("world");
        Element chunks = new Element("chunks");
        CHUNKS.forEach((pos, chunk) -> {
            ChunkHolder chunkHolder = new ChunkHolder(chunk, pos);
            chunks.addContent(chunkHolder.toXML());
        });
        root.addContent(chunks);
        Document document = new Document(root);
        return document;
    }

    public ChunkHolder getChunk(ChunkPos pos){
        if(CHUNKS.containsKey(pos)){
            return new ChunkHolder(CHUNKS.get(pos), pos);
        } else {
            return generateChunk(pos);
        }
    }

    private ChunkHolder generateChunk(ChunkPos pos){
        List<IBiome> biomeList = new ArrayList<>();
        for(ChunkPos chunkPos : pos.getSurroundings()){
            if (CHUNKS.containsKey(chunkPos)){
                biomeList.add(CHUNKS.get(chunkPos).getBiome());
            }
        }
        boolean flag = biomeList.isEmpty();
        IBiome biome = null;
        if(!flag)
        biome = biomeList.get(ChunkFactory.random.nextInt(biomeList.size()));
        ChunkFactory factory = new ChunkFactory();
        factory.initFactory(biome, flag);
        return new ChunkHolder(factory.getChunk(), pos);
    }
}
