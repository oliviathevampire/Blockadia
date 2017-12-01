package net.thegaminghuskymc.sandboxgame.engine.world;

import net.thegaminghuskymc.sandboxgame.engine.Entity;
import net.thegaminghuskymc.sandboxgame.engine.Scene;
import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.graph.Material;
import net.thegaminghuskymc.sandboxgame.engine.graph.Mesh;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

public class World {

    public final WorldType type = WorldType.DEFAULT;
    public ArrayList<Entity> entities = new ArrayList<>();
    public final HashMap<Vector3f, Block> blocks = new HashMap<>();
    public final ArrayList<Block> notAddedToScene = new ArrayList<>();

    public final Mesh mesh;

    public World(Material terrainMaterial) {
        mesh = new Mesh(Block.getPositions(), Block.getTextCoords(), Block.getNormals(), Block.getIndices());
        mesh.setBoundingRadius(2);
        mesh.setMaterial(terrainMaterial);
    }

    public Block setBlock(Vector3f vec, Block block) {
        block.setMesh(mesh);
        int textPos = Math.random() > 0.5f ? 0 : 1;
        block.setTextPos(textPos);
        block.setPosition(vec.x,vec.y,vec.z);
        blocks.put(block.getPosition(), block);
        notAddedToScene.add(block);

        return block;
    }

    public void endAddingBlocks(Scene scene) {
        scene.setGameItems(notAddedToScene);
        notAddedToScene.clear();
    }

    public void rayTrace(Vector3f pos, Quaternionf rot, float lenght) {
    }

    public void update() {
        for (Entity e : entities) {
            e.update();
        }
    }

}
