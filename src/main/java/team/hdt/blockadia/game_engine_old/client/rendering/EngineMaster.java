package team.hdt.blockadia.game_engine_old.client.rendering;

import team.hdt.blockadia.game_engine.client.MainExtras;
import team.hdt.blockadia.game_engine_old.client.glRequestProcessing.GlRequestProcessor;
import team.hdt.blockadia.game_engine_old.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine_old.client.particles.ParticleMaster;
import team.hdt.blockadia.game_engine_old.client.rendering.textures.TextureManager;
import team.hdt.blockadia.game_engine_old.client.resourceProcessing.RequestProcessor;
import team.hdt.blockadia.game_engine_old.common.CameraInterface;
import team.hdt.blockadia.game_engine_old.common.Loader;
import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.world.misc.EnvironmentVariables;

public class EngineMaster {

    private static CameraInterface sceneCamera;

    /**
     * Initialise the engine. Carries out any necessary initialisations.
     *
     * @param camera - The camera being used in-game.
     */
    public static void init(CameraInterface camera) {
        sceneCamera = camera;
//        UserConfigs.loadConfigs();
//        GameText.init(UserConfigs.getLanguage().ordinal());
        Display display = new Display("Blockadia", MainExtras.WIDTH, MainExtras.HEIGHT);
        display.run();
        MasterRenderer.init(camera);
        ParticleMaster.init(MasterRenderer.getProjectionMatrix());
    }

    /**
     * @return The camera being used to render the scene.
     */
    public static CameraInterface getCamera() {
        return sceneCamera;
    }

    /**
     * Updates many systems that need to be updated every frame.
     */
    public static void preRenderUpdate() {
        sceneCamera.moveCamera();
        EnvironmentVariables.getVariables().update();
        GuiMaster.updateGuis();
        ParticleMaster.update(sceneCamera);
    }

    /**
     * Updates the display and carries out some outstanding OpenGL calls.
     */
    public static void update() {
        GlRequestProcessor.dealWithTopRequests();
    }

    /**
     * Deals with closing down the game and all necessary systems.
     */
    public static void close() {
        RequestProcessor.cleanUp();
        GlRequestProcessor.completeAllRequests();
        TextureManager.cleanUp();
        Loader.cleanUpModelMemory();
        MasterRenderer.cleanUp();
        GlRequestProcessor.completeAllRequests();
    }

}
