/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.thegaminghuskymc.sgf.registries;

import java.util.List;

import com.google.common.collect.Lists;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

import javax.annotation.Nullable;

public class RegistryBuilder<T extends IForgeRegistryEntry<T>>
{
    private ResourceLocation registryName;
    private Class<T> registryType;
    private ResourceLocation optionalDefaultKey;
    private int minId = 0;
    private int maxId = Integer.MAX_VALUE - 1;
    private List<IForgeRegistry.AddCallback<T>> addCallback = Lists.newArrayList();
    private List<IForgeRegistry.ClearCallback<T>> clearCallback = Lists.newArrayList();
    private List<IForgeRegistry.CreateCallback<T>> createCallback = Lists.newArrayList();
    private boolean saveToDisc = true;
    private boolean allowOverrides = true;
    private boolean allowModifications = false;
    private IForgeRegistry.DummyFactory<T> dummyFactory;
    private IForgeRegistry.MissingFactory<T> missingFactory;

    public RegistryBuilder<T> setName(ResourceLocation name)
    {
        this.registryName = name;
        return this;
    }

    public RegistryBuilder<T> setType(Class<T> type)
    {
        this.registryType = type;
        return this;
    }

    public RegistryBuilder<T> setIDRange(int min, int max)
    {
        this.minId = min;
        this.maxId = max;
        return this;
    }

    public RegistryBuilder<T> setMaxID(int max)
    {
        return this.setIDRange(0, max);
    }

    public RegistryBuilder<T> setDefaultKey(ResourceLocation key)
    {
        this.optionalDefaultKey = key;
        return this;
    }

    @SuppressWarnings("unchecked")
    public RegistryBuilder<T> addCallback(Object inst)
    {
        if (inst instanceof IForgeRegistry.AddCallback)
            this.add((IForgeRegistry.AddCallback<T>)inst);
        if (inst instanceof IForgeRegistry.ClearCallback)
            this.add((IForgeRegistry.ClearCallback<T>)inst);
        if (inst instanceof IForgeRegistry.CreateCallback)
            this.add((IForgeRegistry.CreateCallback<T>)inst);
        if (inst instanceof IForgeRegistry.DummyFactory)
            this.set((IForgeRegistry.DummyFactory<T>)inst);
        if (inst instanceof IForgeRegistry.MissingFactory)
            this.set((IForgeRegistry.MissingFactory<T>)inst);
        return this;
    }

    public RegistryBuilder<T> add(IForgeRegistry.AddCallback<T> add)
    {
        this.addCallback.add(add);
        return this;
    }

    public RegistryBuilder<T> add(IForgeRegistry.ClearCallback<T> clear)
    {
        this.clearCallback.add(clear);
        return this;
    }

    public RegistryBuilder<T> add(IForgeRegistry.CreateCallback<T> create)
    {
        this.createCallback.add(create);
        return this;
    }

    public RegistryBuilder<T> set(IForgeRegistry.DummyFactory<T> factory)
    {
        this.dummyFactory = factory;
        return this;
    }

    public RegistryBuilder<T> set(IForgeRegistry.MissingFactory<T> missing)
    {
        this.missingFactory = missing;
        return this;
    }

    public RegistryBuilder<T> disableSaving()
    {
        this.saveToDisc = false;
        return this;
    }

    public RegistryBuilder<T> disableOverrides()
    {
        this.allowOverrides = false;
        return this;
    }

    public RegistryBuilder<T> allowModification()
    {
        this.allowModifications = true;
        return this;
    }

    public IForgeRegistry<T> create()
    {
        return RegistryManager.ACTIVE.createRegistry(registryName, registryType, optionalDefaultKey, minId, maxId,
                getAdd(), getClear(), getCreate(), saveToDisc, allowOverrides, allowModifications, dummyFactory, missingFactory);
    }

    @Nullable
    private IForgeRegistry.AddCallback<T> getAdd()
    {
        if (addCallback.isEmpty())
            return null;
        if (addCallback.size() == 1)
            return addCallback.get(0);

        return (owner, stage, id, obj, old) ->
        {
            for (IForgeRegistry.AddCallback<T> cb : this.addCallback)
                cb.onAdd(owner, stage, id, obj, old);
        };
    }

    @Nullable
    private IForgeRegistry.ClearCallback<T> getClear()
    {
        if (clearCallback.isEmpty())
            return null;
        if (clearCallback.size() == 1)
            return clearCallback.get(0);

        return (owner, stage) ->
        {
            for (IForgeRegistry.ClearCallback<T> cb : this.clearCallback)
                cb.onClear(owner, stage);
        };
    }

    @Nullable
    private IForgeRegistry.CreateCallback<T> getCreate()
    {
        if (createCallback.isEmpty())
            return null;
        if (createCallback.size() == 1)
            return createCallback.get(0);

        return (owner, stage) ->
        {
            for (IForgeRegistry.CreateCallback<T> cb : this.createCallback)
                cb.onCreate(owner, stage);
        };
    }
}