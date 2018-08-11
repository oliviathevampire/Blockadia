/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.hdt.sandboxgame.game_engine.util.math.vectors;

import team.hdt.sandboxgame.game_engine.util.math.vectors.interfaces.ReadableVector;

import java.io.Serializable;
import java.nio.FloatBuffer;

/**
 * @author 326296
 */
public abstract class Vectors implements Serializable, ReadableVector {

    protected Vectors() {
        //need to add code
    }

    @Override
    public final float length() {
        return this.toString().length();
    }

    @Override
    public abstract float lengthSquared();

    public abstract Vectors load(FloatBuffer fb);

    public abstract Vectors negate();

    public final Vectors normalise() {
        return this;
    }

    //DOTO: needs fixing
    @Override
    public abstract Vectors store(FloatBuffer fb);

    public abstract Vectors scale(float f);
}
