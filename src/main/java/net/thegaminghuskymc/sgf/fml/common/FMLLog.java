package net.thegaminghuskymc.sgf.fml.common;

import net.thegaminghuskymc.sandboxgame.GameEngine;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.Locale;

public class FMLLog
{

    public static final Logger log;

    static {
        log = LogManager.getLogger("FML");
        // Default side to client for test harness purposes
        GameEngine.Side side = FMLLaunchHandler.side();
        if (side == null) side = GameEngine.Side.CLIENT;
        ThreadContext.put("side", side.name().toLowerCase(Locale.ENGLISH));

        log.debug("Injecting tracing printstreams for STDOUT/STDERR.");
        System.setOut(new TracingPrintStream(LogManager.getLogger("STDOUT"), System.out));
        System.setErr(new TracingPrintStream(LogManager.getLogger("STDERR"), System.err));
    }

    public static void bigWarning(String format, Object... data)
    {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        log.warn("****************************************");
        log.warn("* "+format, data);
        for (int i = 2; i < 8 && i < trace.length; i++)
        {
            log.warn("*  at {}{}", trace[i].toString(), i == 7 ? "..." : "");
        }
        log.warn("****************************************");
    }

    @Deprecated
    public static void log(String targetLog, Level level, String format, Object... data)
    {
        FMLRelaunchLog.log(targetLog, level, format, data);
    }

    @Deprecated
    public static void log(Level level, String format, Object... data)
    {
        FMLRelaunchLog.log(level, format, data);
    }

    @Deprecated
    public static void log(String targetLog, Level level, Throwable ex, String format, Object... data)
    {
        FMLRelaunchLog.log(targetLog, level, ex, format, data);
    }

    @Deprecated
    public static void log(Level level, Throwable ex, String format, Object... data)
    {
        FMLRelaunchLog.log(level, ex, format, data);
    }

    @Deprecated
    public static void severe(String format, Object... data)
    {
        log(Level.ERROR, format, data);
    }

    @Deprecated
    public static void warning(String format, Object... data)
    {
        log(Level.WARN, format, data);
    }

    @Deprecated
    public static void info(String format, Object... data)
    {
        log(Level.INFO, format, data);
    }

    @Deprecated
    public static void fine(String format, Object... data)
    {
        log(Level.DEBUG, format, data);
    }

    @Deprecated
    public static void finer(String format, Object... data)
    {
        log(Level.TRACE, format, data);
    }

    @Deprecated
    public static Logger getLogger()
    {
        return log;
    }
}