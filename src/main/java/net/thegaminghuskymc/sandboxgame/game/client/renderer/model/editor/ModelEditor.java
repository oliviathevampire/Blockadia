package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.game.client.BlockitectEngineClient;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiModelEditor;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiModelView;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.toolbox.GuiToolbox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.json.JSONModelExporter;

public class ModelEditor {

    private static ModelEditor instance;
    private GuiModelEditor guiModelEditor;

    public static void main(String[] args) {
        new ModelEditor().run();
    }

    private void run() {

        instance = this;

        /* 1 */
        // initialize engine
        BlockitectEngineClient engine = new BlockitectEngineClient();
        try {
            engine.initialize();

            /* 2 */
            // inject resources to be loaded
            engine.getModLoader().injectMod(ModelEditorMod.class);

            // load resources (mods)
            engine.load();

            /* prepare engine before looping */
            this.prepareEngine(engine);

            /* 3 */
            // loop, every allocated memory will be released properly on program
            // termination */

            engine.loop();
        } catch (Exception e) {
            Logger.get().log(Logger.Level.ERROR,
                    "That's unfortunate... VoxelEngine crashed. Please send me the following crash report.");
            Logger.get().log(Logger.Level.ERROR, e);
            e.printStackTrace(Logger.get().getPrintStream());
            String path = R.getResPath("models/tmp/" + System.currentTimeMillis());
            try {
                Logger.get().log(Logger.Level.ERROR, "Trying to save model", path);
                JSONModelExporter.export(guiModelEditor.getSelectedModel(), path);
            } catch (Exception exception) {
                Logger.get().log(Logger.Level.ERROR, "Couldn't save model... sorry bro",
                        exception.getLocalizedMessage());
            }
        }
        engine.uninitialized();
    }

    private void prepareEngine(BlockitectEngineClient engine) {

        engine.loadWorld(ModelEditorMod.WORLD_ID);

        engine.getGLFWWindow().swapInterval(1);
        engine.getGLFWWindow().setScreenPosition(100, 100);

        this.guiModelEditor = new GuiModelEditor();
        this.guiModelEditor.setBox(0, 0, 1.0f, 1.0f, 0);
        engine.getRenderer().getGuiRenderer().addGui(this.guiModelEditor);
    }

    public final ModelEditor instance() {
        return (instance);
    }

    public final GuiToolbox getToolbox() {
        return (this.guiModelEditor.getToolbox());
    }

    public final GuiModelView getModelView() {
        return (this.guiModelEditor.getModelView());
    }
}
