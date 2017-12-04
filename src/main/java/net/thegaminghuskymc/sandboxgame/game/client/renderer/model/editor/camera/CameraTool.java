package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventMouseScroll;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiModelView;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModelLayer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

public abstract class CameraTool {

    protected final GuiModelView guiModelView;

    public CameraTool(GuiModelView guiModelView) {
        this.guiModelView = guiModelView;
    }

    public abstract String getName();

    public final void update() {
        this.guiModelView.getWorldRenderer().getLineRendererFactory().removeAllLines();
        this.onUpdate();
    }

    protected void onUpdate() {
    }

    /**
     * do the action of the tool
     *
     * @param modelInstance
     * @return : true if the model has been modified (the model mesh will be
     * re-generated)
     */
    public boolean action(ModelInstance modelInstance, EditableModelLayer modelLayer) {
        return (false);
    }

    public void onLeftPressed() {
    }

    public void onLeftReleased() {
    }

    public void onMouseScroll(GuiEventMouseScroll<GuiModelView> event) {
    }

    public void onRightPressed() {
    }

    public void onRightReleased() {
    }

    public void onMouseMove() {
    }

    public final GuiModelView getGuiModelView() {
        return (this.guiModelView);
    }

    public final float getBlockSizeUnit() {
        return (this.getModelLayer() == null ? 1.0f : this.getModelLayer().getBlockSizeUnit());
    }

    public final ModelInstance getModelInstance() {
        return (this.guiModelView.getSelectedModelInstance());
    }

    public final EditableModel getModel() {
        return (this.guiModelView.getSelectedModel());
    }

    public final EditableModelLayer getModelLayer() {
        return (this.guiModelView.getSelectedModelLayer());
    }

    public final ModelEditorCamera getCamera() {
        return (this.guiModelView.getCamera());
    }
}
