/**
 * *	This file is part of the project https://github.com/toss-dev/VoxelEngine
 * *
 * *	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
 * *
 * *	PEREIRA Romain
 * *                                       4-----7
 * *                                      /|    /|
 * *                                     0-----3 |
 * *                                     | 5___|_6
 * *                                     |/    | /
 * *                                     1-----2
 */

package net.thegaminghuskymc.sandboxgame.game.client.renderer.particles;

public class ParticleBillboarded extends WorldObjectParticle {

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
    public ParticleBillboarded(int health, TextureSprite sprite, boolean glows) {
        super(health);
        this.glows = glows;
        this._sprite = sprite;
    }

    public ParticleBillboarded(int health, TextureSprite sprite) {
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
