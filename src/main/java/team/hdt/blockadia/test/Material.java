package team.hdt.blockadia.test;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors4f;

public class Material {

    private static final Vectors4f DEFAULT_COLOUR = new Vectors4f(1.0f, 1.0f, 1.0f, 1.0f);

    private Vectors4f ambientColour;

    private Vectors4f diffuseColour;

    private Vectors4f specularColour;
    
    private float reflectance;

    private Texture texture;
    
    private Texture normalMap;

    public Material() {
        this.ambientColour = DEFAULT_COLOUR;
        this.diffuseColour = DEFAULT_COLOUR;
        this.specularColour = DEFAULT_COLOUR;
        this.texture = null;
        this.reflectance = 0;
    }

    public Material(Vectors4f colour, float reflectance) {
        this(colour, colour, colour, null, reflectance);
    }

    public Material(Texture texture) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0);
    }

    public Material(Texture texture, float reflectance) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance);
    }

    public Material(Vectors4f ambientColour, Vectors4f diffuseColour, Vectors4f specularColour, Texture texture, float reflectance) {
        this.ambientColour = ambientColour;
        this.diffuseColour = diffuseColour;
        this.specularColour = specularColour;
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public Vectors4f getAmbientColour() {
        return ambientColour;
    }

    public void setAmbientColour(Vectors4f ambientColour) {
        this.ambientColour = ambientColour;
    }

    public Vectors4f getDiffuseColour() {
        return diffuseColour;
    }

    public void setDiffuseColour(Vectors4f diffuseColour) {
        this.diffuseColour = diffuseColour;
    }

    public Vectors4f getSpecularColour() {
        return specularColour;
    }

    public void setSpecularColour(Vectors4f specularColour) {
        this.specularColour = specularColour;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public boolean isTextured() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    
    public boolean hasNormalMap() {
        return this.normalMap != null;
    }

    public Texture getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(Texture normalMap) {
        this.normalMap = normalMap;
    }
}