package team.hdt.blockadia.game_engine.core.world.storage;

import org.jdom2.Element;
import team.hdt.blockadia.game_engine.core.block.BlockType;

public interface RawBlockStorage {
    void setBlockUnchecked(int x, int y, int z, BlockType type);

    Element toXML(Element element);

    BlockType getBlockUnchecked(int x, int y, int z);

    int getSizeX();

    int getSizeY();

    int getSizeZ();
}
