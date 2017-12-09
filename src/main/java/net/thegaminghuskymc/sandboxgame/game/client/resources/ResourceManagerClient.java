package net.thegaminghuskymc.sandboxgame.game.client.resources;

import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.game.client.GameEngineClient;

public class ResourceManagerClient extends ResourceManager {

    /**
     * block renderer manager
     */
    private BlockRendererManager blockTextureManager;

    /**
     * Sound manager
     */
    private SoundManager soundManager;

    /**
     * Models manager
     */
    private ModelManager modelManager;

    public ResourceManagerClient(GameEngineClient engine) {
        super(engine);
    }

    @Override
    protected void addManagers() {
        super.addManagers();

        this.blockTextureManager = new BlockRendererManager(this);
        super.addManager(this.blockTextureManager);

        this.soundManager = new SoundManager(this);
        super.addManager(this.soundManager);

        this.modelManager = new ModelManager(this);
        super.addManager(this.modelManager);
    }

    /**
     * the block renderer manager
     */
    public final BlockRendererManager getBlockTextureManager() {
        return (this.blockTextureManager);
    }

    /**
     * get the model manager
     */
    public final ModelManager getModelManager() {
        return (this.modelManager);
    }

}