package team.hdt.blockadia.game_engine.client.model.objutil;


import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class Vertex {

    private static final int NO_INDEX = -1;

    private Vectors3f position;
    private int textureIndex = NO_INDEX;
    private int normalIndex = NO_INDEX;
    private Vertex duplicateVertex = null;
    private int index;
    private float length;

    public Vertex(int index, Vectors3f position) {
        this.index = index;
        this.position = position;
        this.length = position.length();
    }

    public int getIndex() {
        return index;
    }

    public float getLength() {
        return length;
    }

    public boolean isSet() {
        return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
    }

    public boolean hasSameTextureAndNormal(int textureIndexOther, int normalIndexOther) {
        return textureIndexOther == textureIndex && normalIndexOther == normalIndex;
    }

    public Vectors3f getPosition() {
        return position;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

    public int getNormalIndex() {
        return normalIndex;
    }

    public void setNormalIndex(int normalIndex) {
        this.normalIndex = normalIndex;
    }

    public Vertex getDuplicateVertex() {
        return duplicateVertex;
    }

    public void setDuplicateVertex(Vertex duplicateVertex) {
        this.duplicateVertex = duplicateVertex;
    }

}