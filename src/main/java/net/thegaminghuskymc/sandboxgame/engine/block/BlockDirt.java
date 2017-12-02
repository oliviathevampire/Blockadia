package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.graph.Material;

public class BlockDirt extends Block {

    public BlockDirt() {
        super("dirt");
    }

    public BlockDirt(Material material) {
        super("dirt");
        setMaterial(material);
    }

}
