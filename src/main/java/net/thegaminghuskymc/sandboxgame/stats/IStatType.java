package net.thegaminghuskymc.sandboxgame.stats;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

public interface IStatType
{
    /**
     * Formats a given stat for human consumption.
     */
    @SideOnly(Side.CLIENT)
    String format(int number);
}