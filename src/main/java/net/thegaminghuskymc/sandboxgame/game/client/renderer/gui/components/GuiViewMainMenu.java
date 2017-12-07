package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;

import net.thegaminghuskymc.sandboxgame.game.client.opengl.object.GLTexture;

public class GuiViewMainMenu extends GuiView {

    private GuiButton button, button2, button3, button4, button5;

    private GuiTexture logo;

    public GuiViewMainMenu() {
        super();

        this.logo = new GuiTexture();
        this.logo.setTexture(new GLTexture());

        this.button = new GuiButton(1F);
        this.button.setBox(0.3f, 0.6f, 0.35f, 0.07f, 0f);
        this.button.setDefaultColors();
        this.button.setFontSize(1F, 0.5F);
        this.button.addText("Singleplayer");
        this.addChild(this.button);

        /*this.button2 = new GuiButton(1F);
        this.button.setBox(15, 15, 20, 10, 0);
        this.button2.setDefaultColors();
        this.button2.addText("Miltiplayer");
        this.addChild(this.button2);

        this.button3 = new GuiButton(1F);
        this.button.setBox(27, 27, 20, 10, 0);
        this.button3.setDefaultColors();
        this.button3.addText("Realms");
        this.addChild(this.button3);

        this.button4 = new GuiButton(1F);
        this.button.setBox(10, 39, 20, 10, 0);
        this.button4.setDefaultColors();
        this.button4.addText("Options");
        this.addChild(this.button4);

        this.button5 = new GuiButton(1F);
        this.button.setBox(10, 51, 20, 10, 0);
        this.button5.setDefaultColors();
        this.button5.addText("Quit Game");
        this.addChild(this.button5);*/

    }
}
