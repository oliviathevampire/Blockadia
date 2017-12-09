package net.thegaminghuskymc.sandboxgame.game.client.renderer.particles;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;

public class ParticleCube extends Particle {
    private Matrix4f transfMatrix;

    private ParticleCube(int health) {
        super(health);
        this.transfMatrix = new Matrix4f();
    }

    public ParticleCube() {
        this(1000);
    }

    public final Matrix4f getTransfMatrix() {
        return (this.transfMatrix);
    }

    @Override
    protected void onUpdate(double dt) {
        this.calculateTransformationMatrix();
    }

    private void calculateTransformationMatrix() {
        Matrix4f.createTransformationMatrix(this.transfMatrix, this.getPosition(), this.getRotation(), this.getSize());
    }
}
