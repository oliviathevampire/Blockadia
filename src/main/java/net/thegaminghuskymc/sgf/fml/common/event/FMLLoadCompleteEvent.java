package net.thegaminghuskymc.sgf.fml.common.event;

import net.thegaminghuskymc.sgf.fml.common.LoaderState;

public class FMLLoadCompleteEvent extends FMLStateEvent
{

    public FMLLoadCompleteEvent(Object... data)
    {
        super(data);
    }
    
    @Override
    public LoaderState.ModState getModState()
    {
        return LoaderState.ModState.AVAILABLE;
    }

}