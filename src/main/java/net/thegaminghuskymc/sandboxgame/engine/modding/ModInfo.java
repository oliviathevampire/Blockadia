package net.thegaminghuskymc.sandboxgame.engine.modding;

import net.thegaminghuskymc.sandboxgame.engine.GameEngine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModInfo {

    String modid();

    String name() default "";

    String creator() default "Unknown";

    String version() default "";

    String acceptedGameVersions() default "";

    /**
     * the classpath of the client proxy.
     */
    String clientProxy() default "";

    /**
     * the classpath of the server proxy
     */
    String serverProxy() default "";

    String dependencies() default "";

    boolean useMetadata() default false;

    boolean clientSideOnly() default false;

    boolean serverSideOnly() default false;

    String acceptedMinecraftVersions() default "";

    String acceptableRemoteVersions() default "";

    String acceptableSaveVersions() default "";

    String certificateFingerprint() default "";

    String modLanguage() default "java";

    String modLanguageAdapter() default "";

    boolean canBeDeactivated() default false;

    String guiFactory() default "";

    String updateJSON() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface CustomProperty
    {
        /**
         * A key. Should be unique.
         * @return A key
         */
        String k();
        /**
         * A value. Can be anything.
         * @return A value
         */
        String v();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface EventHandler{}

    /**
     * Populate the annotated field with the mod instance based on the specified ModId. This can be used
     * to retrieve instances of other mods.
     * @author cpw
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Instance {
        /**
         * The mod object to inject into this field
         */
        String value() default "";

        /**
         * Optional owner modid, required if this annotation is on something that is not inside the main class of a mod container.
         * This is required to prevent mods from classloading other, potentially disabled mods.
         */
        String owner() default "";
    }
    /**
     * Populate the annotated field with the mod's metadata.
     * @author cpw
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Metadata {
        /**
         * The mod id specifying the metadata to load here
         */
        String value() default "";

        /**
         * Optional owner modid, required if this annotation is on something that is not inside the main class of a mod container.
         * This is required to prevent mods from classloading other, potentially disabled mods.
         */
        String owner() default "";
    }

    /**
     * Mod instance factory method. Should return an instance of the mod. Applies only to static methods on the same class as {@link Mod}.
     * @author cpw
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface InstanceFactory {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface EventBusSubscriber {
        GameEngine.Side[] value() default { GameEngine.Side.CLIENT, GameEngine.Side.SERVER };

        /**
         * Optional value, only nessasary if tis annotation is not on the same class that has a @Mod annotation.
         * Needed to prevent early classloading of classes not owned by your mod.
         * @return
         */
        String modid() default "";
    }

}
