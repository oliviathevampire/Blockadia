package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera;

import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraPerspectiveWorldCentered;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventKeyPress;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventMouseScroll;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiModelView;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.toolbox.GuiToolboxModel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModelLayer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;
import org.lwjgl.glfw.GLFW;

import java.util.Stack;

public class ModelEditorCamera extends CameraPerspectiveWorldCentered {

    /**
     * maximum number of step that can be canceled
     */
    private static final int HISTORIC_MAX_DEPTH = 16;
    private CameraTool[] tools;
    private int toolID;
    private Stack<ModelEditorState> oldStates;

    public ModelEditorCamera(GLFWWindow window) {
        super(window);
        super.setPosition(0, 16, 0);
        super.setPositionVelocity(0, 0, 0);
        super.setRotationVelocity(0, 0, 0);
        super.setPitch(0);
        super.setYaw(0);
        super.setRoll(0);
        super.setSpeed(0.2f);
        super.setRotSpeed(1);
        super.setFarDistance(Float.MAX_VALUE);
        super.setRenderDistance(Float.MAX_VALUE);
        super.setDistanceFromCenter(16);
        super.setAngleAroundCenter(-45);
        this.oldStates = new Stack<ModelEditorState>();
    }

    @Override
    public void update() {
        super.update();
        this.tools[this.toolID].update();
    }

    ;

    public void onRightReleased() {
        if (this.tools[this.toolID] != null) {
            this.tools[this.toolID].onRightReleased();
        }
    }

    public void onMouseMove() {
        if (this.tools[this.toolID] != null) {
            this.tools[this.toolID].onMouseMove();
        }
    }

    public void onMouseScroll(GuiEventMouseScroll<GuiModelView> event) {
        if (this.tools[this.toolID] != null) {
            this.tools[this.toolID].onMouseScroll(event);
        }
    }

    public void onRightPressed() {
        if (this.tools[this.toolID] != null) {
            this.tools[this.toolID].onRightPressed();
        }
    }

    public void onLeftReleased() {
        if (this.tools[this.toolID] != null) {
            this.tools[this.toolID].onLeftReleased();
        }
    }

    public void onLeftPressed() {
        if (this.tools[this.toolID] != null) {
            this.tools[this.toolID].onLeftPressed();
        }
    }

    protected final void stackState(ModelEditorState state) {
        this.oldStates.push(state);
    }

    protected final void unstackState() {
        if (this.oldStates.size() == 0) {
            return;
        }
        ModelEditorState state = this.oldStates.pop();
        state.restoreState();
    }

    public void onKeyPress(GuiEventKeyPress<GuiModelView> event) {
        ModelInstance modelInstance = event.getGui().getSelectedModelInstance();
        EditableModelLayer modelLayer = event.getGui().getSelectedModelLayer();

        if (modelInstance == null || modelLayer == null) {
            return;
        }
        EditableModel model = ((EditableModel) modelInstance.getModel());

        if (this.tools[this.toolID] != null) {
            if (event.getKey() == GLFW.GLFW_KEY_Z) {
                // do a deep copy of the current model block data

                final EditableModelLayer layerCopy = modelLayer.clone();

                ModelEditorState state = new ModelEditorState() {
                    @Override
                    public void restoreState() {
                        model.setLayer(layerCopy);
                        model.requestMeshUpdate();
                    }
                };

                if (this.tools[this.toolID].action(modelInstance, modelLayer)) {
                    // generate mesh, save
                    while (this.oldStates.size() >= HISTORIC_MAX_DEPTH) {
                        this.oldStates.pop();
                    }
                    this.stackState(state);
                    modelLayer.requestPlanesUpdate();
                    model.requestMeshUpdate();
                }
            }
        }

        GuiToolboxModel modelPanel = event.getGui().getToolbox().getModelToolbox();
        if (modelPanel != null) {
            if (event.getKey() == GLFW.GLFW_KEY_E) {
                modelPanel.getGuiToolboxModelPanelBuild().selectNextTool();
            } else if (event.getKey() == GLFW.GLFW_KEY_Q) {
                modelPanel.getGuiToolboxModelPanelBuild().selectPreviousTool();
            } else if (event.getKey() == GLFW.GLFW_KEY_D) {
                modelPanel.selectNextPanel();
            } else if (event.getKey() == GLFW.GLFW_KEY_A) {
                modelPanel.selectPreviousPanel();
            } else if (event.getKey() == GLFW.GLFW_KEY_W
                    && event.getGLFWWindow().isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) {
                if (this.oldStates.size() > 0) {
                    ModelEditorState state = this.oldStates.pop();
                    state.restoreState();
                } else {
                    GuiRenderer guiRenderer = event.getGui().getWorldRenderer().getMainRenderer().getGuiRenderer();
                    guiRenderer.toast(event.getGui(), "Nothing to be canceled", false);
                }
            }
        }
    }

    public final void loadTools(GuiModelView guiModelView) {
        this.tools = new CameraTool[]{new CameraToolPlace(guiModelView), new CameraToolPaint(guiModelView),
                new CameraToolRemove(guiModelView), new CameraToolExtrude(guiModelView),
                new CameraToolRigging(guiModelView)};
    }

    public CameraTool getTool() {
        return (this.tools != null ? this.tools[this.toolID] : null);
    }

    public void setTool(int toolID) {
        this.toolID = toolID;
    }

    private interface ModelEditorState {
        public void restoreState();
    }
}
