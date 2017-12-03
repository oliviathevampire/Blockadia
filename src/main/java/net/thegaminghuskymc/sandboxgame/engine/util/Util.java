package net.thegaminghuskymc.sandboxgame.engine.util;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Util {

    public static Util.EnumOS getOSType()
    {
        String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);

        if (s.contains("win"))
        {
            return Util.EnumOS.WINDOWS;
        }
        else if (s.contains("mac"))
        {
            return Util.EnumOS.OSX;
        }
        else if (s.contains("solaris"))
        {
            return Util.EnumOS.SOLARIS;
        }
        else if (s.contains("sunos"))
        {
            return Util.EnumOS.SOLARIS;
        }
        else if (s.contains("linux"))
        {
            return Util.EnumOS.LINUX;
        }
        else
        {
            return s.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN;
        }
    }

    /**
     * Run a task and return the result, catching any execution exceptions and logging them to the specified logger
     */
    @Nullable
    public static <V> V runTask(FutureTask<V> task, Logger logger)
    {
        try
        {
            task.run();
            return task.get();
        }
        catch (ExecutionException | InterruptedException executionexception)
        {
            logger.fatal("Error executing task", executionexception);
        }

        return null;
    }

    public static <T> T getLastElement(List<T> list)
    {
        return list.get(list.size() - 1);
    }

    public enum EnumOS
    {
        LINUX,
        SOLARIS,
        WINDOWS,
        OSX,
        UNKNOWN
    }
}