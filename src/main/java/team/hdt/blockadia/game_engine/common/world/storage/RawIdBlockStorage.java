package team.hdt.blockadia.game_engine.common.world.storage;

import org.jdom2.Element;
import team.hdt.blockadia.game_engine.common.world.block.BlockType;
import team.hdt.blockadia.game_engine.common.world.block.BlockTypes;

public class RawIdBlockStorage implements RawBlockStorage {
    private final byte[][][] data;
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;

    public RawIdBlockStorage(int sizeX, int sizeY, int sizeZ) {
        this.data = new byte[sizeX][sizeY][sizeZ];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    @Override
    public void setBlockUnchecked(int x, int y, int z, BlockType type) {
        int id = BlockTypes.REGISTRY.getId(type.getIdentifier()) & 0xFF;
        this.data[x][y][z] = (byte) id;
    }

    @Override
    public BlockType getBlockUnchecked(int x, int y, int z) {
        byte id = this.data[x][y][z];
        return BlockTypes.REGISTRY.get(id & 0xFF);
    }

    @Override
    public int getSizeX() {
        return this.sizeX;
    }

    @Override
    public int getSizeY() {
        return this.sizeY;
    }

    @Override
    public int getSizeZ() {
        return this.sizeZ;
    }

    @Override
    public Element toXML(Element element){
        element.setAttribute("sizeX",sizeX + "");
        element.setAttribute("sizeY",sizeY + "");
        element.setAttribute("sizeZ",sizeZ + "");
        Element data = new Element("data");
        for (int x =0; x < sizeX; x++){
            for (int y =0; y < sizeY; y++){
                for (int z =0; z < sizeZ; z++){
                    Element block = new Element("block");
                    block.setAttribute("x", x+"");
                    block.setAttribute("y", y+"");
                    block.setAttribute("z", z+"");
                    block.setText(this.data[x][y][z] + "");
                    data.addContent(block);
                }
            }
        }
        element.addContent(data);
        return element;
    }

}
