package net.thegaminghuskymc.sandboxgame.testmod.common.world;

import net.thegaminghuskymc.sandboxgame.engine.world.WorldFlat;
import net.thegaminghuskymc.sandboxgame.engine.world.generator.WorldGeneratorFlat;
import net.thegaminghuskymc.sandboxgame.engine.world.generator.WorldGeneratorHoles;
import net.thegaminghuskymc.sandboxgame.testmod.common.entities.EntityPlant;

public class WorldDefault extends WorldFlat {

    public WorldDefault() {
        super();
    }

    @Override
    public void onLoaded() {
        this.setWorldGenerator(new WorldGeneratorHoles());
        for (int z = 1; z > 0; z--) {
            for (int x = -2; x < 2; x++) {
                for (int y = -2; y < 2; y++) {
                    this.generateTerrain(x, y, 0);
                }
            }
        }

    }

    @Override
    public String getName() {
        return ("Default");
    }
}