package team.hdt.sandboxgame.game_engine.common.world.storage;

import org.jdom2.Element;
import team.hdt.sandboxgame.game_engine.common.world.Chunk;

public class ChunkHolder {

    Chunk chunkIn;
    int[] pos;

    public ChunkHolder(Chunk chunk, int x, int y){
        this.chunkIn = chunk;
        this.pos = new int[]{
                x,
                y
        };
    }

    /**
     * @return the chunk it's holding
     */
    public Chunk getChunk() {
        return chunkIn;
    }

    /**
     * @return the chunk pos in an array; { x, y }
     */
    public int[] getPos() {
        return pos;
    }

    public Element toXML(){
        Element element = new Element("chunk");
        Element data = new Element("data");
        data.setAttribute("x",pos[0] + "");
        data.setAttribute("y",pos[1] + "");
        element.addContent(data);
        Element blockdata = new Element("blockdata");

        return null;
    }
}
