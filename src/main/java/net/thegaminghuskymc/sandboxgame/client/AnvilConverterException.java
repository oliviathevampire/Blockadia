package net.thegaminghuskymc.sandboxgame.client;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AnvilConverterException extends Exception {
    public AnvilConverterException(String exceptionMessage) {
        super(exceptionMessage);
    }
}