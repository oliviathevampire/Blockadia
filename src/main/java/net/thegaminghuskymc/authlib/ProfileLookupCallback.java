package net.thegaminghuskymc.authlib;

public interface ProfileLookupCallback {
    void onProfileLookupSucceeded(GameProfile profile);

    void onProfileLookupFailed(GameProfile profile, Exception exception);
}