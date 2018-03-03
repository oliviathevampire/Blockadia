package net.thegaminghuskymc.sgf.fml.common.eventhandler;

import net.thegaminghuskymc.sgf.fml.common.ModContainer;

public interface IContextSetter
{
    default void setModContainer(ModContainer mod){};
}