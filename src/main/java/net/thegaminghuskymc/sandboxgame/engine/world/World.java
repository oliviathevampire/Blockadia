package net.thegaminghuskymc.sandboxgame.engine.world;

import net.thegaminghuskymc.sandboxgame.engine.Entity;
import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

public class World {

    public final WorldType type = WorldType.DEFAULT;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public final HashMap<Vector3f, Block> blocks = new HashMap<>();



}
