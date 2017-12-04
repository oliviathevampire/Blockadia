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

package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.font;

import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLTexture;

public class Font {
    /** opengl textureID */
    private GLTexture texture;

    /** font data */
    private FontFile fontFile;

    public Font(String filepath) {
        this.texture = GLH.glhGenTexture(filepath + ".png");
        this.fontFile = new FontFile(filepath + ".fnt");
    }

    public GLTexture getTexture() {
        return (this.texture);
    }

    public FontFile getFile() {
        return (this.fontFile);
    }
}
