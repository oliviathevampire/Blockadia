package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.toolbox;

import net.thegaminghuskymc.sandboxgame.engine.util.math.Quaternion;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiButton;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiLabel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiPrompt;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.GuiSpinner;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventClick;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiListener;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiPromptEventHeldTextChanged;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiSpinnerEventPick;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelSkeleton;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.Bone;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiPopUpCallback;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiPromptEditor;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiSpinnerEditor;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiWindowNewBone;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModel;

public class GuiToolboxModelPanelSkeleton extends GuiToolboxModelPanel {

    private final GuiButton addBone;
    private final GuiSpinnerEditor bones;
    private final GuiButton removeBone;

    private final GuiLabel boneTransformLabel;

    private final GuiPromptEditor posX;
    private final GuiPromptEditor posY;
    private final GuiPromptEditor posZ;

    private final GuiPromptEditor rotX;
    private final GuiPromptEditor rotY;
    private final GuiPromptEditor rotZ;

    public GuiToolboxModelPanelSkeleton() {
        super();
        this.addBone = new GuiButton();
        this.bones = new GuiSpinnerEditor();
        this.removeBone = new GuiButton();

        this.boneTransformLabel = new GuiLabel();

        this.posX = new GuiPromptEditor("X", "pos. x");
        this.posY = new GuiPromptEditor("Y", "pos. y");
        this.posZ = new GuiPromptEditor("Z", "pos. z");

        this.rotX = new GuiPromptEditor("RX", "rot. x");
        this.rotY = new GuiPromptEditor("RY", "rot. y");
        this.rotZ = new GuiPromptEditor("RZ", "rot. z");
    }

    @Override
    public final void onInitialized(GuiRenderer guiRenderer) {
        {
            this.addChild(this.addBone);
            this.addChild(this.bones);
            this.addChild(this.removeBone);

            this.addBone.setText("Add");
            this.addBone.setBox(0.0f, 0.70f, 1 / 3.0f, 0.05f, 0.0f);
            this.addBone.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
            this.addBone.addTextParameter(new GuiTextParameterTextCenterBox());
            this.addBone.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
                @Override
                public void invoke(GuiEventClick<GuiButton> event) {
                    new GuiWindowNewBone(getSelectedModel(), new GuiPopUpCallback<GuiWindowNewBone>() {

                        @Override
                        public void onConfirm(GuiWindowNewBone win) {
                            EditableModel model = getSelectedModel();
                            Bone bone = new Bone(model.getSkeleton(), win.name.getHeldText());
                            String parentName = (String) win.parent.getPickedObject();
                            if (parentName != null) {
                                bone.setParent(parentName);
                                model.getSkeleton().getBone(parentName).addChild(bone.getName());
                            }
                            model.getSkeleton().addBone(bone);
                            bones.add(bone, bone.getName());
                            bones.pick(bones.count() - 1);
                            guiRenderer.toast("Bone added.");
                            refresh();

                        }

                        @Override
                        public void onCancel(GuiWindowNewBone win) {
                        }
                    }).open(getOldestParent());
                }
            });

            this.bones.setHint("Bones...");
            this.bones.setBox(1 / 3.0f, 0.70f, 1 / 3.0f, 0.05f, 0);
            this.bones.addListener(new GuiListener<GuiSpinnerEventPick<GuiSpinner>>() {
                @Override
                public void invoke(GuiSpinnerEventPick<GuiSpinner> event) {
                    onBonePicked();
                }
            });
            for (Bone bone : this.getModelSkeleton().getBones()) {
                this.bones.add(bone, bone.getName());
            }
            this.bones.pick(0);

