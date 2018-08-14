package team.hdt.blockadia.game_engine.client.particles;

import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;

public class ParticleTexture {

    private Texture texture;
    private int numberOfRows;
    private boolean additive;
    private boolean glows = false;

    public ParticleTexture(Texture texture, int numberOfRows, boolean additive) {
        this.texture = texture;
        this.numberOfRows = numberOfRows;
        this.additive = additive;
    }

    public ParticleTexture setGlowy() {
        this.glows = true;
        return this;
    }

    public boolean glows() {
        return glows;
    }

    protected int getTextureID() {
        return texture.getID();
    }

    protected int getNumberOfRows() {
        return numberOfRows;
    }

    protected boolean isAdditive() {
        return additive;
    }


}
