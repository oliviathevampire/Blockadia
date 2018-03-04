package net.thegaminghuskymc.sandboxgame.game.client.tests;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.game.client.GameEngineClient;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.GuiRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.*;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventMouseEnter;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventMouseExit;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiListener;

public class TestGui {

    public static void main(String[] args) {

        GameEngineClient engine = new GameEngineClient();
        engine.initialize();
        engine.load();

        GuiView guiView = new GuiView() {

            @Override
            protected void onInitialized(GuiRenderer renderer) {
//				 this.addGuiTexture();
//				 this.addGuiLabel();
                this.addGuiTextPrompt();
//				 this.addGuiSliderBar();
            }

            private void addGuiTexture() {
                GuiTexture t = new GuiTexture();
                float ux = 144.0f / 256.0f;
                float uy = 16.0f / 256.0f;
                t.setBox(0f, 0f, 1f, 1f, 0F);
                t.setTexture(GLH.glhGenTexture(R.getResPath("textures/gui/logo.png")));
                t.addListener(new GuiListener<GuiEventMouseEnter<GuiTexture>>() {
                    @Override
                    public void invoke(GuiEventMouseEnter<GuiTexture> event) {
                        Logger.get().log(Logger.Level.DEBUG, "in");
                    }
                });

                t.addListener(new GuiListener<GuiEventMouseExit<GuiTexture>>() {
                    @Override
                    public void invoke(GuiEventMouseExit<GuiTexture> event) {
                        Logger.get().log(Logger.Level.DEBUG, "out");
                    }
                });
                this.addChild(t);
            }

            private void addGuiTextPrompt() {
                GuiPrompt lbl = new GuiPrompt();
                lbl.setFontColor(1, 1, 1, 1.0f);
                lbl.setBox(0, 0.5f, 0.5f, 0.5f, 0);
                lbl.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
                lbl.addTextParameter(new GuiTextParameterTextCenterBox());

                this.addChild(lbl);
            }

            private void addGuiLabel() {
                GuiLabel lbl = new GuiLabel();
                lbl.setFontColor(1, 1, 1, 1.0f);
                lbl.setText("Husky's Sandbox Game");
                lbl.setBox(0, 0, 0.5f, 0.5f, 0);
                lbl.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
                lbl.addTextParameter(new GuiTextParameterTextCenterBox());

                this.addChild(lbl);
            }
        };
        // guiView.set(0.5f, 0.5f, 0.5f, 0.5f, 0.0f);
        guiView.setBox(0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
        engine.getRenderer().getGuiRenderer().addGui(guiView);

        engine.loop();
        engine.deinitialize();
    }
}
