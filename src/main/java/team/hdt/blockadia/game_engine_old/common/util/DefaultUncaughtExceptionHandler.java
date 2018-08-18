//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.common.util;

import org.apache.logging.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

public class DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private final Logger LOGGER;

    public DefaultUncaughtExceptionHandler(Logger p_i48772_1_) {
        this.LOGGER = p_i48772_1_;
    }

    public void uncaughtException(Thread p_uncaughtException_1_, Throwable p_uncaughtException_2_) {
        this.LOGGER.error("Caught previously unhandled exception :");
        this.LOGGER.error(p_uncaughtException_2_);
    }
}
