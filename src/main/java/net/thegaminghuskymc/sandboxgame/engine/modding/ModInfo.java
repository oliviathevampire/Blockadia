package net.thegaminghuskymc.sandboxgame.engine.modding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModInfo {

    String name() default "";

    String author() default "Unknown";

    String modid();

    String version() default "0.0";

    String acceptedGameVersions() default "";

    /**
     * the classpath of the client proxy.
     */
    String clientProxy() default "";

    /**
     * the classpath of the server proxy
     */
    String serverProxy() default "";

}
