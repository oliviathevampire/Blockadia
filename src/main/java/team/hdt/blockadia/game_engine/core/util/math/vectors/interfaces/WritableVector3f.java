/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.hdt.blockadia.game_engine.core.util.math.vectors.interfaces;

/**
 * @author 326296
 */
public interface WritableVector3f extends WritableVector2f {
    public void setZ(float f);

    public void set(float f, float f1, float f2);
}
