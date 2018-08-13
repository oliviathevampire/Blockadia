package team.hdt.blockadia.game_engine.common.particle;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors4f;

import java.util.Random;

public class ParticleSystem {

    private float pps, averageSpeed, gravityComplient, averageLifeLength, averageScale;

    private float speedError, lifeError, scaleError = 0;
    private boolean randomRotation = false;
    private Vectors3f direction;
    private float directionDeviation = 0;

    private Random random = new Random();

    public ParticleSystem(float pps, float speed, float gravityCompliant, float lifeLength, float scale) {
        this.pps = pps;
        this.averageSpeed = speed;
        this.gravityComplient = gravityCompliant;
        this.averageLifeLength = lifeLength;
        this.averageScale = scale;
    }

    private static Vectors3f generateRandomUnitVectorWithinCone(Vectors3f coneDirection, float angle) {
        float cosAngle = (float) Math.cos(angle);
        Random random = new Random();
        float theta = (float) (random.nextFloat() * 2f * Math.PI);
        float z = cosAngle + (random.nextFloat() * (1 - cosAngle));
        float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
        float x = (float) (rootOneMinusZSquared * Math.cos(theta));
        float y = (float) (rootOneMinusZSquared * Math.sin(theta));

        Vectors4f direction = new Vectors4f(x, y, z, 1);
        if (coneDirection.x != 0 || coneDirection.y != 0 || (coneDirection.z != 1 && coneDirection.z != - 1)) {
            Vectors3f rotateAxis = Vectors3f.cross(coneDirection, new Vectors3f(0, 0, 1), null);
            rotateAxis.normalise();
            float rotateAngle = (float) Math.acos(Vectors3f.dot(coneDirection, new Vectors3f(0, 0, 1)));
            Matrix4fs rotationMatrix = new Matrix4fs();
            rotationMatrix.rotate(- rotateAngle, rotateAxis);
            Matrix4fs.transform(rotationMatrix, direction, direction);
        } else if (coneDirection.z == - 1) {
            z *= - 1;
        }
        return new Vectors3f(x, y, z);
    }

    public void setDirection(Vectors3f direction, float deviation) {
        this.direction = new Vectors3f(direction);
        this.directionDeviation = (float) (deviation * Math.PI);
    }

    public void randomizeRotation() {
        randomRotation = true;
    }

    public void setSpeedError(float error) {
        this.speedError = error * averageSpeed;
    }

    public void setLifeError(float error) {
        this.lifeError = error * averageLifeLength;
    }

    public void setScaleError(float error) {
        this.scaleError = error * averageScale;
    }

    public void generateParticles(Vectors3f systemCenter) {
        float delta = 0 /*FrameTimeSeconds*/;
        float particlesToCreate = pps * delta;
        int count = (int) Math.floor(particlesToCreate);
        float partialParticle = particlesToCreate % 1;
        for (int i = 0; i < count; i++) {
            emitParticle(systemCenter);
        }
        if (Math.random() < partialParticle) {
            emitParticle(systemCenter);
        }
    }

    private void emitParticle(Vectors3f center) {
        Vectors3f velocity = null;
        if (direction != null) {
            velocity = generateRandomUnitVectorWithinCone(direction, directionDeviation);
        } else {
            velocity = generateRandomUnitVector();
        }
        velocity.normalise();
        velocity.scale(generateValue(averageSpeed, speedError));
        float scale = generateValue(averageScale, scaleError);
        float lifeLength = generateValue(averageLifeLength, lifeError);
        new Particle(new Vectors3f(center), velocity, gravityComplient, lifeLength, generateRotation(), scale);
    }

    private float generateValue(float average, float errorMargin) {
        float offset = (random.nextFloat() - 0.5f) * 2f * errorMargin;
        return average + offset;
    }

    private float generateRotation() {
        if (randomRotation) {
            return random.nextFloat() * 360f;
        } else {
            return 0;
        }
    }

    private Vectors3f generateRandomUnitVector() {
        float theta = (float) (random.nextFloat() * 2f * Math.PI);
        float z = (random.nextFloat() * 2) - 1;
        float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
        float x = (float) (rootOneMinusZSquared * Math.cos(theta));
        float y = (float) (rootOneMinusZSquared * Math.sin(theta));
        return new Vectors3f(x, y, z);
    }

}