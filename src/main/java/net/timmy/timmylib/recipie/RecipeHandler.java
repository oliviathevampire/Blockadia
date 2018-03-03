package net.timmy.timmylib.recipie;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.item.ItemBlock;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.common.Loader;
import net.timmy.timmylib.interf.IRecipeGrouped;
import net.timmy.timmylib.utils.ProxyRegistry;

import java.util.*;

public final class RecipeHandler {
    private static final List<ResourceLocation> usedNames = new ArrayList();

    public RecipeHandler() {
    }

    public static void addOreDictRecipe(ItemStack output, Object... inputs) {
        addShapedRecipe((MultiRecipe) null, output, inputs);
    }

    public static void addShapelessOreDictRecipe(ItemStack output, Object... inputs) {
        addShapelessRecipe((MultiRecipe) null, output, inputs);
    }

    public static void addOreDictRecipe(MultiRecipe multi, ItemStack output, Object... inputs) {
        addShapedRecipe(multi, output, inputs);
    }

    public static void addShapelessOreDictRecipe(MultiRecipe multi, ItemStack output, Object... inputs) {
        addShapelessRecipe(multi, output, inputs);
    }

    public static void addShapelessRecipe(ItemStack output, Object... inputs) {
        addShapelessRecipe((MultiRecipe) null, output, inputs);
    }

    public static void addShapedRecipe(ItemStack output, Object... inputs) {
        addShapedRecipe((MultiRecipe) null, output, inputs);
    }

    public static void addShapelessRecipe(MultiRecipe multi, ItemStack output, Object... inputs) {
        String namespace = getNamespace();
        NonNullList<Ingredient> ingredients = NonNullList.create();
        Object[] var5 = inputs;
        int var6 = inputs.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            Object input = var5[var7];
            ingredients.add(asIngredient(input));
        }

        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("No ingredients for shapeless recipe");
        } else if (ingredients.size() > 9) {
            throw new IllegalArgumentException("Too many ingredients for shapeless recipe");
        } else {
            ShapelessRecipes recipe = new ShapelessRecipes(outputGroup(namespace, output), output, ingredients);
            if (multi != null) {
                multi.addRecipe(recipe);
            } else {
                addRecipe(unusedLocForOutput(namespace, output), recipe);
            }

        }
    }

    public static void addShapedRecipe(MultiRecipe multi, ItemStack output, Object... inputs) {
        String namespace = getNamespace();
        ArrayList<String> pattern = Lists.newArrayList();
        Map<String, Ingredient> key = Maps.newHashMap();
        Iterator itr = Arrays.asList(inputs).iterator();

        while (itr.hasNext()) {
            Object obj = itr.next();
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() > 3) {
                    throw new IllegalArgumentException("Invalid string length for recipe " + str.length());
                }

                if (pattern.size() > 2) {
                    throw new IllegalArgumentException("Recipe has too many crafting rows!");
                }

                pattern.add(str);
            } else {
                if (!(obj instanceof Character)) {
                    throw new IllegalArgumentException("Unexpected argument of type " + obj.getClass().toString());
                }

                key.put(((Character) obj).toString(), asIngredient(itr.next()));
            }
        }

        int width = ((String) pattern.get(0)).length();
        int height = pattern.size();

        try {
            key.put(" ", Ingredient.EMPTY);
            Object ingredients = prepareMaterials(pattern.toArray(new String[pattern.size()]), key, width, height);
            ShapedRecipes recipe = new ShapedRecipes(outputGroup(namespace, output), width, height, (NonNullList) ingredients, output);
            if (multi != null) {
                multi.addRecipe(recipe);
            } else {
                addRecipe(unusedLocForOutput(namespace, output), recipe);
            }

        } catch (Throwable var11) {
            throw new RuntimeException(var11);
        }
    }

    private static NonNullList<Ingredient> prepareMaterials(String[] p_192402_0_, Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_192402_2_ * p_192402_3_, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(p_192402_1_.keySet());
        set.remove(" ");

        for (int i = 0; i < p_192402_0_.length; ++i) {
            for (int j = 0; j < p_192402_0_[i].length(); ++j) {
                String s = p_192402_0_[i].substring(j, j + 1);
                Ingredient ingredient = (Ingredient) p_192402_1_.get(s);
                set.remove(s);
                nonnulllist.set(j + p_192402_2_ * i, ingredient);
            }
        }

        return nonnulllist;
    }

    public static void addRecipe(ResourceLocation res, IRecipe recipe) {
        if (!(recipe instanceof ModRecipe) && recipe.getRecipeOutput().isEmpty()) {
            throw new IllegalArgumentException("Illegal recipe output");
        } else {
            recipe.setRegistryName(res);
            usedNames.add(res);
            ProxyRegistry.register(recipe);
        }
    }

    private static Ingredient asIngredient(Object object) {
        if (object instanceof Item) {
            return Ingredient.fromItem((Item) object);
        } else if (object instanceof Block) {
            return Ingredient.fromStacks(new ItemStack[]{new ItemStack((Block) object)});
        } else if (object instanceof ItemStack) {
            return Ingredient.fromStacks(new ItemStack[]{(ItemStack) object});
        } else if (object instanceof String) {
            return new OreIngredient((String) object);
        } else {
            throw new IllegalArgumentException("Cannot convert object of type " + object.getClass().toString() + " to an Ingredient!");
        }
    }

    private static ResourceLocation unusedLocForOutput(String namespace, ItemStack output) {
        ResourceLocation baseLoc = new ResourceLocation(namespace, output.getItem().getRegistryName().getResourcePath());
        ResourceLocation recipeLoc = baseLoc;

        for (int index = 0; usedNames.contains(recipeLoc); recipeLoc = new ResourceLocation(namespace, baseLoc.getResourcePath() + "_" + index)) {
            ++index;
        }

        return recipeLoc;
    }

    private static String outputGroup(String namespace, ItemStack output) {
        Item item = output.getItem();
        if (item instanceof IRecipeGrouped) {
            return namespace + ":" + ((IRecipeGrouped) item).getRecipeGroup();
        } else {
            if (item instanceof ItemBlock) {
                Block block = ((ItemBlock) item).getBlock();
                if (block instanceof IRecipeGrouped) {
                    return namespace + ":" + ((IRecipeGrouped) block).getRecipeGroup();
                }
            }

            return output.getItem().getRegistryName().toString();
        }
    }

    private static String getNamespace() {
        return Loader.instance().activeModContainer().getModId();
    }
}