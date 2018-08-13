package team.hdt.sandboxgame.game_engine.common.world;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import team.hdt.sandboxgame.game_engine.common.world.storage.ChunkHolder;

import java.util.HashMap;
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
}
