
package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiWindowRigging;

public class CameraActionRigging implements CameraAction {

	@Override
	public boolean action(CameraSelector cameraSelector) {
		GuiWindowRigging gui = new GuiWindowRigging(cameraSelector);
		gui.open(cameraSelector.getGuiModelView());
		return (false);

	}

	@Override
	public void update() {

	}

}
