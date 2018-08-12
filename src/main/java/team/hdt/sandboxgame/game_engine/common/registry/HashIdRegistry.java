package team.hdt.sandboxgame.game_engine.common.registry;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import team.hdt.sandboxgame.game_engine.common.Identifier;
import team.hdt.sandboxgame.game_engine.common.util.interfaces.Nullable;

import java.util.HashMap;
import java.util.Map;

public class HashIdRegistry<T extends RegistryEntry> implements IdRegistry<T> {
    private final Map<Identifier, T> entries = new HashMap<>();
    private final Int2ObjectMap<T> idMap = new Int2ObjectOpenHashMap<>();
    private final Object2IntMap<Identifier> reverseIdLookup = new Object2IntOpenHashMap<>();

    @Override
    public void register(Identifier identifier, T value, int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        if (this.idMap.containsKey(id)) {
            throw new IllegalArgumentException("Id " + id + " already registered");
        }
        if (this.entries.containsKey(identifier)) {
            throw new IllegalArgumentException("Identifier " + identifier + " already registered");
        }

        value.setIdentifier(identifier);

        this.entries.put(identifier, value);
        this.idMap.put(id, value);
        this.reverseIdLookup.put(identifier, id);
    }

    @Nullable
    @Override
    public T get(int id) {
        return this.idMap.get(id);
    }

    @Nullable
    @Override
    public T get(Identifier identifier) {
        return this.entries.get(identifier);
    }

    @Override
    public int getId(Identifier identifier) {
        return this.reverseIdLookup.getOrDefault(identifier, -1);
    }
}
