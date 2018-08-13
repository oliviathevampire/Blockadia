package team.hdt.blockadia.game_engine.client.skybox;

import org.lwjgl.opengl.GL11;
import team.hdt.blockadia.game_engine.client.rendering.EngineMaster;
import team.hdt.blockadia.game_engine.client.rendering.MasterRenderer;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.test.Main;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.common.world.misc.EnvironmentVariables;
import team.hdt.blockadia.game_engine.util.FileUtils;
import team.hdt.blockadia.game_engine.util.MyFile;
import team.hdt.blockadia.game_engine.util.toolbox.Maths;
import team.hdt.blockadia.game_engine.util.toolbox.OpenglUtils;

public class SkyboxRenderer {

	private static final float SIZE = 550;
	private static final float STAR_SPEED = 0.0006f;
	private static final int SEG_COUNT = 25;
	private static final MyFile STAR_FILE = new MyFile(FileUtils.RES_FOLDER,"nightSky.png");

	private int vao;
	private SkyboxShader shader;
	private Matrix4fs viewMatrix = new Matrix4fs();
	private Matrix4fs pvMatrix = new Matrix4fs();
	private Texture skyTexture;
	
	private float time = 0;

	public SkyboxRenderer() {
		initShader();
		vao = new CurvedMeshGenerator(SEG_COUNT, (float) (Math.PI/2f)).generateMeshVao();
		skyTexture = Texture.newTexture(STAR_FILE).create();
		shader.start();
		shader.segCount.loadFloat(SEG_COUNT);
		shader.stop();
	}

	public void render() {
		prepare();
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, (SEG_COUNT + 1) * 2);
		finishRendering();
	}

	public void cleanUp() {
		skyTexture.delete();
		shader.cleanUp();
	}

	private void prepare() {
		OpenglUtils.bindVAO(vao, 0);
		GL11.glDepthMask(false);
		OpenglUtils.bindTextureToBank(skyTexture.getID(), 0);
		shader.start();
		time += Main.getGameSeconds() * STAR_SPEED;
		time %= 1;
		shader.time.loadFloat(time);
		shader.starBrightness.loadFloat(EnvironmentVariables.starBrightness);
		shader.horizonColour.loadVec3(EnvironmentVariables.horizonColour.getVector());
		shader.skyColour.loadVec3(EnvironmentVariables.skyColour.getVector());
		shader.scroll.loadFloat(EngineMaster.getCamera().getYaw()/(EngineMaster.getCamera().getFOV() * 2));
		Maths.createViewMatrix(viewMatrix, new Vectors3f(0, 0, 0), EngineMaster.getCamera().getPitch(), 0);
		Matrix4fs.mul(MasterRenderer.getProjectionMatrix(), viewMatrix, pvMatrix);
		shader.pvMatrix.loadMatrix(pvMatrix);
		
	}

	private void finishRendering() {
		OpenglUtils.unbindVAO(0);
		GL11.glDepthMask(true);
		shader.stop();
	}
	
	private void initShader(){
		shader = new SkyboxShader();
		shader.start();
		shader.skyboxSize.loadFloat(SIZE);
		shader.stop();
	}

}
