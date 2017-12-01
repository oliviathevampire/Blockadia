package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.graph.Material;
import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;

public class BlockDirt extends Block {

    public BlockDirt() {
        super("dirt");
        setTexture(new ResourceLocation("/assets/sandboxgame/textures/blocks/dirt.png"));
    }

    public BlockDirt(Material material) {
        this();
        setMaterial(material);
    }

}
