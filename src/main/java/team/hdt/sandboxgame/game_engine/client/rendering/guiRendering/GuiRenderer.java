package team.hdt.sandboxgame.game_engine.client.rendering.guiRendering;

import basics.Loader;
import basics.MasterRenderer;
import org.lwjgl.opengl.GL11;
import team.hdt.sandboxgame.game_engine.client.guis.GuiTexture;
import team.hdt.sandboxgame.game_engine.util.toolbox.OpenglUtils;

import java.util.List;

public class GuiRenderer {

	private static final float[] POSITIONS = { 0, 0, 0, 1, 1, 0, 1, 1 };

	private GuiShader shader;
	private int vao;

	public GuiRenderer() {
		shader = new GuiShader();
		vao = Loader.createInterleavedVAO(POSITIONS, 2);
	}

	public void render(List<GuiTexture> guis) {
		prepare();
		for (GuiTexture gui : guis) {
			renderGui(gui);
		}
		endRendering();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

	private void prepare() {
		OpenglUtils.antialias(false);
		OpenglUtils.enableAlphaBlending();
		OpenglUtils.disableDepthTesting();
		OpenglUtils.cullBackFaces(true);
		shader.start();
		OpenglUtils.bindVAO(vao, 0);
	}

	private void renderGui(GuiTexture gui) {
		if (!gui.getTexture().isLoaded()) {
			return;
		}
		OpenglUtils.bindTextureToBank(gui.getTexture().getID(), 0);
		OpenglUtils.bindTextureToBank(MasterRenderer.getOutputTexture(), 1);
		setScissorTest(gui.getClippingBounds());
		if(gui.isAdditive()){
			OpenglUtils.enableAdditiveBlending();
		}
		shader.alpha.loadFloat(gui.getAlpha());
		shader.usesBlur.loadBoolean(gui.usesBlur());
		shader.flipTexture.loadBoolean(gui.isFlipTexture());
		shader.transform.loadVec4(gui.getPosition().x, gui.getPosition().y, gui.getScale().x, gui.getScale().y);
		shader.useOverrideColour.loadBoolean(gui.hasOverrideColour());
		if(gui.hasOverrideColour()){
			shader.overrideColour.loadVec3(gui.getOverrideColour().getVector());
		}
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		if(gui.isAdditive()){
			OpenglUtils.enableAlphaBlending();
		}
	}

	private void endRendering() {
		OpenglUtils.disableScissorTest();
		OpenglUtils.unbindVAO(0);
		shader.stop();
		OpenglUtils.disableBlending();
		OpenglUtils.enableDepthTesting();
	}
	
	private void setScissorTest(int[] bounds){
		if(bounds==null){
			OpenglUtils.disableScissorTest();
		}else{
			OpenglUtils.enableScissorTest(bounds[0], bounds[1], bounds[2], bounds[3]);
		}
	}

}
