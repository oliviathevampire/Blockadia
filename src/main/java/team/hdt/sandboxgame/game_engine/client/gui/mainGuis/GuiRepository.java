package team.hdt.sandboxgame.game_engine.client.gui.mainGuis;

import team.hdt.sandboxgame.game_engine.client.guis.GuiMaster;
import team.hdt.sandboxgame.game_engine.client.rendering.textures.Texture;
import team.hdt.sandboxgame.game_engine.util.MyFile;

public class GuiRepository {

	public static final Texture BACKGROUND = loadGuiTexture("panel.png");
	public static final Texture DARK_PANEL = loadGuiTexture("darkPanel.png");
	public static final Texture GREEN_BAR = loadGuiTexture("green.png");
	public static final Texture DP_ICON = loadGuiTexture("dpIcon2.png");
	public static final Texture CHECK_BOX = loadGuiTexture("checkBox.png");
	public static final Texture CIRCLE = loadGuiTexture("circle.png");
	public static final Texture SQUARE = loadGuiTexture("square.png");
	public static final Texture BLOCK = loadGuiTexture("plain.png");
	public static final Texture AA_BLOCK = loadGuiTexture("aa_block.png");
	public static final Texture INFO = loadGuiTexture("info.png");
	public static final Texture LOCKED = loadGuiTexture("locked.png");
	public static final Texture UNLOCKED = loadGuiTexture("unlocked.png");
	public static final Texture BLOB = loadGuiTexture("blob.png");
	public static final Texture INVENTORY = loadGuiTexture("inventory.png");
	public static final Texture ARROW_UP = loadGuiTexture("arrow.png");
	public static final Texture ARROW_DOWN = loadGuiTexture("arrowDown.png");
	public static final Texture CURSOR = loadGuiTexture("cursorTop.png");
	public static final Texture EXIT = loadGuiTexture("exit.png");
	public static final Texture BREED_UP = loadGuiTexture("breedTreeUp.png");
	public static final Texture BREED_DOWN = loadGuiTexture("breedTreeDown.png");
	public static final Texture SEPARATOR = loadGuiTexture("separator.png");
	public static final Texture BOX_OUTLINE = loadGuiTexture("squareBox.png");
	public static final Texture SWITCH_OFF = loadGuiTexture("switchOff.png");
	public static final Texture SWITCH_OFF_H = loadGuiTexture("switchOffBright.png");
	public static final Texture SWITCH_ON = loadGuiTexture("switchOn.png");
	public static final Texture SWITCH_ON_H = loadGuiTexture("switchOnBright.png");
	public static final Texture UNKNOWN = loadGuiTexture("unknown.png");
	public static final Texture SUN = loadGuiTexture("sun.png");
	public static final Texture DISEASE = loadGuiTexture("diseaseWhite.png");
	public static final Texture TASKS = loadGuiTexture("tasks.png");
	public static final Texture MUSIC = loadGuiTexture("music.png");
	public static final Texture PROGRESS_OUTLINE = loadGuiTexture("progressOutline.png");
	public static final Texture CONFIRM = loadGuiTexture("confirm.png");
	public static final Texture DELETE = loadGuiTexture("delete.png");
	public static final Texture TICK = loadGuiTexture("tick.png");
	public static final Texture CROSS = loadGuiTexture("cross.png");
	public static final Texture LINE = loadGuiTexture("line.png");
	public static final Texture TM = loadGuiTexture("tmSplash.png");
	public static final Texture JGM = loadGuiTexture("jgmSplash.png");
	public static final Texture DS = loadGuiTexture("dsSplash.png");
	public static final Texture BLACKOUT = loadGuiTexture("black2.png");
	public static final Texture PIN = loadGuiTexture("pin.png");
	public static final Texture NEW_SPECIES = loadGuiTexture("newSpecies.png");
	public static final Texture NEW = loadGuiTexture("new.png");
	public static final Texture BREED_ICON = loadGuiTexture("speedyBreed.png");
	public static final Texture STATS = loadPixelPerfectTexture("stats.png");
	public static final Texture DNA = loadGuiTexture("dna.png");
	public static final Texture CHECK_INNER = loadGuiTexture("checkInner.png");
	public static final Texture CHECK_OUTER = loadGuiTexture("checkOuter.png");
	public static final Texture SPINNER_UP = loadGuiTexture("spinnerUp.png");
	public static final Texture SPINNER_DOWN = loadGuiTexture("spinnerDown.png");
	public static final Texture FADE = loadGuiTexture("greenFade.png");
	public static final Texture COOL = loadGuiTexture("cool.png");
	public static final Texture EDIT = loadGuiTexture("edit.png");
	public static final Texture ACTION = loadGuiTexture("actions.png");
	public static final Texture DNA_MAIN = loadGuiTexture("dnaBigFade.png");//mainMenuDna2
	public static final Texture DNA_BUTTON = loadGuiTexture("dnaButton.png");
	public static final Texture CROSS_HAIR = loadPixelPerfectTexture("crosshair.png");
	public static final Texture SELECT_BAR = loadPixelPerfectTexture("selectorBar.png");
	public static final Texture POINTER = loadGuiTexture("pointer.png");
	public static final Texture COLOUR_WHEEL = loadGuiTexture("colourWheel.png");
	public static final Texture BRIGHT_CONTROL = loadGuiTexture("brightnessControl.png");
	public static final Texture FADE_BACK = loadGuiTexture("fadeBackground.png");
	public static final Texture EDIT_2 = loadGuiTexture("edit2.png");
	public static final Texture PROGRESS_BAR = loadGuiTexture("progBar.png");
	public static final Texture PLAY = loadGuiTexture("play.png");
	public static final Texture WAIT = loadGuiTexture("wait.png");
	public static final Texture POINT = loadGuiTexture("point.png");
	public static final Texture UPGRADES = loadGuiTexture("upgrades.png");
	public static final Texture TIER_ARROW = loadGuiTexture("upgradeTier.png");
	public static final Texture TIER_ARROW_GLOW = loadGuiTexture("upgradeTierGlow.png");
	public static final Texture REPEAT = loadGuiTexture("repeat.png");
	public static final Texture CONTROLS = loadGuiTexture("controls.png");
	public static final Texture INSECT = loadGuiTexture("butterflyIcon.png");
	public static final Texture DOWN_ARROW = loadGuiTexture("downArrow.png");
	public static final Texture NOTIFY_BELL = loadGuiTexture("notifyBell.png");
	public static final Texture NOTIFY_BELL_CIRCLE = loadGuiTexture("notifyBellCircle.png");
	public static final Texture BUFF = loadGuiTexture("buff.png");
	public static final Texture TICK_EMPTY = loadPixelPerfectTexture("emptyTick.png");
	public static final Texture TICK_FILL = loadPixelPerfectTexture("filledTick.png");
	public static final Texture PLUS = loadPixelPerfectTexture("plus.png");
	public static final Texture MINUS = loadPixelPerfectTexture("minus.png");
	public static final Texture FAST_FORWARD = loadGuiTexture("fastForward.png");
	public static final Texture CHECK_LIST = loadGuiTexture("checkList.png");
	public static final Texture RIGHT_ARROW = loadGuiTexture("rightArrow.png");
	public static final Texture LEFT_ARROW = loadGuiTexture("leftArrow.png");
	public static final Texture HELP_ICON = loadGuiTexture("help.png");
	public static final Texture HELP_LIL_ICON = loadGuiTexture("help2.png");
	public static final Texture DROP_MENU_ARROW = loadGuiTexture("dropMenuArrow.png");
	public static final Texture IMG1 = loadGuiTexture("img1.png");
	public static final Texture IMG2 = loadGuiTexture("img2.png");
	public static final Texture IMG3 = loadGuiTexture("img3.png");
	public static final Texture IMG4 = loadGuiTexture("img4.png");
	public static final Texture IMG5 = loadGuiTexture("img5.png");
	public static final Texture IMG_REQ = loadGuiTexture("imageRequired.png");
	public static final Texture CLOSE_BUTTON = loadPixelPerfectTexture("closeButton.png");
	public static final Texture BREED = loadPixelPerfectTexture("evolveIcon.png");
	public static final Texture BREED20 = loadPixelPerfectTexture("evolveIcon20.png");
	public static final Texture BREED_UP_UP = loadPixelPerfectTexture("evolveIconUp.png");
	public static final Texture DNA_ICON = loadPixelPerfectTexture("dna2.png");
	public static final Texture HAND = loadPixelPerfectTexture("handRight.png");
	public static final Texture INFO_ICON = loadPixelPerfectTexture("info2.png");
	public static final Texture INFO_ICON_20 = loadPixelPerfectTexture("infoIcon.png");
	public static final Texture STRONG = loadPixelPerfectTexture("strong.png");
	public static final Texture CIRCLE_TICK = loadPixelPerfectTexture("circleTick.png");
	public static final Texture MUSIC_ICON = loadPixelPerfectTexture("musicIcon.png");
	public static final Texture HELP_ICON2 = loadPixelPerfectTexture("helpIcon2.png");
	public static final Texture LIST_ICON = loadPixelPerfectTexture("listIcon.png");
	public static final Texture EYEDROP = loadPixelPerfectTexture("eyedrop.png");
	public static final Texture SPECIES_ICON = loadPixelPerfectTexture("speciesIcon.png");
	public static final Texture TASK_ICON = loadPixelPerfectTexture("taskIcon.png");
	public static final Texture BONUS_ICON = loadPixelPerfectTexture("bonusIcon.png");
	public static final Texture LEAF_ICON = loadPixelPerfectTexture("leafIcon.png");
	public static final Texture LOCK_ICON = loadGuiTexture("lock36.png");
	public static final Texture CIRCLE_TICK_EMPTY = loadPixelPerfectTexture("circleTickEmpty.png");
	public static final Texture CONTROL = loadPixelPerfectTexture("controls18.png");
	public static final Texture PLAYLIST = loadGuiTexture("playlist.png");
	public static final Texture HELP = loadGuiTexture("help2.png");
	public static final Texture TASKS_256 = loadGuiTexture("tasks256.png");
	public static final Texture SPECIES_256 = loadGuiTexture("species256.png");
	public static final Texture ANIMAL_128 = loadGuiTexture("animal128.png");
	public static final Texture DNA_256 = loadGuiTexture("dna256.png");
	public static final Texture TASK_DONE_256 = loadGuiTexture("taskDone256.png");
	public static final Texture ITEMS_256 = loadGuiTexture("items256.png");
	public static final Texture MUSIC_256 = loadGuiTexture("music256.png");
	public static final Texture WELCOME = loadGuiTexture("welcome.png");
	public static final Texture PROG_BAR = loadGuiTexture("loading.png");
	public static final Texture CHECK_EMPTY = loadGuiTexture("checkboxBlank.png");
	public static final Texture CHECK_FILLED = loadGuiTexture("checkboxFilled.png");
	public static final Texture ARROW_ON = loadPixelPerfectTexture("arrowRightOn.png");
	public static final Texture ARROW_OFF = loadPixelPerfectTexture("arrowRightOff.png");
	public static final Texture TICK_ICON = loadPixelPerfectTexture("tick20.png");
	public static final Texture ANIMAL_ICON = loadPixelPerfectTexture("animal3.png");
	public static final Texture SPANNER_OFF = loadPixelPerfectTexture("spannerOff.png");
	public static final Texture SPANNER_ON = loadPixelPerfectTexture("spannerOn.png");
	public static final Texture SPANNER_ONLY = loadPixelPerfectTexture("spannerOnly.png");
	
	public static final Texture[] PAGES = { loadGuiTexture("num1.png"), loadGuiTexture("num2.png"),
			loadGuiTexture("num3.png"), loadGuiTexture("num4.png"), loadGuiTexture("num5.png"),
			loadGuiTexture("num6.png"), loadGuiTexture("num7.png"), loadGuiTexture("num8.png"),
			loadGuiTexture("num9.png") };
	
	public static final Texture[] LINES = { loadGuiTexture("line0b.png"), loadGuiTexture("line1.png"),
			loadGuiTexture("line2.png"), loadGuiTexture("line3.png"), loadGuiTexture("line4.png"),
			loadGuiTexture("line5.png"), loadGuiTexture("line6.png"), loadGuiTexture("line7.png")};

	private static Texture loadGuiTexture(String name) {
		return Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, name)).noFiltering().clampEdges().create();
	}
	
	private static Texture loadPixelPerfectTexture(String name) {
		return Texture.newTexture(new MyFile(GuiMaster.GUIS_LOC, name)).noFiltering().nearestFiltering().clampEdges().create();
	}

}
