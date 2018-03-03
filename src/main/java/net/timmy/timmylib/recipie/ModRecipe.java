package net.timmy.timmylib.recipie;


import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.registries.IForgeRegistryEntry;

public abstract class ModRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public ModRecipe(ResourceLocation res) {
        RecipeHandler.addRecipe(res, this);
    }
}