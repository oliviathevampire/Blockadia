package net.thegaminghuskymc.authlib.sandboxgame;

import net.thegaminghuskymc.authlib.HttpAuthenticationService;

public abstract class HttpMinecraftSessionService extends BaseMinecraftSessionService {
    protected HttpMinecraftSessionService(final HttpAuthenticationService authenticationService) {
        super(authenticationService);
    }

    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return (HttpAuthenticationService) super.getAuthenticationService();
    }
}