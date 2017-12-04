package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.toolbox;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiButton;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiPrompt;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSpinner;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventClick;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiListener;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiPromptEventHeldTextChanged;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiSpinnerEventPick;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiPromptEditor;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiSpinnerEditor;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModelLayer;

public class GuiToolboxModelPanelBuild extends GuiToolboxModelPanel {

    /**
     * the building tools list
     */
    private final GuiSpinner tools;

    // TODO
    private final GuiButton addLayer;
    private final GuiSpinnerEditor layers;
    private final GuiButton removeLayer;

    /**
     * the model block size unit slider bar
     */
    private final GuiPromptEditor modelBlockSizeUnit;

    private final GuiButton tx;
    private final GuiButton ty;
    private final GuiButton tz;

    private final GuiButton txm;
    private final GuiButton tym;
    private final GuiButton tzm;

    private final GuiButton rx;
    private final GuiButton ry;
    private final GuiButton rz;

    public GuiToolboxModelPanelBuild() {
        super();
        this.tools = new GuiSpinnerEditor();
        this.addLayer = new GuiButton();
        this.layers = new GuiSpinnerEditor();
        this.removeLayer = new GuiButton();
        this.modelBlockSizeUnit = new GuiPromptEditor("Size-unit:", "block size");

        this.rx = new GuiButton();
        this.ry = new GuiButton();
        this.rz = new GuiButton();
        this.tx = new GuiButton();
        this.ty = new GuiButton();
        this.tz = new GuiButton();
        this.txm = new GuiButton();
        this.tym = new GuiButton();
        this.tzm = new GuiButton();

    }

