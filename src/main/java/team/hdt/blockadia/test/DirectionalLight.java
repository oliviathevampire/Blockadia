package team.hdt.blockadia.test;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class DirectionalLight {
    
    private Vectors3f color;

    private Vectors3f direction;

    private float intensity;

    private OrthoCoords orthoCords;
    
    private float shadowPosMult;
    
    public DirectionalLight(Vectors3f color, Vectors3f direction, float intensity) {
        this.orthoCords = new OrthoCoords();
        this.shadowPosMult = 1;
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
        shadowPosMult = 1;
    }

    public DirectionalLight(DirectionalLight light) {
        this(new Vectors3f(light.getColor()), new Vectors3f(light.getDirection()), light.getIntensity());
    }

    public float getShadowPosMult() {
        return shadowPosMult;
    }
    
    public void setShadowPosMult(float shadowPosMult) {
        this.shadowPosMult = shadowPosMult;
    }
    
    public OrthoCoords getOrthoCoords(){
        return orthoCords;
    }
    
    public void setOrthoCords(float left, float right, float bottom, float top, float near, float far) {
        orthoCords.left = left;
        orthoCords.right = right;
        orthoCords.bottom = bottom;
        orthoCords.top = top;
        orthoCords.near = near;
        orthoCords.far = far;
    }
    
    public Vectors3f getColor() {
        return color;
    }

    public void setColor(Vectors3f color) {
        this.color = color;
    }

    public Vectors3f getDirection() {
        return direction;
    }

    public void setDirection(Vectors3f direction) {
        this.direction = direction;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    
    public static class OrthoCoords {
        
        public float left;
        
        public float right;
        
        public float bottom;
        
        public float top;

        public float near;
        
        public float far;
    }
}