package net.thegaminghuskymc.sandboxgame.game.client.renderer.particles;

public class ParticleBillboarded extends Particle {

    /** sprite to use */
    private TextureSprite _sprite;

    /** true if the particle is blowing (additive blending) */
    private boolean glows;

    /**
     * constructor of a billboarded particles:
     *
     * @param health
     *            : health for this particle
     * @param sprite
     *            : the sprite to use
     * @param glows
     *            : if the particle glows
     */
    private ParticleBillboarded(int health, TextureSprite sprite, boolean glows) {
        super(health);
        this.glows = glows;
        this._sprite = sprite;
    }

    private ParticleBillboarded(int health, TextureSprite sprite) {
        this(health, sprite, false);
    }

    public ParticleBillboarded(TextureSprite sprite) {
        this(500, sprite);
    }

    public final TextureSprite getSprite() {
        return (this._sprite);
    }

    public final boolean isGlowing() {
        return (this.glows);
    }
}
