package team.hdt.blockadia.game_engine.client.guis;

import team.hdt.blockadia.game_engine.client.rendering.fontRendering.Text;
import team.hdt.blockadia.game_engine.client.rendering.guiRendering.GuiRenderData;
import team.hdt.blockadia.game_engine.util.FileUtils;
import team.hdt.blockadia.game_engine.util.MyFile;

public class GuiMaster {

    public static final MyFile GUIS_LOC = new MyFile(FileUtils.RES_FOLDER, "guis");
    public static final GuiScreenContainer CONTAINER = new GuiScreenContainer();

    private static final GuiRenderData renderData = new GuiRenderData();

    private static boolean mouseInteraction = true;
    private static GuiComponent currentFocus = null;
    private static boolean removeFocus = false;

    public static void updateGuis() {
        if (removeFocus) {
            removeFocus();
        }
        renderData.clear();
        CONTAINER.update(renderData);
    }

    //TODO test whether mouse over popup as well? Would slightly break biome picker click-off though.
    public static boolean isMouseInGui() {
        return CONTAINER.isMouseInGui();
    }

    public static void focusOn(GuiComponent newFocus) {
        if (currentFocus != null) {
            removeFocus();
        }
        newFocus.setFocus(true);
        currentFocus = newFocus;
    }

    public static void releaseFocus(GuiComponent oldFocus) {
        if (currentFocus == oldFocus) {
            removeFocus = true;
        }
    }

    private static void removeFocus() {
        currentFocus.setFocus(false);
        currentFocus = null;
        removeFocus = false;
    }

    public static boolean clickedOffGui() {
        if (!isMouseInGui()) {
			/*MyMouse mouse = MyMouse.getActiveMouse();
			if(mouse.isLeftClick() || mouse.isRightClick() || mouse.isMiddleClick()){
				return true;
			}*/
        }
        return false;
    }

    public static boolean isInFocus(GuiComponent component) {
        return currentFocus == null || component.inFocus();
    }

    public static void enableMouseInteraction(boolean enable) {
        mouseInteraction = enable;
    }

    public static boolean isMouseInteractionEnabled() {
        return mouseInteraction;
    }

    public static GuiRenderData getRenderData() {
        return renderData;
    }

    public static void addComponent(GuiComponent component, float relX, float relY, float relScaleX, float relScaleY) {
        CONTAINER.addComponent(component, relX, relY, relScaleX, relScaleY);
    }

    public static void addResizingComponent(GuiComponent component) {
        CONTAINER.addComponent(component, 0, 0, 1, 1);
    }

    public static void addText(Text text, float relX, float relY, float relLineWidth) {
        CONTAINER.addText(text, relX, relY, relLineWidth);
    }

    public static void removeText(Text text) {
        CONTAINER.deleteText(text);
    }

}
