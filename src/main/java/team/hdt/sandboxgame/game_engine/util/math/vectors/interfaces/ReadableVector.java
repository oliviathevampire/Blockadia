/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.hdt.sandboxgame.game_engine.util.math.vectors.interfaces;

import team.hdt.sandboxgame.game_engine.util.math.vectors.Vectors;

import java.nio.FloatBuffer;

/**
 * @author 326296
 */
public interface ReadableVector {
    public float length();

    public float lengthSquared();

    public Vectors store(FloatBuffer fb);
}

