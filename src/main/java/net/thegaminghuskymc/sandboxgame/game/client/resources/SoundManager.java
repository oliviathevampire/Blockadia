package net.thegaminghuskymc.sandboxgame.game.client.resources;

import net.thegaminghuskymc.sandboxgame.engine.managers.GenericManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.game.client.openal.ALH;
import net.thegaminghuskymc.sandboxgame.game.client.openal.ALSourcePool;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraView;

/**
 * sound manager register only file sound name
 */
public class SoundManager extends GenericManager<String> {
    private static SoundManager _instance = null;
    /**
     * sources pool
     */
    private ALSourcePool soundPool;

    public SoundManager(ResourceManager manager) {
        super(manager);
        _instance = this;
    }

    public static SoundManager instance() {
        return (_instance);
    }

    @Override
    public void onInitialized() {
        ALH.alhInit();
    }

    @Override
    public void onDeinitialized() {
        ALH.alhStop();
    }

    @Override
    public void onLoaded() {
        this.soundPool = ALH.alhGenSourcePool(32);
    }

    @Override
    public void onUnloaded() {
        this.soundPool.stopAll();
        this.soundPool.destroy();
    }

    public void update(CameraView camera) {
        ALH.alhGetListener().setPosition(camera.getPosition());
        ALH.alhGetListener().setOrientation(camera.getViewVector());
    }

    @Override
    protected void onObjectRegistered(String object) {
    }
}
