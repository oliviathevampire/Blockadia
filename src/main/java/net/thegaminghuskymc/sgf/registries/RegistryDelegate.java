package net.thegaminghuskymc.sgf.registries;

import com.google.common.base.Objects;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

final class RegistryDelegate<T> implements IRegistryDelegate<T>
{
    private T referent;
    private ResourceLocation name;
    private final Class<T> type;

    public RegistryDelegate(T referent, Class<T> type)
    {
        this.referent = referent;
        this.type = type;
    }

    @Override
    public T get() { return referent; }
    @Override
    public ResourceLocation name() { return name; }
    @Override
    public Class<T> type() { return this.type; }

    void changeReference(T newTarget){ this.referent = newTarget; }
    public void setName(ResourceLocation name) { this.name = name; }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof RegistryDelegate)
        {
            RegistryDelegate<?> other = (RegistryDelegate<?>) obj;
            return Objects.equal(other.name, name);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }
}