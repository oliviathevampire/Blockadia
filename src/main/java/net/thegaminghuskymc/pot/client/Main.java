package net.thegaminghuskymc.pot.client;

import net.thegaminghuskymc.futopia.common.Futopia;
import net.thegaminghuskymc.pot.common.PeopleOfToss;
import net.thegaminghuskymc.pot.common.entities.EntityBipedTest;
import net.thegaminghuskymc.pot.common.world.POTWorlds;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.ResourcePack;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.game.client.BlockitectEngineClient;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraPerspectiveWorldFree;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiViewDebug;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiViewWorld;

public class Main {

    public static BlockitectEngineClient engine = new BlockitectEngineClient();

    public static void main(String[] args) {
        engine.initialize();

        engine.getModLoader().injectMod(PeopleOfToss.class);
        engine.getModLoader().injectMod(Futopia.class);
        engine.putAssets(new ResourcePack("blockitect", "./assets.zip"));

        engine.load();

        prepareEngine(engine);

        engine.loop();
        engine.uninitialized();
    }

    private static void prepareEngine(BlockitectEngineClient engine) {
        engine.loadWorld(POTWorlds.DEFAULT);
        engine.getGLFWWindow().swapInterval(1);
        engine.getGLFWWindow().setScreenPosition(100, 100);

        CameraPerspectiveWorldFree camera = new CameraPerspectiveWorldFree(engine.getGLFWWindow());
        camera.setPosition(-6.0f, 80.0f, 18.0f);
        World world = engine.getWorld(POTWorlds.DEFAULT);
        EntityBipedTest player = new EntityBipedTest(world);
        player.setPosition(-6.0f, 80.0f, 18.0f);
        world.spawnEntity(player);
        camera.setWorld(world);
//        camera.setEntity(player);

        engine.getRenderer().getGuiRenderer().addGui(new GuiViewWorld(camera, POTWorlds.DEFAULT));
        engine.getRenderer().getGuiRenderer().addGui(new GuiViewDebug(camera));
    }

}
