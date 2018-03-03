package net.thegaminghuskymc.sandboxgame.item.crafting;

import com.google.common.base.Predicate;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.thegaminghuskymc.sandboxgame.client.util.RecipeItemHelper;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.item.Item;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;
import net.thegaminghuskymc.sgf.oredict.OreDictionary;

import javax.annotation.Nullable;

public class Ingredient implements Predicate<ItemStack> {
    public static final Ingredient EMPTY = new Ingredient(new ItemStack[0]) {
        public boolean apply(@Nullable ItemStack p_apply_1_) {
            return p_apply_1_.isEmpty();
        }
    };
    //Because Mojang caches things... we need to invalidate them.. so... here we go..
    private static final java.util.Set<Ingredient> INSTANCES = java.util.Collections.newSetFromMap(new java.util.WeakHashMap<Ingredient, Boolean>());
    private final ItemStack[] matchingStacks;
    private final ItemStack[] matchingStacksExploded;
    private final boolean isSimple;
    private IntList matchingStacksPacked;

    protected Ingredient(int size) {
        this(new ItemStack[size]);
    }

    protected Ingredient(ItemStack... p_i47503_1_) {
        boolean simple = true;
        this.matchingStacks = p_i47503_1_;
        NonNullList<ItemStack> lst = NonNullList.create();
        for (ItemStack s : p_i47503_1_) {
            if (s.isEmpty())
                continue;
            if (s.getItem().isDamageable())
                simple = false;
            if (s.getMetadata() == OreDictionary.WILDCARD_VALUE)
                s.getItem().getSubItems(CreativeTabs.SEARCH, lst);
            else
                lst.add(s);
        }
        this.matchingStacksExploded = lst.toArray(new ItemStack[lst.size()]);
        this.isSimple = simple && this.matchingStacksExploded.length > 0;
        Ingredient.INSTANCES.add(this);
    }

    public static void invalidateAll() {
        for (Ingredient ing : INSTANCES)
            if (ing != null)
                ing.invalidate();
    }

    public static Ingredient fromItem(Item p_193367_0_) {
        return fromStacks(new ItemStack(p_193367_0_, 1, 32767));
    }

    public static Ingredient fromItems(Item... items) {
        ItemStack[] aitemstack = new ItemStack[items.length];

        for (int i = 0; i < items.length; ++i) {
            aitemstack[i] = new ItemStack(items[i]);
        }

        return fromStacks(aitemstack);
    }

    public static Ingredient fromStacks(ItemStack... stacks) {
        if (stacks.length > 0) {
            for (ItemStack itemstack : stacks) {
                if (!itemstack.isEmpty()) {
                    return new Ingredient(stacks);
                }
            }
        }

        return EMPTY;
    }

    // Merges several vanilla Ingredients together. As a qwerk of how the json is structured, we can't tell if its a single Ingredient type or multiple so we split per item and remerge here.
    //Only public for internal use, so we can access a private field in here.
    public static Ingredient merge(java.util.Collection<Ingredient> parts) {
        NonNullList<ItemStack> lst = NonNullList.create();
        for (Ingredient part : parts) {
            for (ItemStack stack : part.matchingStacks)
                lst.add(stack);
        }
        return new Ingredient(lst.toArray(new ItemStack[lst.size()]));
    }

    public ItemStack[] getMatchingStacks() {
        return this.matchingStacksExploded;
    }

    public boolean apply(@Nullable ItemStack p_apply_1_) {
        if (p_apply_1_ == null) {
            return false;
        } else {
            for (ItemStack itemstack : this.matchingStacks) {
                if (itemstack.getItem() == p_apply_1_.getItem()) {
                    int i = itemstack.getMetadata();

                    if (i == 32767 || i == p_apply_1_.getMetadata()) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public IntList getValidItemStacksPacked() {
        if (this.matchingStacksPacked == null) {
            this.matchingStacksPacked = new IntArrayList(this.matchingStacksExploded.length);

            for (ItemStack itemstack : this.matchingStacksExploded) {
                this.matchingStacksPacked.add(RecipeItemHelper.pack(itemstack));
            }

            this.matchingStacksPacked.sort(IntComparators.NATURAL_COMPARATOR);
        }

        return this.matchingStacksPacked;
    }

    protected void invalidate() {
        this.matchingStacksPacked = null;
    }

    public boolean isSimple() {
        return isSimple || this == EMPTY;
    }
}