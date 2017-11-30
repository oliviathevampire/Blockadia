package net.thegaminghuskymc.sandboxgame.engine.entities;

import net.thegaminghuskymc.sandboxgame.game.DummyGame;
import net.thegaminghuskymc.sandboxgame.game.Main;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EntityPlayer extends Entity {

    public EntityPlayer() {
        super("entity_player");
    }

    @Override
    public void update() {
        Vector3f pos = this.getPosition();
        Quaternionf rot = this.getRotation();
        this.setPosition(pos.x, pos.y- DummyGame.GRAVITY, pos.z);

        Main.gameLogic.getCamera().setPosition(pos);
        Main.gameLogic.getCamera().setRotation(rot);
    }

}
