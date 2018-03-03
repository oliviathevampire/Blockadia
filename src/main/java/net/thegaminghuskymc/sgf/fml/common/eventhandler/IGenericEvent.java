package net.thegaminghuskymc.sgf.fml.common.eventhandler;

import java.lang.reflect.Type;

public interface IGenericEvent<T>
{
    Type getGenericType();
}