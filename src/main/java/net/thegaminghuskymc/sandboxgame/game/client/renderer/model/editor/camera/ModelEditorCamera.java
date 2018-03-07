package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Maths;
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

;

public class ModelEditorCamera extends CameraPerspectiveWorldCentered {

    public static final String[] TOOLS_NAME = {"Place", "Remove", "Paint", "Fill Surface", "Extrude", "Rigging"};
    public static final Color[] TOOLS_COLOR = {Color.BLUE, Color.RED, Color.BLUE, Color.ORANGE, Color.YELLOW,
            Color.GREEN};
    public static final CameraAction[] TOOLS_ACTIONS = {new CameraActionPlace(), new CameraActionRemove(),
            new CameraActionPaint(), new CameraActionFillSurface(), new CameraActionExtrude(),
            new CameraActionRigging()};
    public static final Class<?>[] TOOLS_SELECTORS = {CameraSelectorBlockFace.class, CameraSelectorBlock.class,
            CameraSelectorFace.class, CameraSelectorFace.class, CameraSelectorFace.class, CameraSelectorFace.class};
    /**
     * maximum number of step that can be canceled
     */
    private static final int HISTORIC_MAX_DEPTH = 16;
    private CameraTool[] tools;

    ;
    private int toolID;
    private Stack<ModelEditorState> oldStates;

    public ModelEditorCamera(GLFWWindow window) {
        super(window);
        super.setPosition(0, 0, 16);
        super.setRotX(-Maths.PI_4);
        super.setRotY(0);
        super.setRotZ(0);
        super.setFarDistance(Float.MAX_VALUE);
        super.setRenderDistance(Float.MAX_VALUE);
        this.oldStates = new Stack<ModelEditorState>();
    }

    @Override
    public void update() {
        super.update();
        if (this.getTool() != null) {
            this.getTool().update();
        }
    }

    public final CameraTool getTool() {
        return (this.tools != null ? this.tools[this.toolID] : null);
    }

    public final void setTool(int toolID) {
        this.toolID = toolID;
    }

    public final int getToolID() {
        return (this.toolID);
    }

    public void onRightReleased() {
        if (this.getTool() != null) {
            this.getTool().getCameraSelector().onRightReleased();
        }
    }

    public void onMouseMove() {
        if (this.getTool() != null) {
            this.getTool().getCameraSelector().onMouseMove();
        }
    }

    public void onMouseScroll(GuiEventMouseScroll<GuiModelView> event) {
        if (this.getTool() != null) {
            this.getTool().getCameraSelector().onMouseScroll(event);
        }
    }

    public void onRightPressed() {
        if (this.getTool() != null) {
            this.getTool().getCameraSelector().onRightPressed();
        }
    }

    public void onLeftReleased() {
        if (this.getTool() != null) {
            this.getTool().getCameraSelector().onLeftReleased();
        }
    }

    public void onLeftPressed() {
        if (this.getTool() != null) {
            this.getTool().getCameraSelector().onLeftPressed();
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
                    event.getGui().getToolbox().refresh();
                } else {
                    GuiRenderer guiRenderer = event.getGui().getWorldRenderer().getMainRenderer().getGuiRenderer();
                    guiRenderer.toast(event.getGui(), "Nothing to be canceled", false);
                }
            }
        }

        ModelInstance modelInstance = event.getGui().getSelectedModelInstance();
        EditableModelLayer modelLayer = event.getGui().getSelectedModelLayer();

        if (modelInstance != null && modelLayer != null) {

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

                    if (this.getTool().getCameraAction().action(this.getTool().getCameraSelector())) {
                        // generate mesh, save
                        while (this.oldStates.size() >= HISTORIC_MAX_DEPTH) {
                            this.oldStates.pop();
                        }
                        this.stackState(state);
                        modelLayer.requestPlanesUpdate();
                        model.requestMeshUpdate();
                        event.getGui().getToolbox().refresh();
                    }
                }
            }
        }
    }

    public final void loadTools(GuiModelView guiModelView) {
        this.tools = new CameraTool[TOOLS_NAME.length];
        for (int i = 0; i < this.tools.length; i++) {
            Class<?> selector = TOOLS_SELECTORS[i];
            CameraSelector cameraSelector;
            try {
                cameraSelector = (CameraSelector) selector.getConstructor(GuiModelView.class, Color.class)
                        .newInstance(guiModelView, TOOLS_COLOR[i]);
                this.tools[i] = new CameraTool(TOOLS_ACTIONS[i], cameraSelector);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private interface ModelEditorState {
        public void restoreState();
    }

}
