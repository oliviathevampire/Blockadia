package net.thegaminghuskymc.authlib.sandboxgame;

import net.thegaminghuskymc.authlib.exceptions.AuthenticationException;
import net.thegaminghuskymc.authlib.GameProfile;
import net.thegaminghuskymc.authlib.exceptions.AuthenticationUnavailableException;

import java.net.InetAddress;
import java.util.Map;

public interface MinecraftSessionService {
    /**
     * Attempts to join the specified Minecraft server.
     * <p />
     * authentication service. If this method returns without throwing an exception, the join was successful and a subsequent call to
     * {@link #hasJoinedServer(GameProfile, String, InetAddress)} will return true.
     *
     * @param serverId The random ID of the server to join
     */
    void joinServer(GameProfile profile, String authenticationToken, String serverId) throws AuthenticationException;

    /**
     * Checks if the specified user has joined a Minecraft server.
     * <p />
     * authentication service.
     *
     * @param user Partial {@link GameProfile} to check for
     * @param serverId The random ID of the server to check for
     * @param address The address connected from
     * @return Full game profile if the user had joined, otherwise null
     */
    GameProfile hasJoinedServer(GameProfile user, String serverId, InetAddress address) throws AuthenticationUnavailableException;

    /**
     * <p />
     * If a profile contains invalid textures, they will not be returned. If a profile contains no textures, an empty map will be returned.
     *
     * @param profile Game profile to return textures from.
     * @param requireSecure If true, requires the payload to be recent and securely fetched.
     * @return Map of texture types to textures.
     */
    Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile profile, boolean requireSecure);

    /**
     * Fills a profile with all known properties from the session service.
     * <p />
     * The profile must have an ID. If no information is found, nothing will be done.
     *
     * @param profile Game profile to fill with properties.
     * @param requireSecure If you require verifiable correct data.
     * @return Filled profile for the previous user.
     */
    GameProfile fillProfileProperties(GameProfile profile, boolean requireSecure);
}