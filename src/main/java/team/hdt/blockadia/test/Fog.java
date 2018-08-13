package team.hdt.blockadia.test;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class Fog {

    private boolean active;

    private Vectors3f colour;

    private float density;

    public static Fog NOFOG = new Fog();
    
    public Fog() {
        active = false;
        this.colour = new Vectors3f(0, 0, 0);
        this.density = 0;
    }

    public Fog(boolean active, Vectors3f colour, float density) {
        this.colour = colour;
        this.density = density;
        this.active = active;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the color
     */
    public Vectors3f getColour() {
        return colour;
    }

    /**
     * @param colour the color to set
     */
    public void setColour(Vectors3f colour) {
        this.colour = colour;
    }

    /**
     * @return the density
     */
    public float getDensity() {
        return density;
    }

    /**
     * @param density the density to set
     */
    public void setDensity(float density) {
        this.density = density;
    }
}