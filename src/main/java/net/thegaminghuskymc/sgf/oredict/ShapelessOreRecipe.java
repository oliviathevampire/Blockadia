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

package net.thegaminghuskymc.sgf.oredict;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.client.util.RecipeItemHelper;
import net.thegaminghuskymc.sandboxgame.inventory.InventoryCrafting;
import net.thegaminghuskymc.sandboxgame.item.Item;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.item.crafting.IRecipe;
import net.thegaminghuskymc.sandboxgame.item.crafting.Ingredient;
import net.thegaminghuskymc.sandboxgame.util.JsonUtils;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sgf.common.crafting.CraftingHelper;
import net.thegaminghuskymc.sgf.common.crafting.JsonContext;
import net.thegaminghuskymc.sgf.common.util.RecipeMatcher;
import net.thegaminghuskymc.sgf.registries.IForgeRegistryEntry;

public class ShapelessOreRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    @Nonnull
    protected ItemStack output = ItemStack.EMPTY;
    protected NonNullList<Ingredient> input = NonNullList.create();
    protected ResourceLocation group;
    protected boolean isSimple = true;

    public ShapelessOreRecipe(ResourceLocation group, Block result, Object... recipe){ this(group, new ItemStack(result), recipe); }
    public ShapelessOreRecipe(ResourceLocation group, Item result, Object... recipe){ this(group, new ItemStack(result), recipe); }
    public ShapelessOreRecipe(ResourceLocation group, NonNullList<Ingredient> input, @Nonnull ItemStack result)
    {
        this.group = group;
        output = result.copy();
        this.input = input;
        for (Ingredient i : input)
            this.isSimple &= i.isSimple();
    }
    public ShapelessOreRecipe(ResourceLocation group, @Nonnull ItemStack result, Object... recipe)
    {
        this.group = group;
        output = result.copy();
        for (Object in : recipe)
        {
            Ingredient ing = CraftingHelper.getIngredient(in);
            if (ing != null)
            {
                input.add(ing);
                this.isSimple &= ing.isSimple();
            }
            else
            {
                String ret = "Invalid shapeless ore recipe: ";
                for (Object tmp :  recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    @Nonnull
    public ItemStack getRecipeOutput(){ return output; }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1){ return output.copy(); }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world)
    {
        int ingredientCount = 0;
        RecipeItemHelper recipeItemHelper = new RecipeItemHelper();
        List<ItemStack> items = Lists.newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty())
            {
                ++ingredientCount;
                if (this.isSimple)
                    recipeItemHelper.accountStack(itemstack, 1);
                else
                    items.add(itemstack);
            }
        }

        if (ingredientCount != this.input.size())
            return false;

        if (this.isSimple)
            return recipeItemHelper.canCraft(this, null);

        return RecipeMatcher.findMatches(items, this.input) != null;
    }

    @Override
    @Nonnull
    public NonNullList<Ingredient> getIngredients()
    {
        return this.input;
    }

    /**
     * Recipes with equal group are combined into one button in the recipe book
     */
    @Override
    @Nonnull
    public String getGroup()
    {
        return this.group == null ? "" : this.group.toString();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canFit(int width, int height)
    {
        return width * height >= this.input.size();
    }

    public static ShapelessOreRecipe factory(JsonContext context, JsonObject json)
    {
        String group = JsonUtils.getString(json, "group", "");

        NonNullList<Ingredient> ings = NonNullList.create();
        for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients"))
            ings.add(CraftingHelper.getIngredient(ele, context));

        if (ings.isEmpty())
            throw new JsonParseException("No ingredients for shapeless recipe");

        ItemStack itemstack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
        return new ShapelessOreRecipe(group.isEmpty() ? null : new ResourceLocation(group), ings, itemstack);
    }
}