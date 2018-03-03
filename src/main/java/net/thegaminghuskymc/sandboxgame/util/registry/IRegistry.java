package net.thegaminghuskymc.sandboxgame.registries;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Set;

public interface IRegistry<K, V> extends Iterable<V> {
    @Nullable
    @SideOnly(Side.CLIENT)
    V getObject(K name);

    /**
     * Register an object on this registry.
     */
    void putObject(K key, V value);

    /**
     * Gets all the keys recognized by this registry.
     */
    @SideOnly(Side.CLIENT)
    Set<K> getKeys();
}