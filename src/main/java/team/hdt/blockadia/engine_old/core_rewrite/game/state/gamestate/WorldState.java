package team.hdt.blockadia.old_engine_code_1.core_rewrite.game.state.gamestate;

import org.joml.Vector2f;
import org.joml.Vector4f;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.Display;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.entity.EntityPlayer;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.entity.item.EntityItem;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.item.ItemStack;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.state.GameState;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.game.world.World;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.gui.Gui;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.gui.GuiPlayerInventory;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.light.Light;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.renderer.MasterRenderer;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.handler.Items;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.keybind.Keybinds;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.object.ICamera;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WorldState extends GameState {

	private Gui displayedGui;
	private World world;

	@Override
	public void init() {
		displayedGui = null;
		world = new World();
		world.addEntity(new EntityPlayer());

		int i = 0;
		for (String registryName : Items.REGISTRY.keySet()) {
			world.addEntity(new EntityItem(new ItemStack(Items.byName(registryName), Objects.requireNonNull(Items.byName(registryName)).getMaxStackSize()), 50 + 25 * i++, 50));
		}
	}

	@Override
	public void update() {
		world.update();
		if (displayedGui != null) {
			displayedGui.update();
		}
	}

	@Override
	public void render(MasterRenderer renderer, ICamera camera, double mouseX, double mouseY, float partialTicks) {
		world.render(renderer, camera, partialTicks);
		if (displayedGui != null) {
			renderer.renderGuis(displayedGui);
		}

		renderer.renderLights(new Light(new Vector2f((float) Display.getMouseX() / MasterRenderer.scale + camera.getPosition().x, (float) Display.getMouseY() / MasterRenderer.scale + camera.getPosition().y), new Vector4f(1, 1, 1, 50), 25));
	}

	@Override
	public void onKeyPressed(int keyCode) {
		if (keyCode == Keybinds.OPEN_INVENTORY.getKeyCode()) {
			this.openGui(0);
		}
	}

	@Override
	public void onKeyReleased(int keyCode) {
	}

	@Override
	public void onJoystickMoved(double xDirection, double yDirection) {
		Objects.requireNonNull(world.getPlayer()).onJoystickMoved(xDirection, yDirection);
	}

	@Override
	public void save(File saveFolder) {
		try {
			File savesFolder = new File(saveFolder, "saves");
			if (!savesFolder.exists()) {
				savesFolder.mkdirs();
			}
			world.save(savesFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(File saveFolder) {
		try {
			File savesFolder = new File(saveFolder, "saves");
			if (savesFolder.exists()) {
				world.load(savesFolder);
			} else {
				savesFolder.mkdirs();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		world.generate();
	}

	@Override
	public void openGui(int type) {
		if (type == 0) {
			this.displayedGui = new GuiPlayerInventory(world.getPlayer().getInventory());
		}
	}
}