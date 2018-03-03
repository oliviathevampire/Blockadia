package net.thegaminghuskymc.authlib.yggrasil.request;

import net.thegaminghuskymc.authlib.yggrasil.YggdrasilUserAuthentication;

public class InvalidateRequest {
    private String accessToken;
    private String clientToken;

    public InvalidateRequest(final YggdrasilUserAuthentication authenticationService) {
        this.accessToken = authenticationService.getAuthenticatedToken();
        this.clientToken = authenticationService.getAuthenticationService().getClientToken();
    }
}