package net.thegaminghuskymc.sgf.fml.common.event;

import net.thegaminghuskymc.sgf.fml.common.LoaderState;

public class FMLAddResourcesEvent extends FMLStateEvent
{
    public FMLAddResourcesEvent(Object... data)
    {
        super(data);
    }

    @Override
    public LoaderState.ModState getModState()
    {
        return LoaderState.ModState.ADDRESOURCES;
    }

}