package team.hdt.blockadia.game_engine_old.client.particleSpawns;

import team.hdt.blockadia.game_engine_old.common.util.math.Maths;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors4f;

public class CircleSpawn implements ParticleSpawn {

    private Vectors3f heading;
    private float radius;

    public CircleSpawn(Vectors3f heading, float radius) {
        this.heading = (Vectors3f) heading.normalise();
        this.radius = radius;
    }

    @Override
    public Vectors4f getBaseSpawnPosition() {
        Vectors3f point = Maths.randomPointOnCircle(heading, radius);
        return new Vectors4f(point.x, point.y, point.z, 1f);
    }

}
