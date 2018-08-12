package team.hdt.sandboxgame.game_engine.common.registry;

import team.hdt.sandboxgame.game_engine.common.Identifier;
import team.hdt.sandboxgame.game_engine.common.util.interfaces.Nonnull;

public class DefaultedHashIdRegistry<T extends RegistryEntry> extends HashIdRegistry<T> {
    private T defaultEntry;

    public DefaultedHashIdRegistry<T> withDefaultEntry(T defaultEntry) {
        this.defaultEntry = defaultEntry;
        return this;
    }

    @Nonnull
    @Override
    public T get(int id) {
        T entry = super.get(id);
        if (entry == null) {
            return this.defaultEntry;
        }
        return entry;
    }

    @Nonnull
    @Override
    public T get(Identifier identifier) {
        T entry = super.get(identifier);
        if (entry == null) {
            return this.defaultEntry;
        }
        return entry;
    }
}
