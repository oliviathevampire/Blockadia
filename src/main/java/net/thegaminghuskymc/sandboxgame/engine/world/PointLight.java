package net.thegaminghuskymc.sandboxgame.engine.world;


import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;

public class PointLight extends Light {

    public PointLight(Vector3f pos, Vector3f color, float intensity) {
        super(pos, color, intensity);
    }

    public PointLight(PointLight sun) {
        super(sun);
    }

}
