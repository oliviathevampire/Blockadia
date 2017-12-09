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
        for (int y = 1; y > 0; y--) {
            for (int x = -4; x < 4; x++) {
                for (int z = -4; z < 4; z++) {
                    this.generateTerrain(x, y, z);
                }
            }
        }

        for (int x = 0; x < 4; x++) {
            for (int z = 0; z < 4; z++) {
                EntityPlant entityTest = new EntityPlant(this);
                entityTest.setPosition(x * 8.0f, 140.0f, z * 8.0f);
                this.spawnEntity(entityTest);
            }
        }
    }

    @Override
    public String getName() {
        return ("Default");
    }
}