package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;

import net.thegaminghuskymc.sandboxgame.game.client.BlockitectEngineClient;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjectiveWorld;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventAspectRatio;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiListener;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.flat.WorldFlatRenderer;

public class GuiViewWorld extends GuiView {

    private GuiTexture txWorld;
    private WorldFlatRenderer worldRenderer;
    private CameraProjective camera;
    private int worldID;

    public GuiViewWorld() {
        super();
        this.txWorld = new GuiTexture();
        this.txWorld.setHoverable(false);
        this.txWorld.setBox(0, 0, 1, 1, 0);
    }

    public GuiViewWorld(CameraProjective camera, int worldID) {
        this();
        this.set(camera, worldID);
    }

    public final void set(CameraProjective camera, int worldID) {
        this.camera = camera;
        this.worldID = worldID;
    }

    @Override
    protected void onInitialized(GuiRenderer renderer) {
        MainRenderer mainRenderer = renderer.getMainRenderer();

        this.worldRenderer = new WorldFlatRenderer(mainRenderer);

        this.worldRenderer.initialize();
        this.worldRenderer.setCamera(this.camera);
        this.worldRenderer.setWorld(this.worldID);
        mainRenderer.addRenderer(this.worldRenderer);

        this.txWorld.setTexture(this.worldRenderer.getFBOTexture(), 0.0f, 0.0f, 1.0f, 1.0f);
        this.addChild(this.txWorld);

        this.addListener(new GuiListener<GuiEventAspectRatio<Gui>>() {
            @Override
            public void invoke(GuiEventAspectRatio<Gui> event) {
                BlockitectEngineClient.instance().addGLTask(new MainRenderer.GLTask() {
                    @Override
                    public void run() {
                        worldRenderer.resizeFbo();
                    }
                });
            }
        });
    }

    /**
     * deinitialize the gui: this function is call in opengl main thread
     */
    protected void onDeinitialized(GuiRenderer renderer) {
        this.worldRenderer.deinitialize();
    }

    @Override
    protected void onUpdate() {
        if (this.worldRenderer == null) {
            return;
        }
        if (this.worldRenderer.getCamera() instanceof CameraProjectiveWorld) {
            ((CameraProjectiveWorld) this.worldRenderer.getCamera()).setWorld(this.worldRenderer.getWorld());
        }
        this.worldRenderer.getCamera().update();

        // TODO update this by setting world renderer viewport
        float aspect = GLH.glhGetWindow().getAspectRatio() * super.getTotalAspectRatio();
        this.worldRenderer.getCamera().setAspect(aspect);
    }

    public final WorldFlatRenderer getWorldRenderer() {
        return (this.worldRenderer);
    }
}
