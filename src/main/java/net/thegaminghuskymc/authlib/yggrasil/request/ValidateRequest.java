package net.thegaminghuskymc.authlib.yggrasil.request;

import net.thegaminghuskymc.authlib.yggrasil.YggdrasilUserAuthentication;

public class ValidateRequest {
    private String clientToken;
    private String accessToken;

    public ValidateRequest(final YggdrasilUserAuthentication authenticationService) {
        this.clientToken = authenticationService.getAuthenticationService().getClientToken();
        this.accessToken = authenticationService.getAuthenticatedToken();
    }
}