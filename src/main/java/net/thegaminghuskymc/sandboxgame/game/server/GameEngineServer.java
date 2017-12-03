package net.thegaminghuskymc.sandboxgame.game.server;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;

public class GameEngineServer extends GameEngine {

	public GameEngineServer() {
		super(Side.SERVER);
	}

	@Override
	protected ResourceManager instanciateResourceManager() {
		return (new ResourceManagerServer(this));
	}

	@Override
	protected void onInitialized() {
	}

	@Override
	protected void onDeinitialized() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResourceManager getResourceManager() {
		return (super.resources);
	}
}
