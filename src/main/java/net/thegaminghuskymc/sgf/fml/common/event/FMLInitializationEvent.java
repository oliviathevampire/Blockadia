package net.thegaminghuskymc.sgf.fml.common.event;

import net.thegaminghuskymc.sgf.fml.common.LoaderState;

public class FMLInitializationEvent extends FMLStateEvent
{

    public FMLInitializationEvent(Object... data)
    {
        super(data);
    }
    
    @Override
    public LoaderState.ModState getModState()
    {
        return LoaderState.ModState.INITIALIZED;
    }

}