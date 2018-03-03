package net.thegaminghuskymc.authlib.yggrasil.response;

import net.thegaminghuskymc.authlib.sandboxgame.MinecraftProfileTexture;

import java.util.Map;
import java.util.UUID;

public class MinecraftTexturesPayload {
    private long timestamp;
    private UUID profileId;
    private String profileName;
    private boolean isPublic;
    private Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textures;

    public long getTimestamp() {
        return timestamp;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures() {
        return textures;
    }
}