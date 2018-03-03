package net.thegaminghuskymc.authlib;

public interface GameProfileRepository {
    void findProfilesByNames(String[] names, Agent agent, ProfileLookupCallback callback);
}