package team.hdt.blockadia.game_engine.common;

import team.hdt.blockadia.game_engine.client.ClientMain;
import team.hdt.blockadia.game_engine.client.gui.mainGuis.ColourPalette;
import team.hdt.blockadia.game_engine.client.gui.userInterfaces.GuiImage;
import team.hdt.blockadia.game_engine.client.guis.GuiComponent;
import team.hdt.blockadia.game_engine.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine.client.guis.GuiTexture;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.gameManaging.GameManager;
import team.hdt.blockadia.game_engine.common.gameManaging.GameState;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.util.MyFile;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.ConstantDriver;
import team.hdt.blockadia.game_engine.util.visualFxDrivers.SlideDriver;

public class FirstScreenUi extends GuiComponent {

	private static final Texture BACK_TEX = Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, "startBackground.png"))
			.noFiltering().clampEdges().create();
	private static final Texture PLAIN_TEX = Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, "plain.png"))
			.noFiltering().clampEdges().create();
	private static final Texture LOGO_TEX = Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, "equilinoxLogo3.png"))
			.noFiltering().clampEdges().create();

	private static final Texture TM_LOGO = Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, "tmSplash.png"))
			.noFiltering().clampEdges().create();
	private static final Texture DS_LOGO = Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, "dsSplash.png"))
			.noFiltering().clampEdges().create();
	private static final Texture JG_LOGO = Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, "jgmSplash.png"))
			.noFiltering().clampEdges().create();

	private static final float START = 0.4f;

	private static final float SLIDE_SPEED = 3f;
	private static final float START_HEIGHT = -0.05f;
	private static final float END_HEIGHT = 0.05f;

	private static final float SLIDE_TIME = 1f;
	private static final float FLASH_TIME = 0.8f;
	private static final float EXTRAS_TIME = 1.4f;
	private static final float READY_TIME = EXTRAS_TIME + SLIDE_TIME + 0.1f;

	private boolean logoFadingIn = false;
	private boolean extrasFadingIn = false;
	private boolean waiting = false;
	private boolean fadeOut = false;
	private boolean endPhase = false;

	private GuiTexture background;
	private GuiImage logo;

	private GuiImage tmLogo;
	private GuiImage jgLogo;
	private GuiImage dsLogo;

	private GuiImage whiteFlash;

	private float time = 0;

	private int bufferFrames = 0;

	public FirstScreenUi() {
		background = new GuiTexture(BACK_TEX);

		logo = createImage(LOGO_TEX);
		this.tmLogo = createImage(TM_LOGO);
		this.jgLogo = createImage(JG_LOGO);
		this.dsLogo = createImage(DS_LOGO);
		whiteFlash = new GuiImage(PLAIN_TEX);
		whiteFlash.getTexture().setOverrideColour(ColourPalette.WHITE);
		whiteFlash.getTexture().setAlphaDriver(new ConstantDriver(0));
		super.setRenderLevel(1);
	}

	@Override
	protected void init() {
		super.init();
		super.addComponentX(logo, 0, START_HEIGHT, 1);
		super.addCenteredComponentX(tmLogo, 0.2f, 0.75f, 0.1f);
		super.addCenteredComponentX(jgLogo, 0.5f, 0.75f, 0.1f);
		super.addCenteredComponentX(dsLogo, 0.8f, 0.75f, 0.1f);
		super.addComponent(whiteFlash, 0, 0, 1, 1);
	}

	public boolean isReady() {
		return waiting;
	}

	@Override
	protected void updateGuiTexturePositions(Vectors2f position, Vectors2f scale) {
		background.setPosition(position.x, position.y, scale.x, scale.y);
	}

	@Override
	protected void updateSelf() {
		background.update();
		time += ClientMain.getDeltaSeconds();
		if (!logoFadingIn && time > START) {
			startAnimation();
		}
		if (logoFadingIn) {
			slideLogo();
		}
		if (!extrasFadingIn && time > EXTRAS_TIME) {
			startExtrasFading();
		}
		if (!waiting && time > READY_TIME) {
			waiting = true;
		}
		if (fadeOut) {
			doFlash();

		} else if (waiting) {
			checkFinishedLoading();
		}

	}

	@Override
	protected void getGuiTextures(GuiRenderData data) {
		if (!endPhase) {
			data.addTexture(getLevel(), background);
		}
	}

	private void startAnimation() {
		logo.getTexture().setAlphaDriver(new SlideDriver(0, 1, SLIDE_TIME));
		logoFadingIn = true;
	}

	private void slideLogo() {
		float difference = END_HEIGHT - logo.getRelativeY();
		float change = difference * ClientMain.getDeltaSeconds() * SLIDE_SPEED;
		logo.setRelativeY(logo.getRelativeY() + change);
	}

	private void startExtrasFading() {
		extrasFadingIn = true;
		tmLogo.getTexture().setAlphaDriver(new SlideDriver(0, 1, SLIDE_TIME));
		jgLogo.getTexture().setAlphaDriver(new SlideDriver(0, 1, SLIDE_TIME));
		dsLogo.getTexture().setAlphaDriver(new SlideDriver(0, 1, SLIDE_TIME));
	}

	private GuiImage createImage(Texture texture) {
		GuiImage image = new GuiImage(texture);
		image.setPreferredAspectRatio(4);
		image.getTexture().setAlphaDriver(new ConstantDriver(0));
		return image;
	}

	private void checkFinishedLoading() {
		/*boolean loaded = GameManager.sessionManager.hasWorldReady() && !GameManager.sessionManager.isLoading();
		if (loaded) {
			bufferFrames++;
			if (bufferFrames == 5) {
				this.fadeOut = true;
				whiteFlash.getTexture().setAlphaDriver(new SlideDriver(0, 1, FLASH_TIME));
			}
		}*/
	}

	private void doFlash() {
		if (endPhase) {
			if (whiteFlash.getTexture().getAlpha() <= 0) {
				remove();
			}
		} else if (whiteFlash.getTexture().getAlpha() >= 1) {
			initMenu();
		}
	}

	private void initMenu() {
		whiteFlash.getTexture().setAlphaDriver(new SlideDriver(1, 0, FLASH_TIME));
//		tmLogo.remove();
//		jgLogo.remove();
//		logo.remove();
//		dsLogo.remove();
		GameManager.gameState.setState(GameState.CAMERA);
//		endPhase = true;
	}

}
