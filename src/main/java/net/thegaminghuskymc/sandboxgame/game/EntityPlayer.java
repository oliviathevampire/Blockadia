package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.Entity;

public class EntityPlayer extends Entity {

	public EntityPlayer() {
		super();
		height = 2;
	}
	
    @Override
    public void update() {
    	super.update();

        Main.gameLogic.camera.setPosition(getPosition());
        Main.gameLogic.camera.setRotation(getRotation());
    }

}