    @Override
    public final void onInitialized(GuiRenderer guiRenderer) {

        this.tools.setHint("Tools...");
        this.tools.add(0, "Place");
        this.tools.add(1, "Paint");
        this.tools.add(2, "Remove");
        this.tools.add(3, "Extrude");
        this.tools.add(4, "Rigging");
        this.tools.pick(0);
        this.tools.setBox(0, 0.70f, 1.0f, 0.05f, 0);
        this.addChild(this.tools);

        // layers
        this.addLayer.setText("Add");
        this.addLayer.setBox(0.0f, 0.65f, 1 / 3.0f, 0.05f, 0.0f);
        this.addLayer.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.addLayer.addTextParameter(new GuiTextParameterTextCenterBox());
        this.addLayer.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                // TODO
            }
        });
        this.addChild(this.addLayer);

        this.layers.setHint("layers...");
        this.layers.setBox(1 / 3.0f, 0.65f, 1 / 3.0f, 0.05f, 0);
        for (EditableModelLayer layer : this.getSelectedModel().getRawLayers().values()) {
            this.layers.add(layer, layer.getName());
        }
        this.layers.pick(0);
        this.layers.addListener(new GuiListener<GuiSpinnerEventPick<GuiSpinner>>() {
            @Override
            public void invoke(GuiSpinnerEventPick<GuiSpinner> event) {
                onLayerPicked();
            }
        });
        this.addChild(this.layers);

        this.removeLayer.setText("Remove");
        this.removeLayer.setBox(2 * 1 / 3.0f, 0.65f, 1 / 3.0f, 0.05f, 0.0f);
        this.removeLayer.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.removeLayer.addTextParameter(new GuiTextParameterTextCenterBox());
        this.removeLayer.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                // TODO
                refresh();
            }
        });
        this.addChild(this.removeLayer);

        // block size unit slider bar
        this.modelBlockSizeUnit.setBox(0, 0.60f, 1.0f, 0.05f, 0);
        this.modelBlockSizeUnit.getPrompt().addListener(new GuiListener<GuiPromptEventHeldTextChanged<GuiPrompt>>() {

            @Override
            public void invoke(GuiPromptEventHeldTextChanged<GuiPrompt> event) {
                onBlockSizeUnitChanged();
            }
        });
        this.addChild(this.modelBlockSizeUnit);

        float w = 1.0f / 3.0f;

        // translate
        this.tx.setText("+X");
        this.tx.setBox(0.0f, 0.55f, w, 0.05f, 0.0f);
        this.tx.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.tx.addTextParameter(new GuiTextParameterTextCenterBox());
        this.tx.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().translate(1, 0, 0);
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.tx);

        this.ty.setText("+Y");
        this.ty.setBox(w, 0.55f, w, 0.05f, 0.0f);
        this.ty.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.ty.addTextParameter(new GuiTextParameterTextCenterBox());
        this.ty.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().translate(0, 1, 0);
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.ty);

        this.tz.setText("+Z");
        this.tz.setBox(2.0f * w, 0.55f, w, 0.05f, 0.0f);
        this.tz.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.tz.addTextParameter(new GuiTextParameterTextCenterBox());
        this.tz.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().translate(0, 0, 1);
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.tz);

        // translate
        this.txm.setText("-X");
        this.txm.setBox(0.0f, 0.50f, w, 0.05f, 0.0f);
        this.txm.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.txm.addTextParameter(new GuiTextParameterTextCenterBox());
        this.txm.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().translate(-1, 0, 0);
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.txm);

        this.tym.setText("-Y");
        this.tym.setBox(w, 0.50f, w, 0.05f, 0.0f);
        this.tym.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.tym.addTextParameter(new GuiTextParameterTextCenterBox());
        this.tym.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().translate(0, -1, 0);
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.tym);

        this.tzm.setText("-Z");
        this.tzm.setBox(2.0f * w, 0.50f, w, 0.05f, 0.0f);
        this.tzm.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.tzm.addTextParameter(new GuiTextParameterTextCenterBox());
        this.tzm.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().translate(0, 0, -1);
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.tzm);

        // rotate
        this.rx.setText("RX");
        this.rx.setBox(0.0f, 0.45f, w, 0.05f, 0.0f);
        this.rx.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.rx.addTextParameter(new GuiTextParameterTextCenterBox());
        this.rx.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().rotateX();
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.rx);

        this.ry.setText("RY");
        this.ry.setBox(w, 0.45f, w, 0.05f, 0.0f);
        this.ry.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.ry.addTextParameter(new GuiTextParameterTextCenterBox());
        this.ry.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().rotateY();
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.ry);

        this.rz.setText("RZ");
        this.rz.setBox(2.0f * w, 0.45f, w, 0.05f, 0.0f);
        this.rz.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
        this.rz.addTextParameter(new GuiTextParameterTextCenterBox());
        this.rz.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
            @Override
            public void invoke(GuiEventClick<GuiButton> event) {
                getSelectedModelLayer().rotateZ();
                getSelectedModelLayer().requestPlanesUpdate();
                getSelectedModel().requestMeshUpdate();
            }
        });
        this.addChild(this.rz);
    }

    @Override
    public void refresh() {
        EditableModelLayer layer = this.getSelectedModelLayer();
        this.modelBlockSizeUnit.setVisible(layer != null);
        this.rx.setVisible(layer != null);
        this.ry.setVisible(layer != null);
        this.rz.setVisible(layer != null);
        this.tx.setVisible(layer != null);
        this.ty.setVisible(layer != null);
        this.tz.setVisible(layer != null);
        this.txm.setVisible(layer != null);
        this.tym.setVisible(layer != null);
        this.tzm.setVisible(layer != null);

        if (layer != null) {
            this.modelBlockSizeUnit.setValue(layer.getBlockSizeUnit());
        }

        String v = this.getSelectedModelLayer() == null ? "0.0"
                : String.valueOf(this.getSelectedModelLayer().getBlockSizeUnit());
        this.modelBlockSizeUnit.getPrompt().setHeldText(v);
    }

    private final void onBlockSizeUnitChanged() {
        EditableModelLayer layer = this.getSelectedModelLayer();
        if (layer == null) {
            return;
        }
        float sizeUnit = 1.0f;
        try {
            sizeUnit = this.modelBlockSizeUnit.getPrompt().asFloat();
        } catch (Exception e) {
            this.modelBlockSizeUnit.getPrompt().setHeldText("1.0");
        }
        if (sizeUnit == layer.getBlockSizeUnit()) {
            return;
        }
        layer.setBlockSizeUnit(sizeUnit);
        layer.requestPlanesUpdate();
    }

    @Override
    public String getTitle() {
        return ("Build");
    }

    public final int getSelectedTool() {
        return ((Integer) this.tools.getPickedObject());
    }

    private final void onLayerPicked() {
        EditableModelLayer layer = this.getSelectedModelLayer();
        if (layer == null) {
            return;
        }
        this.refresh();
    }

    public final EditableModelLayer getSelectedModelLayer() {
        return ((EditableModelLayer) this.layers.getPickedObject());
    }

    public final int selectNextTool() {
        this.tools.pick((this.tools.getPickedIndex() + 1) % this.tools.count());
        return (this.tools.getPickedIndex());
    }

    public final int selectPreviousTool() {
        int index = this.tools.getPickedIndex() - 1;
        if (index < 0) {
            index = this.tools.count() - 1;
        }
        this.tools.pick(index);
        return (index);
    }
}
