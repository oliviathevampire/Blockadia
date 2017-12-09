package net.thegaminghuskymc.sandboxgame.game.client;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.events.EventGetTasks;
import net.thegaminghuskymc.sandboxgame.engine.events.EventListener;
import net.thegaminghuskymc.sandboxgame.engine.events.EventOnLoop;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.ResourcePack;
import net.thegaminghuskymc.sandboxgame.game.client.opencl.CLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLFWContext;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.particles.ParticleRendererFactory;
import net.thegaminghuskymc.sandboxgame.game.client.resources.ResourceManagerClient;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class GameEngineClient extends GameEngine {

    /**
     * Game window
     */
    private GLFWContext glContext;

    /**
     * Renderer
     */
    private MainRenderer renderer;

    /**
     * tasks to be run in a gl context
     */
    private ArrayList<MainRenderer.GLTask> glTasks;

    private ParticleRendererFactory rendererFactory = new ParticleRendererFactory(renderer);

    public GameEngineClient() {
        super(Side.CLIENT);
    }

    public static GameEngineClient instance() {
        return ((GameEngineClient) GameEngine.instance());
    }

    /**
     * initialize libraries + window
     */
    @Override
    protected final void onInitialized() {

        // load assets pack
        String assets = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "../assets.zip";
        super.putAssets(new ResourcePack("GameEngine", assets));

        // init opengl
        GLH.glhInit();
        this.glContext = GLH.glhCreateContext(GLH.glhCreateWindow());
        GLH.glhSetContext(this.glContext);

        Logger.get().log(Logger.Level.FINE, "OpenGL initiated.");
        Logger.get().log(Logger.Level.FINE, "GLFW version: " + GLFW.glfwGetVersionString());

        // init opencl
        CLH.clhInit();

        // main renderer
        this.renderer = new MainRenderer(this);
        this.renderer.initialize();

        this.glTasks = new ArrayList<>();
    }

    @Override
    public void load() {
        super.load();

        // event callback
        this.registerEventCallback(new EventListener<EventOnLoop>() {
            @Override
            public void invoke(EventOnLoop event) {

                // run tasks
                for (MainRenderer.GLTask glTask : glTasks) {
                    glTask.run();
                }
                glTasks.clear();

                // window update has to be done in the main thread
                // BEGIN FRAME
                getGLFWWindow().clearScreen();

                // render has to be done in the main thread
                // RENDER THE FRAME
                getRenderer().render();

                // FLUSH THE FRAME
                getGLFWWindow().flushScreen();
                getGLFWWindow().pollEvents();

                if (getGLFWWindow().shouldClose()) {
                    stopRunning();
                }

                getTimer().update();

                try {
                    // ensure 120 fps, not more, not less
                    long toSleep = 1000 / (150 + 20) - (long) (getTimer().getDt() * 1000) + 1;
                    if (toSleep > 0 && toSleep < 20) {
                        Thread.sleep(toSleep);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // get tasks
        this.registerEventCallback(new EventListener<EventGetTasks>() {
            @Override
            public void invoke(EventGetTasks event) {
                // get all renderer tasks
                renderer.getTasks(event.getEngine(), event.getTasksList());
            }
        });
    }

    @Override
    protected void onDeinitialized() {
        GLH.glhStop();
    }

    @Override
    protected ResourceManager instanciateResourceManager() {
        return (new ResourceManagerClient(this));
    }

    /**
     * a task to be run on a gl context (will be run on the next main thread
     * update)
     */
    public final void addGLTask(MainRenderer.GLTask glTask) {
        this.glTasks.add(glTask);
    }

    /**
     * return the main renderer
     */
    public MainRenderer getRenderer() {
        return (this.renderer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResourceManagerClient getResourceManager() {
        return ((ResourceManagerClient) super.resources);
    }

    /**
     * get the window
     */
    public final GLFWWindow getGLFWWindow() {
        return (this.getGLContext().getWindow());
    }

    public final GLFWContext getGLContext() {
        return (this.glContext);
    }
}
