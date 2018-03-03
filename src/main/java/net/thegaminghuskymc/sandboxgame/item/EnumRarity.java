package net.thegaminghuskymc.sandboxgame.item;

import net.thegaminghuskymc.sandboxgame.util.text.TextFormatting;

public enum EnumRarity
{
    COMMON(TextFormatting.WHITE, "Common"),
    UNCOMMON(TextFormatting.YELLOW, "Uncommon"),
    RARE(TextFormatting.AQUA, "Rare"),
    EPIC(TextFormatting.LIGHT_PURPLE, "Epic");

    /** The color assigned to this rarity type. */
    public final TextFormatting rarityColor;
    /** Rarity name. */
    public final String rarityName;

    private EnumRarity(TextFormatting color, String name)
    {
        this.rarityColor = color;
        this.rarityName = name;
    }
}