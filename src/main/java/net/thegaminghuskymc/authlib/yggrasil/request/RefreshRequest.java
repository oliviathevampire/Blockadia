package net.thegaminghuskymc.authlib.yggrasil.request;

import net.thegaminghuskymc.authlib.GameProfile;
import net.thegaminghuskymc.authlib.yggrasil.YggdrasilUserAuthentication;

public class RefreshRequest {
    private String clientToken;
    private String accessToken;
    private GameProfile selectedProfile;
    private boolean requestUser = true;

    public RefreshRequest(final YggdrasilUserAuthentication authenticationService) {
        this(authenticationService, null);
    }

    public RefreshRequest(final YggdrasilUserAuthentication authenticationService, final GameProfile profile) {
        this.clientToken = authenticationService.getAuthenticationService().getClientToken();
        this.accessToken = authenticationService.getAuthenticatedToken();
        this.selectedProfile = profile;
    }
}