package net.thegaminghuskymc.authlib;

public abstract class HttpUserAuthentication extends BaseUserAuthentication {
    protected HttpUserAuthentication(final HttpAuthenticationService authenticationService) {
        super(authenticationService);
    }

    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return (HttpAuthenticationService) super.getAuthenticationService();
    }
}