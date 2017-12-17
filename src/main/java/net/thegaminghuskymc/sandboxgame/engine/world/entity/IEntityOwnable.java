package net.thegaminghuskymc.sandboxgame.engine.world.entity;

import java.util.UUID;
import javax.annotation.Nullable;

public interface IEntityOwnable
{
    @Nullable
    UUID getOwnerId();

    @Nullable
    Entity getOwner();
}