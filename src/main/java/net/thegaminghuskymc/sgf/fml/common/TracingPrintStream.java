package net.thegaminghuskymc.sgf.fml.common;

import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class TracingPrintStream extends PrintStream {

    private Logger logger;
    private int BASE_DEPTH = 3;

    public TracingPrintStream(Logger logger, PrintStream original) {
        super(original);
        this.logger = logger;
    }

    @Override
    public void println(Object o) {
        logger.info("{}{}", getPrefix(), o);
    }

    @Override
    public void println(String s) {
        logger.info("{}{}", getPrefix(), s);
    }

    private String getPrefix() {
        StackTraceElement[] elems = Thread.currentThread().getStackTrace();
        StackTraceElement elem = elems[BASE_DEPTH]; // The caller is always at BASE_DEPTH, including this call.
        if (elem.getClassName().startsWith("kotlin.io.")) {
            elem = elems[BASE_DEPTH + 2]; // Kotlins IoPackage masks origins 2 deeper in the stack.
        } else if (elem.getClassName().startsWith("java.lang.Throwable")) {
            elem = elems[BASE_DEPTH + 4];
        }
        return "[" + elem.getClassName() + ":" + elem.getMethodName() + ":" + elem.getLineNumber() + "]: ";
    }

}