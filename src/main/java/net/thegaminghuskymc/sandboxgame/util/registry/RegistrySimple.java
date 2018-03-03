package net.thegaminghuskymc.sandboxgame.registries;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;

public class RegistrySimple<K, V> implements IRegistry<K, V> {
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Objects registered on this registry.
     */
    protected final Map<K, V> registryObjects = this.createUnderlyingMap();
    private Object[] values;

    /**
     * Creates the Map we will use to map keys to their registered values.
     */
    protected Map<K, V> createUnderlyingMap() {
        return Maps.<K, V>newHashMap();
    }

    @Nullable
    public V getObject(@Nullable K name) {
        return this.registryObjects.get(name);
    }

    /**
     * Register an object on this registry.
     */
    public void putObject(K key, V value) {
        Validate.notNull(key);
        Validate.notNull(value);
        this.values = null;

        if (this.registryObjects.containsKey(key)) {
            LOGGER.debug("Adding duplicate key '{}' to registry", key);
        }

        this.registryObjects.put(key, value);
    }

    /**
     * Gets all the keys recognized by this registry.
     */
    public Set<K> getKeys() {
        return Collections.<K>unmodifiableSet(this.registryObjects.keySet());
    }

    @Nullable
    public V getRandomObject(Random random) {
        if (this.values == null) {
            Collection<?> collection = this.registryObjects.values();

            if (collection.isEmpty()) {
                return (V) null;
            }

            this.values = collection.toArray(new Object[collection.size()]);
        }

        return (V) this.values[random.nextInt(this.values.length)];
    }

    /**
     * Does this registry contain an entry for the given key?
     */
    public boolean containsKey(K key) {
        return this.registryObjects.containsKey(key);
    }

    public Iterator<V> iterator() {
        return this.registryObjects.values().iterator();
    }
}