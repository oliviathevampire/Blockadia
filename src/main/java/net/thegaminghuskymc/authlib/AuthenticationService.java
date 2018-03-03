package net.thegaminghuskymc.authlib;

import net.thegaminghuskymc.authlib.sandboxgame.MinecraftSessionService;

public interface AuthenticationService {
    /**
     * <p />
     * Certain Authentication Services may have restrictions as to which {@link Agent}s are supported.
     * Please consult their javadoc for more information.
     *
     * @param agent Game agent to authenticate for
     * @throws java.lang.IllegalArgumentException Agent is null or not allowed for this AuthenticationService
     * @return New user authenticator
     */
    UserAuthentication createUserAuthentication(Agent agent);

    /**
     * </p>
     * This is a Minecraft specific service and is not relevant to any other game agent.
     *
     * @return New minecraft session service
     */
    MinecraftSessionService createMinecraftSessionService();

    /**
     *
     * @return New profile repository
     */
    GameProfileRepository createProfileRepository();
}