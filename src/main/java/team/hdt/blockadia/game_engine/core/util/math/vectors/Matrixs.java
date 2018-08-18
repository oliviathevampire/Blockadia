package team.hdt.blockadia.game_engine.core.util.math.vectors;

import java.io.Serializable;
import java.nio.FloatBuffer;

public abstract class Matrixs
        implements Serializable {
    public abstract Matrixs setIdentity();

    public abstract Matrixs invert();

    public abstract Matrixs load(FloatBuffer paramFloatBuffer);

    public abstract Matrixs loadTranspose(FloatBuffer paramFloatBuffer);

    public abstract Matrixs negate();

    public abstract Matrixs store(FloatBuffer paramFloatBuffer);

    public abstract Matrixs storeTranspose(FloatBuffer paramFloatBuffer);

    public abstract Matrixs transpose();

    public abstract Matrixs setZero();

    public abstract float determinant();
}
