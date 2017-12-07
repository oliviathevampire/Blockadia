package net.thegaminghuskymc.sandboxgame.testmod.client;

import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.ResourcePack;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.game.client.GameEngineClient;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraPerspectiveWorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.*;
import net.thegaminghuskymc.sandboxgame.testmod.common.ModPOT;
import net.thegaminghuskymc.sandboxgame.testmod.common.entities.EntityBipedTest;
import net.thegaminghuskymc.sandboxgame.testmod.common.world.POTWorlds;

public class Main {

    public static GameEngineClient engine = new GameEngineClient();

    public static void main(String[] args) {
        engine.initialize();

        engine.getModLoader().injectMod(ModPOT.class);
        engine.putAssets(new ResourcePack("sandbox_game", "./assets.zip"));

        engine.load();

        prepareEngine(engine);

        try {
            engine.loop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        engine.deinitialize();
    }

    private static void prepareEngine(GameEngineClient engine) {
        engine.loadWorld(POTWorlds.DEFAULT);
        engine.getGLFWWindow().swapInterval(1);
        engine.getGLFWWindow().setScreenPosition(100, 100);

        CameraPerspectiveWorldEntity camera = new CameraPerspectiveWorldEntity(engine.getGLFWWindow());
        camera.setPosition(0.0f, 170.0f, -40.0f);
        World world = engine.getWorld(POTWorlds.DEFAULT);
        EntityBipedTest player = new EntityBipedTest(world);
        player.setPosition(16, 200, 16);
        world.spawnEntity(player);
        camera.setWorld(world);
        camera.setEntity(player);

        engine.getRenderer().getGuiRenderer().addGui(new GuiViewMainMenu());
    }

}
