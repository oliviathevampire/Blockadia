package team.hdt.blockadia.game_engine_old.client.particleSpawns;

import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors4f;

public class PointSpawn implements ParticleSpawn {

    public PointSpawn() {

    }

    @Override
    public Vectors4f getBaseSpawnPosition() {
        return new Vectors4f(0f, 0f, 0f, 1f);
    }

}
