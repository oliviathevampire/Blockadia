package team.hdt.blockadia.game_engine_old.client.rendering.fontRendering;

import team.hdt.blockadia.game_engine_old.client.guis.GuiMaster;
import team.hdt.blockadia.game_engine_old.util.MyFile;

public enum FontType {

    SEGOE_UI(new MyFile(getFontsLoc(), "segoeUI.png"), new MyFile(getFontsLoc(), "segoeUI.fnt"), new SegoeCalculator()),
    GILL(new MyFile(getFontsLoc(), "gill3.png"), new MyFile(getFontsLoc(), "gill3.fnt"), new GillCalculator());

    private TextLoader loader;
    private FontVariablesCalculator calculator;

    private FontType(MyFile textureAtlas, MyFile fontFile, FontVariablesCalculator calculator) {
        this.loader = new TextLoader(textureAtlas, fontFile);
        this.calculator = calculator;
    }

    private static MyFile getFontsLoc() {
        return new MyFile(GuiMaster.GUIS_LOC, "fonts");
    }

    public FontVariablesCalculator getCalculator() {
        return calculator;
    }

    protected void loadText(Text text) {
        loader.loadTextIntoMemory(text);
    }

    protected int getTextureAtlas() {
        return loader.getFontTextureAtlas();
    }

}
