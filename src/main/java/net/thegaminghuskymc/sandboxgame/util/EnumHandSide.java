package net.thegaminghuskymc.sandboxgame.util;

import net.thegaminghuskymc.sandboxgame.util.text.ITextComponent;
import net.thegaminghuskymc.sandboxgame.util.text.TextComponentTranslation;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

public enum EnumHandSide
{
    LEFT(new TextComponentTranslation("options.mainHand.left", new Object[0])),
    RIGHT(new TextComponentTranslation("options.mainHand.right", new Object[0]));

    private final ITextComponent handName;

    private EnumHandSide(ITextComponent nameIn)
    {
        this.handName = nameIn;
    }

    @SideOnly(Side.CLIENT)
    public EnumHandSide opposite()
    {
        return this == LEFT ? RIGHT : LEFT;
    }

    public String toString()
    {
        return this.handName.getUnformattedText();
    }
}