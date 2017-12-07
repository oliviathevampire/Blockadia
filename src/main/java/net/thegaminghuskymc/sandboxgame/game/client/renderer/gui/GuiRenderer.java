package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.Taskable;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLTexture;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.Renderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.Gui;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiButton;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiLabel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiView;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.font.Font;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.font.FontModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Main GUI renderer class
 * <p>
 * <p>
 * Optimization to implement later on:
 * <p>
 * -GuiInputManager : only update when needed (rendering list changed, or new
 * input event occured)
 * <p>
 * -Rendering process: render to a GLTexture, and only re-render if needed (if
 * any gui's called for redraw)
 *
 * @author Romain
 */

public class GuiRenderer extends Renderer {
    /**
     * fonts
     */
    public static Font DEFAULT_FONT;
    /**
     * rendering program
     */
    private ProgramFont programFont;
    private ProgramTexturedQuad programTexturedQuad;
    private ProgramColoredQuad programColoredQuad;
    /**
     * Fonts
     */
    private Map<String, Font> fonts;

    /**
     * the main gui, parent of every other guis
     */
    private Gui mainGui;
    private GuiInputManagerDesktop guiInputManager;

    /**
     * gui rendering list (sorted by layers)
     */
    private ArrayList<Gui> renderingList;

    public GuiRenderer(MainRenderer renderer) {
        super(renderer);
    }

    /**
     * wrappers for tinyfd library
     *
     * @param title       : dialog title
     * @param defaultPath : dialog default path
     * @return : the absolute path to the selected folder
     */
    public static String dialogSelectFolder(String title, String defaultPath) {
        return (TinyFileDialogs.tinyfd_selectFolderDialog(title, defaultPath));
    }

    public static Color dialogPickColor() {
        String value = TinyFileDialogs.tinyfd_colorChooser("Pick a color", "#FFFFFF", null,
                BufferUtils.createByteBuffer(3 * 4));
        if (value == null) {
            return (null);
        }
        Color color = new Color((int) Long.parseLong(value.replace("#", ""), 16));
        return (color);
    }

    @Override
    public void initialize() {
        Logger.get().log(Logger.Level.FINE, "Initializing " + this.getClass().getSimpleName());
        this.fonts = new HashMap<>();
        this.programColoredQuad = new ProgramColoredQuad();
        this.programTexturedQuad = new ProgramTexturedQuad();
        this.programFont = new ProgramFont();
        this.mainGui = new GuiView();
        this.guiInputManager = new GuiInputManagerDesktop();
        this.guiInputManager.initialize(this.getMainRenderer().getGLFWWindow());
        this.renderingList = new ArrayList<>();
        this.loadFonts();
    }

    @Override
    public void deinitialize() {
        this.mainGui.deinitialize(this);
        this.guiInputManager.deinitialize();
        this.fonts.clear();
        this.programColoredQuad.delete();
        this.programTexturedQuad.delete();
        this.programFont.delete();
    }

    /**
     * load every fonts
     */
    private void loadFonts() {
        DEFAULT_FONT = this.getFont("Consolas");
    }

    private Font registerFont(String name) {
        Font font = new Font(R.getResPath("font/" + name));
        this.fonts.put(name, font);
        return (font);
    }

    private Font getFont(String fontname) {
        Font font = this.fonts.get(fontname);
        if (font == null) {
            return (this.registerFont(fontname));
        }
        return (font);
    }

    @Override
    public void render() {

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // render them in the correct order
        for (Gui gui : this.renderingList) {
            gui.render(this);
        }

        GL11.glDisable(GL11.GL_BLEND);
    }

    /**
     * a recursive helper to generate rendering list
     */
    private void addGuisToRenderingList(Gui parent) {
        if (parent.getChildren() != null) {
            for (Gui child : parent.getChildren()) {
                if (child.isVisible()) {
                    this.renderingList.add(child);
                    this.addGuisToRenderingList(child);
                }
            }
        }
    }

    public void renderFontModel(FontModel model, float transparency, Matrix4f transfMatrix) {
        this.programFont.useStart();
        this.programFont.bindFontModel(model, transparency, transfMatrix);
        model.render();
    }

    public void renderTexturedQuad(GLTexture glTexture, float ux, float uy, float vx, float vy, float transparency,
                                   Matrix4f transformMatrix) {
        this.programTexturedQuad.useStart();
        this.programTexturedQuad.loadQuadTextured(glTexture, ux, uy, vx, vy, transparency, transformMatrix);
        GLH.glhDrawArrays(GL11.GL_POINTS, 0, 1);
    }

    public void renderColoredQuad(float r, float g, float b, float a, float transparency, Matrix4f transformMatrix) {
        this.programColoredQuad.useStart();
        this.programColoredQuad.loadQuadColored(r, g, b, a * transparency, transformMatrix);
        GLH.glhDrawArrays(GL11.GL_POINTS, 0, 1);
    }

    @Override
    public void getTasks(GameEngine engine, ArrayList<GameEngine.Callable<Taskable>> tasks) {
        updateGuis();
    }

    private void updateGuis() {
        this.renderingList.clear();
        this.addGuisToRenderingList(this.mainGui);
        this.renderingList.sort(Gui.LAYER_COMPARATOR);
        this.guiInputManager.update(this.renderingList);
    }

    /**
     * add a view to render
     */
    public final void addGui(Gui gui) {
        this.mainGui.addChild(gui);
    }

    /**
     * toast a message on the screen
     */
    private void toast(Gui parent, String text, Font font, float r, float g, float b, float a, double time) {
        GuiLabel guiLabel = new GuiLabel();
        guiLabel.setBox(0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
        guiLabel.addTextParameter(new GuiTextParameterTextCenterBox());
        guiLabel.setText(text);
        guiLabel.setFontColor(r, g, b, a);
        guiLabel.setFontSize(1.0f, 1.0f);
        guiLabel.addTask(new Gui.GuiTask() {
            double dt = 0.0f;

            @Override
            public boolean run() {
                this.dt += getTimer().getDt();
                if (this.dt >= time) {
                    guiLabel.pop();
                    return (true);
                }
                return (false);
            }
        });
        parent.addChild(guiLabel);
        guiLabel.setLayer(guiLabel.getTopestLayer() + 1);
    }

    private void toast(String str) {
        this.toast(str, 0, 1, 0, 1, 0.5);
    }

    public void toast(Gui parent, String str, boolean good) {
        if (good) {
            this.toast(parent, str, 0, 1, 0, 1, 0.5);
        } else {
            this.toast(parent, str, 1, 0, 0, 1, 0.5);
        }
    }

    private void toast(String str, float r, float g, float b, float a, double time) {
        this.toast(this.mainGui, str, r, g, b, a, time);
    }

    private void toast(Gui parent, String str, float r, float g, float b, float a, double time) {
        this.toast(parent, str, GuiRenderer.DEFAULT_FONT, r, g, b, a, time);
    }

    /**
     * a callback when the window is resized
     */
    @Override
    public void onWindowResize(GLFWWindow window) {
        this.mainGui.onWindowResized(window.getWidth(), window.getHeight(), window.getAspectRatio());
    }

}
