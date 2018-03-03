package net.thegaminghuskymc.authlib.sandboxgame;

import net.thegaminghuskymc.authlib.AuthenticationService;

public abstract class BaseMinecraftSessionService implements MinecraftSessionService {
    private final AuthenticationService authenticationService;

    protected BaseMinecraftSessionService(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
}