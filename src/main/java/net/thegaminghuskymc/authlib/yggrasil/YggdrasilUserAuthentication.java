package net.thegaminghuskymc.authlib.yggrasil;


import net.thegaminghuskymc.authlib.Agent;
import net.thegaminghuskymc.authlib.GameProfile;
import net.thegaminghuskymc.authlib.HttpAuthenticationService;
import net.thegaminghuskymc.authlib.HttpUserAuthentication;
import net.thegaminghuskymc.authlib.UserType;
import net.thegaminghuskymc.authlib.exceptions.AuthenticationException;
import net.thegaminghuskymc.authlib.exceptions.InvalidCredentialsException;
import net.thegaminghuskymc.authlib.yggrasil.request.AuthenticationRequest;
import net.thegaminghuskymc.authlib.yggrasil.request.RefreshRequest;
import net.thegaminghuskymc.authlib.yggrasil.request.ValidateRequest;
import net.thegaminghuskymc.authlib.yggrasil.response.AuthenticationResponse;
import net.thegaminghuskymc.authlib.yggrasil.response.RefreshResponse;
import net.thegaminghuskymc.authlib.yggrasil.response.Response;
import net.thegaminghuskymc.authlib.yggrasil.response.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;

public class YggdrasilUserAuthentication extends HttpUserAuthentication {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String BASE_URL = "https://authserver.mojang.com/";
    private static final URL ROUTE_AUTHENTICATE = HttpAuthenticationService.constantURL(BASE_URL + "authenticate");
    private static final URL ROUTE_REFRESH = HttpAuthenticationService.constantURL(BASE_URL + "refresh");
    private static final URL ROUTE_VALIDATE = HttpAuthenticationService.constantURL(BASE_URL + "validate");
    private static final URL ROUTE_INVALIDATE = HttpAuthenticationService.constantURL(BASE_URL + "invalidate");
    private static final URL ROUTE_SIGNOUT = HttpAuthenticationService.constantURL(BASE_URL + "signout");

    private static final String STORAGE_KEY_ACCESS_TOKEN = "accessToken";

    private final Agent agent;
    private GameProfile[] profiles;
    private String accessToken;
    private boolean isOnline;

    public YggdrasilUserAuthentication(final YggdrasilAuthenticationService authenticationService, final Agent agent) {
        super(authenticationService);
        this.agent = agent;
    }

    @Override
    public boolean canLogIn() {
        return !canPlayOnline() && StringUtils.isNotBlank(getUsername()) && (StringUtils.isNotBlank(getPassword()) || StringUtils.isNotBlank(getAuthenticatedToken()));
    }

    @Override
    public void logIn() throws AuthenticationException {
        if (StringUtils.isBlank(getUsername())) {
            throw new InvalidCredentialsException("Invalid username");
        }

        if (StringUtils.isNotBlank(getAuthenticatedToken())) {
            logInWithToken();
        } else if (StringUtils.isNotBlank(getPassword())) {
            logInWithPassword();
        } else {
            throw new InvalidCredentialsException("Invalid password");
        }
    }

