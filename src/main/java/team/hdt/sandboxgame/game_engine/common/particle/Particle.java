package team.hdt.sandboxgame.game_engine.common.particle;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;

public class Particle {
    private static final float GRAVITY = - 50;
    private Vectors3f position;
    private Vectors3f velocity;
    private float gravityEffects;
    private float lifeLength;
    private float rotation;
    private float scale;
    private float elapsedTime = 0;


    public Particle(Vectors3f position, Vectors3f velocity, float gravityEffects, float lifeLength, float rotation,
                    float scale) {
        super();
        this.position = position;
        this.velocity = velocity;
        this.gravityEffects = gravityEffects;
        this.lifeLength = lifeLength;
        this.rotation = rotation;
        this.scale = scale;
        //ParticleMaster.addParticle(this);
    }

    public Vectors3f getPosition() {
        return position;
    }


    public float getRotation() {
        return rotation;
    }


    public float getScale() {
        return scale;
    }

    public boolean update() {
        velocity.y += GRAVITY * gravityEffects /* * FrameTimeSeconds*/;
        Vectors3f change = new Vectors3f(velocity);
        //change.scale(/*FrameTimeSeconds*/);
        Vectors3f.add(change, position, position);
        elapsedTime += 0 /* FrameTimeSeconds*/;
        return elapsedTime < lifeLength;

    }

}
