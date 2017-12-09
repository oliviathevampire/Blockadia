package net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.components;

public class GuiViewMainMenu extends GuiView {

    private GuiButton button, button2, button3, button4, button5;

    public GuiViewMainMenu() {
        super();
        this.setHoverable(true);

        this.button = new GuiButton(1F);
        this.button.setBox(0.3f, 0.75f, 0.35f, 0.07f, 0f);
        this.button.setDefaultColors();
        this.button.setFontSize(1F, 0.5F);
        this.button.addText("Singleplayer");
        this.button.setHoverable(true);
        this.addChild(this.button);

        this.button2 = new GuiButton(1F);
        this.button2.setBox(0.3f, button.getBoxY() - 0.10f, 0.35f, 0.07f, 0f);
        this.button2.setDefaultColors();
        this.button2.setFontSize(1F, 0.5F);
        this.button2.addText("Multiplayer");
        this.button2.setHoverable(true);
        this.addChild(this.button2);

        this.button3 = new GuiButton(1F);
        this.button3.setBox(0.3f, button2.getBoxY() - 0.10f, 0.35f, 0.07f, 0f);
        this.button3.setDefaultColors();
        this.button3.setFontSize(1F, 0.5F);
        this.button3.addText("Realms");
        this.button3.setHoverable(true);
        this.addChild(this.button3);

        this.button4 = new GuiButton(1F);
        this.button4.setBox(0.3f, button3.getBoxY() - 0.10f, 0.164f, 0.07f, 0f);
        this.button4.setDefaultColors();
        this.button4.setFontSize(1F, 0.5F);
        this.button4.addText("Options");
        this.button4.setHoverable(true);
        this.addChild(this.button4);

        this.button5 = new GuiButton(1F);
        this.button5.setBox(button4.getBoxX() + 0.02f + 0.164f, button4.getBoxY(), 0.164f, 0.07f, 0f);
        this.button5.setDefaultColors();
        this.button5.setFontSize(1F, 0.5F);
        this.button5.addText("Quit Game");
        this.button5.setHoverable(true);
        this.addChild(this.button5);

    }
}
