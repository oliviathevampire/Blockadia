package team.hdt.blockadia.test;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class PointLight {

    private Vectors3f color;

    private Vectors3f position;

    private float intensity;

    private Attenuation attenuation;
    
    public PointLight(Vectors3f color, Vectors3f position, float intensity) {
        attenuation = new Attenuation(1, 0, 0);
        this.color = color;
        this.position = position;
        this.intensity = intensity;
    }

    public PointLight(Vectors3f color, Vectors3f position, float intensity, Attenuation attenuation) {
        this(color, position, intensity);
        this.attenuation = attenuation;
    }

    public PointLight(PointLight pointLight) {
        this(new Vectors3f(pointLight.getColor()), new Vectors3f(pointLight.getPosition()),
                pointLight.getIntensity(), pointLight.getAttenuation());
    }

    public Vectors3f getColor() {
        return color;
    }

    public void setColor(Vectors3f color) {
        this.color = color;
    }

    public Vectors3f getPosition() {
        return position;
    }

    public void setPosition(Vectors3f position) {
        this.position = position;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
    }

    public static class Attenuation {

        private float constant;

        private float linear;

        private float exponent;

        public Attenuation(float constant, float linear, float exponent) {
            this.constant = constant;
            this.linear = linear;
            this.exponent = exponent;
        }

        public float getConstant() {
            return constant;
        }

        public void setConstant(float constant) {
            this.constant = constant;
        }

        public float getLinear() {
            return linear;
        }

        public void setLinear(float linear) {
            this.linear = linear;
        }

        public float getExponent() {
            return exponent;
        }

        public void setExponent(float exponent) {
            this.exponent = exponent;
        }
    }
}