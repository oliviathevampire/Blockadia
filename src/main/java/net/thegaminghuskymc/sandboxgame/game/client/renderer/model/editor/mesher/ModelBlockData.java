package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher;

import net.thegaminghuskymc.sandboxgame.engine.faces.Face;
import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelSkin;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * hold the data of a single block of the model
 */
public class ModelBlockData {

    /**
     * the plan linked to this block
     */
    private final String[] bones;
    private final float[] weights;
    private final HashMap<ModelSkin, Color[]> colors;
    private final Vector3i pos;

    public ModelBlockData(int x, int y, int z) {
        this.pos = new Vector3i(x, y, z);
        this.bones = new String[]{"", "", ""};
        this.weights = new float[]{1, 0, 0};
        this.colors = new HashMap<ModelSkin, Color[]>();
    }

    public final ModelBlockData clone() {
        ModelBlockData copy = new ModelBlockData(this.pos.x, this.pos.y, this.pos.z);
        for (int i = 0; i < bones.length; i++) {
            copy.bones[i] = this.bones[i];
            copy.weights[i] = this.weights[i];
        }
        for (Entry<ModelSkin, Color[]> entry : this.colors.entrySet()) {
            copy.colors.put(entry.getKey(), entry.getValue().clone());
        }
        return (copy);
    }

    public final Color getColor(ModelSkin modelSkin, int faceID) {
        return (this.colors.containsKey(modelSkin) ? this.colors.get(modelSkin)[faceID] : null);
    }

    public final void setColor(ModelSkin modelSkin, Color color, Face face) {
        Color[] colors = this.colors.get(modelSkin);
        if (colors == null) {
            colors = new Color[Face.values().length];
            this.colors.put(modelSkin, colors);
        }
        colors[face.getID()] = color;
    }

    public final String getBone(int i) {
        return (this.bones[i]);
    }

    public final float getBoneWeight(int i) {
        return (this.weights[i]);
    }

    public final float getBoneWeight(String bone) {
        for (int i = 0; i < this.bones.length; i++) {
            if (this.bones.equals(bone)) {
                return (this.weights[i]);
            }
        }
        return (0);
    }

    public final void setBone(int i, String bone, float weight) {
        this.bones[i] = bone;
        this.weights[i] = weight;
    }

    public final int getX() {
        return (this.pos.x);
    }

    public final void setX(int x) {
        this.pos.x = x;
    }

    public final int getY() {
        return (this.pos.y);
    }

    public final void setY(int y) {
        this.pos.y = y;
    }

    public final int getZ() {
        return (this.pos.z);
    }

    public final void setZ(int z) {
        this.pos.z = z;
    }

    public final Vector3i getPos() {
        return (this.pos);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BlockData{");
        sb.append(this.bones[0]);
        sb.append(",");
        sb.append(this.bones[1]);
        sb.append(",");
        sb.append(this.bones[2]);
        sb.append(",");
        sb.append(this.weights[0]);
        sb.append(",");
        sb.append(this.weights[1]);
        sb.append(",");
        sb.append(this.weights[2]);
        sb.append("}");
        return (sb.toString());
    }

    public final boolean fit(ModelBlockData blockData) {
        for (int i = 0; i < this.bones.length; i++) {
            if (this.weights[i] == blockData.weights[i] && ((this.bones[i] == null && blockData.bones[i] == null)
                    || this.bones[i].equals(blockData.bones[i]))) {
                continue;
            }
            return (false);
        }
        return (true);
    }
}