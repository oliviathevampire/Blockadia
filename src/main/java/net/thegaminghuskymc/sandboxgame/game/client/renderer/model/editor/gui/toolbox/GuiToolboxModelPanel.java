package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.toolbox;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiView;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

/**
 * a view which handles model creation
 */
public abstract class GuiToolboxModelPanel extends GuiView {

    public GuiToolboxModelPanel() {
        super();
        this.setHoverable(false);
    }

    /**
     * refresh the panel
     */
    public abstract void refresh();

    /**
     * get panel title
     */
    public abstract String getTitle();

    public final EditableModel getSelectedModel() {
        return (((GuiToolboxModel) this.getParent()).getModel());
    }

    public final ModelInstance getSelectedModelInstance() {
        return (((GuiToolboxModel) this.getParent()).getModelInstance());
    }
}