    protected void logInWithPassword() throws AuthenticationException {
        if (StringUtils.isBlank(getUsername())) {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isBlank(getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        LOGGER.info("Logging in with username & password");

        final AuthenticationRequest request = new AuthenticationRequest(this, getUsername(), getPassword());
        final AuthenticationResponse response = getAuthenticationService().makeRequest(ROUTE_AUTHENTICATE, request, AuthenticationResponse.class);

        if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }

        if (response.getSelectedProfile() != null) {
            setUserType(response.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        } else if (ArrayUtils.isNotEmpty(response.getAvailableProfiles())) {
            setUserType(response.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }

        final User user = response.getUser();

        if (user != null && user.getId() != null) {
            setUserid(user.getId());
        } else {
            setUserid(getUsername());
        }

        isOnline = true;
        accessToken = response.getAccessToken();
        profiles = response.getAvailableProfiles();
        setSelectedProfile(response.getSelectedProfile());
        getModifiableUserProperties().clear();

        updateUserProperties(user);
    }

    protected void updateUserProperties(final User user) {
        if (user == null) {
            return;
        }

        if (user.getProperties() != null) {
            getModifiableUserProperties().putAll(user.getProperties());
        }
    }

    protected void logInWithToken() throws AuthenticationException {
        if (StringUtils.isBlank(getUserID())) {
            if (StringUtils.isBlank(getUsername())) {
                setUserid(getUsername());
            } else {
                throw new InvalidCredentialsException("Invalid uuid & username");
            }
        }
        if (StringUtils.isBlank(getAuthenticatedToken())) {
            throw new InvalidCredentialsException("Invalid access token");
        }

        LOGGER.info("Logging in with access token");

        if (checkTokenValidity()) {
            LOGGER.debug("Skipping refresh call as we're safely logged in.");
            isOnline = true;
            return;
        }

        final RefreshRequest request = new RefreshRequest(this);
        final RefreshResponse response = getAuthenticationService().makeRequest(ROUTE_REFRESH, request, RefreshResponse.class);

        if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }

        if (response.getSelectedProfile() != null) {
            setUserType(response.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        } else if (ArrayUtils.isNotEmpty(response.getAvailableProfiles())) {
            setUserType(response.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }

        if (response.getUser() != null && response.getUser().getId() != null) {
            setUserid(response.getUser().getId());
        } else {
            setUserid(getUsername());
        }

        isOnline = true;
        accessToken = response.getAccessToken();
        profiles = response.getAvailableProfiles();
        setSelectedProfile(response.getSelectedProfile());
        getModifiableUserProperties().clear();

        updateUserProperties(response.getUser());
    }

    protected boolean checkTokenValidity() throws AuthenticationException {
        final ValidateRequest request = new ValidateRequest(this);
        try {
            getAuthenticationService().makeRequest(ROUTE_VALIDATE, request, Response.class);
            return true;
        } catch (final AuthenticationException ignored) {
            return false;
        }
    }

    @Override
    public void logOut() {
        super.logOut();

        accessToken = null;
        profiles = null;
        isOnline = false;
    }

    @Override
    public GameProfile[] getAvailableProfiles() {
        return profiles;
    }

    @Override
    public boolean isLoggedIn() {
        return StringUtils.isNotBlank(accessToken);
    }

    @Override
    public boolean canPlayOnline() {
        return isLoggedIn() && getSelectedProfile() != null && isOnline;
    }

    @Override
    public void selectGameProfile(final GameProfile profile) throws AuthenticationException {
        if (!isLoggedIn()) {
            throw new AuthenticationException("Cannot change game profile whilst not logged in");
        }
        if (getSelectedProfile() != null) {
            throw new AuthenticationException("Cannot change game profile. You must log out and back in.");
        }
        if (profile == null || !ArrayUtils.contains(profiles, profile)) {
            throw new IllegalArgumentException("Invalid profile '" + profile + "'");
        }

        final RefreshRequest request = new RefreshRequest(this, profile);
        final RefreshResponse response = getAuthenticationService().makeRequest(ROUTE_REFRESH, request, RefreshResponse.class);

        if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }

        isOnline = true;
        accessToken = response.getAccessToken();
        setSelectedProfile(response.getSelectedProfile());
    }

    @Override
    public void loadFromStorage(final Map<String, Object> credentials) {
        super.loadFromStorage(credentials);

        accessToken = String.valueOf(credentials.get(STORAGE_KEY_ACCESS_TOKEN));
    }

    @Override
    public Map<String, Object> saveForStorage() {
        final Map<String, Object> result = super.saveForStorage();

        if (StringUtils.isNotBlank(getAuthenticatedToken())) {
            result.put(STORAGE_KEY_ACCESS_TOKEN, getAuthenticatedToken());
        }

        return result;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public String getSessionToken() {
        if (isLoggedIn() && getSelectedProfile() != null && canPlayOnline()) {
            return String.format("token:%s:%s", getAuthenticatedToken(), getSelectedProfile().getId());
        } else {
            return null;
        }
    }

    @Override
    public String getAuthenticatedToken() {
        return accessToken;
    }

    public Agent getAgent() {
        return agent;
    }

    @Override
    public String toString() {
        return "YggdrasilAuthenticationService{" +
            "agent=" + agent +
            ", profiles=" + Arrays.toString(profiles) +
            ", selectedProfile=" + getSelectedProfile() +
            ", username='" + getUsername() + '\'' +
            ", isLoggedIn=" + isLoggedIn() +
            ", userType=" + getUserType() +
            ", canPlayOnline=" + canPlayOnline() +
            ", accessToken='" + accessToken + '\'' +
            ", clientToken='" + getAuthenticationService().getClientToken() + '\'' +
            '}';
    }

    @Override
    public YggdrasilAuthenticationService getAuthenticationService() {
        return (YggdrasilAuthenticationService) super.getAuthenticationService();
    }
}