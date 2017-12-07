package net.thegaminghuskymc.sandboxgame.game.client.renderer.world;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.Renderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.RendererFactory;

import java.util.ArrayList;

public abstract class RendererFactorized extends Renderer {

    private ArrayList<RendererFactory> factories;

    public RendererFactorized(MainRenderer mainRenderer) {
        super(mainRenderer);
        this.factories = new ArrayList<>();
    }

    @Override
    public final void initialize() {
        Logger.get().log(Logger.Level.DEBUG, "Initializing " + this.getClass().getSimpleName());
        this.onInitialized();
        for (RendererFactory factory : this.factories) {
            factory.initialize();
        }
    }

    protected abstract void onInitialized();

    @Override
    public final void deinitialize() {
        this.onDeinitialized();
        for (RendererFactory factory : this.factories) {
            factory.deinitialize();
        }
        this.factories = null;
    }

    protected abstract void onDeinitialized();

    public final void addFactory(RendererFactory factory) {
        this.factories.add(factory);
    }

    public final void addFactory(RendererFactory factory, int index) {
        this.factories.add(index, factory);
    }

    @Override
    public void render() {
        for (RendererFactory factory : this.factories) {
            factory.render();
        }
    }

    @Override
    public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {
        for (RendererFactory factory : this.factories) {
            tasks.add(engine.new Callable<Taskable>() {
                @Override
                public Taskable call() throws Exception {
                    factory.update(engine.getTimer().getDt());
                    return (RendererFactorized.this);
                }

                @Override
                public String getName() {
                    return (factory.getClass().getSimpleName() + " update");
                }
            });
        }
    }
}
