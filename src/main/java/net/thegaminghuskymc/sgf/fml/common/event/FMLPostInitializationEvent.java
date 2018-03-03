package net.thegaminghuskymc.sgf.fml.common.event;

import net.thegaminghuskymc.sgf.fml.common.FMLLog;
import net.thegaminghuskymc.sgf.fml.common.Loader;
import net.thegaminghuskymc.sgf.fml.common.LoaderState;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class FMLPostInitializationEvent extends FMLStateEvent
{
    public FMLPostInitializationEvent(Object... data)
    {
        super(data);
    }

    @Override
    public LoaderState.ModState getModState()
    {
        return LoaderState.ModState.POSTINITIALIZED;
    }

    /**
     * Build an object depending on if a specific target mod is loaded or not.
     *
     * Usually would be used to access an object from the other mod.
     *
     * @param modId The modId I conditionally want to build an object for
     * @param className The name of the class I wish to instantiate
     * @return An optional containing the object if possible, or null if not
     */
    public Optional<?> buildSoftDependProxy(String modId, String className, Object... arguments)
    {
        if (Loader.isModLoaded(modId))
        {
            Class<?>[] args = Arrays.stream(arguments).filter(Objects::nonNull).map(Object::getClass).toArray(Class<?>[]::new);
            try
            {
                Class<?> clz = Class.forName(className,true,Loader.instance().getModClassLoader());
                Constructor<?> ct = clz.getConstructor(args);
                return Optional.of(ct.newInstance(arguments));
            }
            catch (Exception e)
            {
                FMLLog.log.info("An error occurred trying to build a soft depend proxy", e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}