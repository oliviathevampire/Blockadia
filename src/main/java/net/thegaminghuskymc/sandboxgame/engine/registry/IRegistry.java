package net.thegaminghuskymc.sandboxgame.engine.registry;

import java.util.Set;
import javax.annotation.Nullable;

public interface IRegistry<K, V> extends Iterable<V>
{
    @Nullable
    V getObject(K name);

    /**
     * Register an object on this registry.
     */
    void putObject(K key, V value);

    /**
     * Gets all the keys recognized by this registry.
     */
    Set<K> getKeys();
}