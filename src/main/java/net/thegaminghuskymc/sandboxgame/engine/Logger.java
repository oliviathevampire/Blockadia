package net.thegaminghuskymc.sandboxgame.engine;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private static final DateFormat _date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final Calendar _calendar = Calendar.getInstance();

    private static Logger _instance = new Logger(System.out);

    private PrintStream _print_stream;
    private int _indentation;

    public Logger(PrintStream stream) {
        this._print_stream = stream;
    }

    public static Logger get() {
        return (_instance);
    }

    public PrintStream getPrintStream() {
        return (this._print_stream);
    }

    public void log(Level level, String message) {
        log(level, 3, message);
    }

    public void log(Level level, int stackDepth, String message) {
        Thread thrd = Thread.currentThread();
        StackTraceElement[] trace = thrd.getStackTrace();
        StringBuilder stack = new StringBuilder();
        int offset = 2;
        int end = trace.length < stackDepth + offset ? trace.length : stackDepth + offset;
        for (int i = offset; i < end; i++) {
            if (trace[i].getFileName() == null) {
                continue;
            }
            stack.append(" [" + trace[i].getFileName() + ":" + trace[i].getLineNumber() + "]");
        }
        this._print_stream.printf("%s[%s] [%s] [Thread: %s(%d)]" + stack.toString() + "%s %s\n", level.getColor(),
                _date_format.format(_calendar.getTime()), level.getLabel(), thrd.getName(), thrd.getId(), ANSI_RESET,
                message);
    }

    public void log(Level level, Object... objs) {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (Object obj : objs) {
            builder.append(obj);
            if (i != objs.length - 1) {
                builder.append(" : ");
            }
            ++i;
        }
        log(level, builder.toString());
    }

    public void indent(int i) {
        this._indentation += i;
        if (this._indentation < 0) {
            this._indentation = 0;
        }
    }

    public enum Level {
        FINE(ANSI_GREEN, "fine"), WARNING(ANSI_YELLOW, "warning"), ERROR(ANSI_RED, "error"), DEBUG(ANSI_CYAN, "debug");

        String _color;
        String _label;

        Level(String color, String label) {
            this._color = color;
            this._label = label.toUpperCase();
        }

        String getLabel() {
            return (this._label);
        }

        String getColor() {
            return (this._color);
        }
    }
}
