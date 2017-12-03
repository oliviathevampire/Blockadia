package net.thegaminghuskymc.sandboxgame.engine.packets;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;

public interface INetwork {
	/** stop the network */
	public void stop();

	/** get side of the network */
	public GameEngine.Side getSide();
}
