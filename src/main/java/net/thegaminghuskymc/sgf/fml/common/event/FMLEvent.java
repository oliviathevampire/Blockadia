package net.thegaminghuskymc.sgf.fml.common.event;

import net.thegaminghuskymc.sgf.fml.common.ModContainer;

public class FMLEvent
{
    public final String getEventType()
    {
        return getClass().getSimpleName();
    }
    public final String description()
    {
       String cn = getClass().getName();
       return cn.substring(cn.lastIndexOf('.')+4,cn.length()-5);
    }
    public void applyModContainer(ModContainer activeContainer) {
        // NO OP
    }
}