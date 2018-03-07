package net.thegaminghuskymc.sandboxgame.game.client.renderer.sky;

import net.thegaminghuskymc.sandboxgame.engine.world.Sky;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.RendererFactory;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;

public class SkyRendererFactory extends RendererFactory {

    private CameraProjective camera;
    private Sky sky;

    public SkyRendererFactory(MainRenderer mainRenderer) {
        super(mainRenderer);
    }

    @Override
    public void update(double dt) {

    }

    public final CameraProjective getCamera() {
        return (this.camera);
    }

    public final void setCamera(CameraProjective camera) {
        this.camera = camera;
    }

    public final Sky getSky() {
        return (this.sky);
    }

    public final void setSky(Sky sky) {
        this.sky = sky;
    }

    @Override
    public void render() {
        this.getMainRenderer().getSkyRenderer().render(this.getCamera(), this.getSky());
    }
}
