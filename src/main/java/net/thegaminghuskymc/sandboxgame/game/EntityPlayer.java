package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.Entity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EntityPlayer extends Entity {

    @Override
    public void update() {
        Vector3f pos = this.getPosition();
        Quaternionf rot = this.getRotation();
        this.setPosition(pos.x, pos.y-DummyGame.GRAVITY, pos.z);

        Main.gameLogic.camera.setPosition(pos);
        Main.gameLogic.camera.setRotation(rot);
    }

}
