package team.hdt.blockadia.game_engine.client.rendering;

import team.hdt.blockadia.game_engine.client.glRequestProcessing.GlRequestProcessor;
import team.hdt.blockadia.game_engine.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine.client.particles.ParticleMaster;
import team.hdt.blockadia.game_engine.client.rendering.textures.TextureManager;
import team.hdt.blockadia.game_engine.client.resourceProcessing.RequestProcessor;
import team.hdt.blockadia.game_engine.common.CameraInterface;
import team.hdt.blockadia.game_engine.common.Loader;
import team.hdt.blockadia.game_engine.common.util.Display;
import team.hdt.blockadia.game_engine.common.world.misc.EnvironmentVariables;
import team.hdt.blockadia.test.game.Main;

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
        Display display = new Display("Blockadia", Main.WIDTH, Main.HEIGHT);
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
