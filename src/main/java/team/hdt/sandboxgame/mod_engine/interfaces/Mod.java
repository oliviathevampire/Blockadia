package team.hdt.sandboxgame.mod_engine.interfaces;

import team.hdt.sandboxgame.mod_engine.side.Side;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface Mod {

    /**
     * The unique mod identifier for this mod.
     * <b>Required to be lowercased in the english locale for compatibility. Will be truncated to 64 characters long.</b>
     *
     * This will be used to identify your mod for third parties (other mods), it will be used to identify your mod for registries such as block and item registries.
     * By default, you will have a resource domain that matches the modid. All these uses require that constraints are imposed on the format of the modid.
     */
    String modid();

    /**
     * A user friendly name for the mod
     */
    String name() default "";

    /**
     * A version string for this mod.
     *
     * The version string here should be just numbers separated by dots,
     * to make specifying {@link #dependencies()} simple for other mods.
     *
     * See also: <a href="https://cwiki.apache.org/confluence/display/MAVENOLD/Versioning">"Versioning" on Maven Wiki</a>
     */
    String version() default "";

    /**
     * A dependency string for this mod, which specifies which mod(s) it depends on in order to run.
     *
     * A dependency string must start with a combination of these prefixes, separated by "-":
     *     [before, after], [required], [client, server]
     *     At least one "before", "after", or "required" must be specified.
     * Then ":" and the mod id.
     * Then a version range should be specified for the mod by adding "@" and the version range.
     *     The version range format is described in the javadoc here:
     * Then a ";".
     *
     * If a "required" mod is missing, or a mod exists with a version outside the specified range,
     * the game will not start and an error screen will tell the player which versions are required.
     *
     * Example:
     *     Our example mod:
     *      * depends on Forge and uses new features that were introduced in Forge version 14.21.1.2395
     *         "required:forge@[14.21.1.2395,);"
     *
     *          1.12.2 Note: for compatibility with Forge older than 14.23.0.2501 the syntax must follow this older format:
     *          "required-after:forge@[14.21.1.2395,);"
     *          For more explanation see https://github.com/MinecraftForge/MinecraftForge/issues/4918
     *
     *      * is a dedicated addon to mod1 and has to have its event handlers run after mod1's are run,
     *         "required-after:mod1;"
     *      * has optional integration with mod2 which depends on features introduced in mod2 version 4.7.0,
     *         "after:mod2@[4.7.0,);"
     *      * depends on a client-side-only rendering library called rendermod
     *         "required-client:rendermod;"
     *
     *     The full dependencies string is all of those combined:
     *         "required:forge@[14.21.1.2395,);required-after:mod1;after:mod2@[4.7.0,);required-client:rendermod;"
     *
     *     This will stop the game and display an error message if any of these is true:
     *         The installed forge is too old,
     *         mod1 is missing,
     *         an old version of mod2 is present,
     *         rendermod is missing on the client.
     */
    String dependencies() default "";

    /**
     * Whether to use the mcmod.info metadata by default for this mod.
     * If true, settings in the mcmod.info file will override settings in these annotations.
     */
    boolean useMetadata() default false;

    /**
     * If true, this mod will not be loaded on the Dedicated Server environment.
     * Will crash if both serverSideOnly and clientSideOnly are set to true.
     */
    boolean clientSideOnly() default false;

    /**
     * If true, this mod will not be loaded on the Client environment.
     * Will crash if both serverSideOnly and clientSideOnly are set to true.
     */
    boolean serverSideOnly() default false;

    /**
     * The acceptable range of minecraft versions that this mod will load and run in
     * The default ("empty string") indicates that the currently RUNNING minecraft version is acceptable.
     * This means ANY version that the end user adds the mod to. Modders PLEASE set this.
     * FML will refuse to run with an error if the minecraft version is not in this range across all mods.
     * @return A version range as specified by the maven version range specification or the empty string
     */
    String acceptedMinecraftVersions() default "";
    /**
     * A replacement for the no-longer-existing "versionRange" of NetworkMod. Specify a remote version range
     * that this mod will accept as valid. Defaults to nothing, which is interpreted as "only this version".
     * Another special value is '*' which means accept all versions.
     *
     * @return A version range as specified by the maven version range specification or the empty string
     */
    String acceptableRemoteVersions() default "";

    /**
     * A version range specifying compatible save version information. If your mod follows good version numbering
     * practice <a href="http://semver.org/">Like this (http://semver.org/)</a> then this should be sufficient.
     *
     * @return A version range as specified by the maven version range specification or the empty string
     */
    String acceptableSaveVersions() default "";
    /**
     * Specifying this field allows for a mod to expect a signed jar with a fingerprint matching this value.
     * The fingerprint should be SHA-1 encoded, lowercase with ':' removed. An empty value indicates that
     * the mod is not expecting to be signed.
     *
     * @return A certificate fingerprint that is expected for this mod.
     */
    String certificateFingerprint() default "";

    /**
     * The language the mod is authored in. This will be used to control certain compatibility behaviours for this mod.
     * Valid values are currently "java", "scala"
     *
     * @return The language the mod is authored in
     */
    String modLanguage() default "java";

    String modLanguageAdapter() default "";

    /**
     * If your mod doesn't have a runtime persistent effect on the state of the game, and can be disabled without side effects
     * (minimap mods, graphical tweak mods) then you can set true here and receive the FMLDeactivationEvent to perform deactivation
     * tasks.
     * This does not affect administrative disabling through the system property fml.modStates or the config file fmlModState.properties.
     * The mod will only be deactivated outside of a running game world - FML will never allow mod deactivation whilst a game server
     * is running.
     *
     * @return if this mod can be deactivated whilst the game is open.
     */
    boolean canBeDeactivated() default false;

    /**
     * An optional GUI factory for this mod. This is the name of a class implementing that will be instantiated
     * on the client side, and will have certain configuration/options gui's requested from it.
     *
     * @return The name of a class implementing
     */
    String guiFactory() default "";

    /**
     * An optional URL to a JSON file that will be checked once per launch to determine if there is an updated
     * version of this mod and notify the end user. For more information see ForgeVersion.
     * Format is defined here: https://gist.github.com/LexManos/7aacb9aa991330523884
     * @return URL to update metadata json
     */
    String updateJSON() default "";

    /**
     * A list of custom properties for this mod. Completely up to the mod author if/when they
     * want to put anything in here.
     * @return an optional list of custom properties
     */
    CustomProperty[] customProperties() default {};

    /**
     * A custom key => value property pair for use with {@link Mod#customProperties()}
     * @author HuskyTheArtist
     *
     */
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
    /**
     * Marks the associated method as handling an FML lifecycle event.
     * The method must have a single parameter, one of the following types. This annotation
     * replaces the multiple different annotations that previously were used.
     *
     * Current event classes. This first section is standard lifecycle events. They are dispatched
     * at various phases as the game starts. Each event should have information useful to that
     * phase of the lifecycle. They are fired in this order.
     *
     * @author HuskyTheArtist
     *
     */
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

    /**
     * A class which will be subscribed to at mod construction time.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface EventBusSubscriber {
        Side[] value() default { Side.CLIENT, Side.SERVER };

        /**
         * Optional value, only nessasary if tis annotation is not on the same class that has a @Mod annotation.
         * Needed to prevent early classloading of classes not owned by your mod.
         * @return
         */
        String modid() default "";
    }

}