            this.removeBone.setText("Remove");
            this.removeBone.setBox(2 * 1 / 3.0f, 0.70f, 1 / 3.0f, 0.05f, 0.0f);
            this.removeBone.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
            this.removeBone.addTextParameter(new GuiTextParameterTextCenterBox());
            this.removeBone.addListener(new GuiListener<GuiEventClick<GuiButton>>() {
                @Override
                public void invoke(GuiEventClick<GuiButton> event) {
                    Bone bone = getSelectedBone();
                    getModelSkeleton().removeBone(bone);
                    bones.remove(bone);
                    guiRenderer.toast("Bone removed.");
                    refresh();
                }
            });
        }

        {
            this.boneTransformLabel.setBox(0, 0.65f, 1, 0.05f, 0);
            this.boneTransformLabel.setText("Local Bone Transform");
            this.boneTransformLabel.setFontColor(0, 0, 0, 1.0f);
            this.boneTransformLabel.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
            this.boneTransformLabel.addTextParameter(new GuiTextParameterTextCenterBox());
            this.addChild(this.boneTransformLabel);
        }

        {
            this.addChild(this.posX);
            this.addChild(this.posY);
            this.addChild(this.posZ);
            this.posX.setBox(0, 0.60f, 1.0f, 0.05f, 0);
            this.posY.setBox(0, 0.55f, 1.0f, 0.05f, 0);
            this.posZ.setBox(0, 0.50f, 1.0f, 0.05f, 0);
        }

        {
            this.addChild(this.rotX);
            this.addChild(this.rotY);
            this.addChild(this.rotZ);

            this.rotX.setBox(0, 0.45f, 1.0f, 0.05f, 0);
            this.rotY.setBox(0, 0.40f, 1.0f, 0.05f, 0);
            this.rotZ.setBox(0, 0.35f, 1.0f, 0.05f, 0);
        }
        GuiListener<GuiPromptEventHeldTextChanged<GuiPrompt>> listener = new GuiListener<GuiPromptEventHeldTextChanged<GuiPrompt>>() {
            @Override
            public void invoke(GuiPromptEventHeldTextChanged<GuiPrompt> event) {
                float rx = rotX.getPrompt().asFloat(0.0f);
                float ry = rotY.getPrompt().asFloat(0.0f);
                float rz = rotZ.getPrompt().asFloat(0.0f);
                float rw = 1.0f;
                float x = posX.getPrompt().asFloat(0.0f);
                float y = posY.getPrompt().asFloat(0.0f);
                float z = posZ.getPrompt().asFloat(0.0f);
                getSelectedBone().setLocalBindTransform(x, y, z, rx, ry, rz, rw);
                getSelectedBone().calcInverseBindTransform();
            }
        };
        this.posX.getPrompt().addListener(listener);
        this.posY.getPrompt().addListener(listener);
        this.posZ.getPrompt().addListener(listener);
        this.rotX.getPrompt().addListener(listener);
        this.rotY.getPrompt().addListener(listener);
        this.rotZ.getPrompt().addListener(listener);
    }

    @Override
    public void refresh() {

        Bone bone = this.getSelectedBone();
        if (bone != null) {
            this.posX.getPrompt().setHeldText(String.valueOf(bone.getLocalTranslation().x));
            this.posY.getPrompt().setHeldText(String.valueOf(bone.getLocalTranslation().y));
            this.posZ.getPrompt().setHeldText(String.valueOf(bone.getLocalTranslation().z));

            Vector3f rot = Quaternion.toEulerAngle(bone.getLocalRotation());
            this.rotX.getPrompt().setHeldText(String.valueOf(rot.x));
            this.rotY.getPrompt().setHeldText(String.valueOf(rot.y));
            this.rotZ.getPrompt().setHeldText(String.valueOf(rot.z));
        }

        this.boneTransformLabel.setVisible(bone != null);
        this.posX.setVisible(bone != null);
        this.posY.setVisible(bone != null);
        this.posZ.setVisible(bone != null);
        this.rotX.setVisible(bone != null);
        this.rotY.setVisible(bone != null);
        this.rotZ.setVisible(bone != null);

        this.removeBone.setEnabled(bone != null);
    }

    private final void onBonePicked() {
        refresh();
    }

    public final ModelSkeleton getModelSkeleton() {
        return (this.getSelectedModel().getSkeleton());
    }

    private final Bone getSelectedBone() {
        return ((Bone) this.bones.getPickedObject());
    }

    @Override
    public String getTitle() {
        return ("Skeleton");
    }
}
