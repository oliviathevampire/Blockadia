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

package net.thegaminghuskymc.sandboxgame.game.client.opengl.window.event;

import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;

public class GLFWEventWindowResize extends GLFWEvent {

    private final int width;
    private final int height;

    public GLFWEventWindowResize(GLFWWindow window) {
        super(window);
        this.width = window.getWidth();
        this.height = window.getHeight();
    }

    public final int getWidth() {
        return (this.width);
    }

    public final int getHeight() {
        return (this.height);
    }

}
