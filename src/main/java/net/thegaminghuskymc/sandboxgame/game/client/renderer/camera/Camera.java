package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

public abstract class Camera {

    private int _state;

    /**
     * aspect_ratio is WIDTH / HEIGHT
     */
    public Camera() {
        this._state = 0;
    }

    public boolean hasState(int state) {
        return ((this._state & state) == state);
    }

    public void setState(int state) {
        this._state = this._state | state;
    }

    public void unsetState(int state) {
        this._state = this._state & ~(state);
    }

    public void update() {

    }

    public abstract Camera clone();

}