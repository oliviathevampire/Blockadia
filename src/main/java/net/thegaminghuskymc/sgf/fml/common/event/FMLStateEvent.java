package net.thegaminghuskymc.sgf.fml.common.event;

import net.thegaminghuskymc.sandboxgame.GameEngine;
import net.thegaminghuskymc.sgf.fml.common.LoaderState;

public abstract class FMLStateEvent extends FMLEvent
{
    public FMLStateEvent(Object... data)
    {

    }

    /**
     * The current state of the mod
     * @return The current state of the mod
     */
    public abstract LoaderState.ModState getModState();

    /**
     * The side we're loading on. {@link net.thegaminghuskymc.sandboxgame.GameEngine.Side#CLIENT} means we're loading in the client, {@link net.thegaminghuskymc.sandboxgame.GameEngine.Side#SERVER} means
     * we're loading in the dedicated server.
     * @return Return which side we're loading on.
     */
    public GameEngine.Side getSide()
    {
        return FMLCommonHandler.instance().getSide();
    }
}