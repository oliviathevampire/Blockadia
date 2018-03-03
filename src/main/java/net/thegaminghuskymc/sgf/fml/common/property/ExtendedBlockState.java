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

package net.thegaminghuskymc.sgf.fml.common.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.thegaminghuskymc.sandboxgame.block.state.BlockStateContainer;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;

public class ExtendedBlockState extends BlockStateContainer
{
    public ExtendedBlockState(Block blockIn, IProperty<?>[] properties, IUnlistedProperty<?>[] unlistedProperties)
    {
        super(blockIn, properties);
        ImmutableSet.Builder<IUnlistedProperty<?>> builder = ImmutableSet.builder();
        for(IUnlistedProperty<?> property : unlistedProperties)
        {
            builder.add(property);
        }
    }

    @Override
    @Nonnull
    protected StateImplementation createState(@Nonnull Block block, @Nonnull  ImmutableMap<IProperty<?>, Comparable<?>> properties)
    {
        return new ExtendedStateImplementation(block, properties, null, null);
    }

    protected static class ExtendedStateImplementation extends StateImplementation
    {
        private IBlockState cleanState;

        protected ExtendedStateImplementation(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, @Nullable ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> table, IBlockState clean)
        {
            super(block, properties, table);
            this.cleanState = clean == null ? this : clean;
        }

        /**
         * Get a version of this BlockState with the given Property now set to the given value
         */
        @Override
        @Nonnull
        public <T extends Comparable<T>, V extends T> IBlockState withProperty(@Nonnull IProperty<T> property, @Nonnull V value)
        {
            IBlockState clean = super.withProperty(property, value);
            if (clean == this.cleanState) {
                return this;
            }

            return new ExtendedStateImplementation(getBlock(), clean.getProperties(), ((StateImplementation)clean).getPropertyValueTable(), this.cleanState);
        }

    }
}