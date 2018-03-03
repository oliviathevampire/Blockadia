package net.thegaminghuskymc.sandboxgame.item.crafting;

import com.google.gson.*;
import net.thegaminghuskymc.sandboxgame.inventory.InventoryCrafting;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.registries.RegistryNamespaced;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.item.crafting.RecipeBookCloning;
import net.thegaminghuskymc.sandboxgame.item.crafting.RecipeFireworks;
import net.thegaminghuskymc.sandboxgame.util.JsonUtils;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.registries.GameData;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;

public class CraftingManager {
    public static final RegistryNamespaced<ResourceLocation, IRecipe> REGISTRY = GameData.getWrapper(IRecipe.class);
    private static final Logger LOGGER = LogManager.getLogger();
    private static int nextAvailableId;

    public static boolean init() {
        try {
            register("bookcloning", new RecipeBookCloning());
            register("fireworks", new RecipeFireworks());
            return parseJsonRecipes();
        } catch (Throwable var1) {
            return false;
        }
    }

    private static boolean parseJsonRecipes() {
        FileSystem filesystem = null;
        Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        boolean flag1;

        try {
            URL url = CraftingManager.class.getResource("/assets/.mcassetsroot");

            if (url != null) {
                URI uri = url.toURI();
                Path path;

                if ("file".equals(uri.getScheme())) {
                    path = Paths.get(CraftingManager.class.getResource("/assets/minecraft/recipes").toURI());
                } else {
                    if (!"jar".equals(uri.getScheme())) {
                        LOGGER.error("Unsupported scheme " + uri + " trying to list all recipes");
                        boolean flag2 = false;
                        return flag2;
                    }

                    filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    path = filesystem.getPath("/assets/minecraft/recipes");
                }

                Iterator<Path> iterator = Files.walk(path).iterator();

                while (iterator.hasNext()) {
                    Path path1 = iterator.next();

                    if ("json".equals(FilenameUtils.getExtension(path1.toString()))) {
                        Path path2 = path.relativize(path1);
                        String s = FilenameUtils.removeExtension(path2.toString()).replaceAll("\\\\", "/");
                        ResourceLocation resourcelocation = new ResourceLocation(s);
                        BufferedReader bufferedreader = null;

                        try {
                            boolean flag;

                            try {
                                bufferedreader = Files.newBufferedReader(path1);
                                register(s, parseRecipeJson(JsonUtils.fromJson(gson, bufferedreader, JsonObject.class)));
                            } catch (JsonParseException jsonparseexception) {
                                LOGGER.error("Parsing error loading recipe " + resourcelocation, jsonparseexception);
                                flag = false;
                                return flag;
                            } catch (IOException ioexception) {
                                LOGGER.error("Couldn't read recipe " + resourcelocation + " from " + path1, ioexception);
                                flag = false;
                                return flag;
                            }
                        } finally {
                            IOUtils.closeQuietly(bufferedreader);
                        }
                    }
                }

                return true;
            }

            LOGGER.error("Couldn't find .mcassetsroot");
            flag1 = false;
        } catch (IOException | URISyntaxException urisyntaxexception) {
            LOGGER.error("Couldn't get a list of all recipe files", (Throwable) urisyntaxexception);
            flag1 = false;
            return flag1;
        } finally {
            IOUtils.closeQuietly((Closeable) filesystem);
        }

        return flag1;
    }

    private static IRecipe parseRecipeJson(JsonObject p_193376_0_) {
        String s = JsonUtils.getString(p_193376_0_, "type");

        if ("crafting_shaped".equals(s)) {
            return ShapedRecipes.deserialize(p_193376_0_);
        } else if ("crafting_shapeless".equals(s)) {
            return ShapelessRecipes.deserialize(p_193376_0_);
        } else {
            throw new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
        }
    }

    //Forge: Made private use GameData/Registry events!
    private static void register(String name, IRecipe recipe) {
        register(new ResourceLocation(name), recipe);
    }

    //Forge: Made private use GameData/Registry events!
    private static void register(ResourceLocation name, IRecipe recipe) {
        if (REGISTRY.containsKey(name)) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + name);
        } else {
            REGISTRY.register(nextAvailableId++, name, recipe);
        }
    }

    /**
     * Retrieves an ItemStack that has multiple recipes for it.
     */
    public static ItemStack findMatchingResult(InventoryCrafting craftMatrix, World worldIn) {
        for (IRecipe irecipe : REGISTRY) {
            if (irecipe.matches(craftMatrix, worldIn)) {
                return irecipe.getCraftingResult(craftMatrix);
            }
        }

        return ItemStack.EMPTY;
    }

    @Nullable
    public static IRecipe findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn) {
        for (IRecipe irecipe : REGISTRY) {
            if (irecipe.matches(craftMatrix, worldIn)) {
                return irecipe;
            }
        }

        return null;
    }

    public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftMatrix, World worldIn) {
        for (IRecipe irecipe : REGISTRY) {
            if (irecipe.matches(craftMatrix, worldIn)) {
                return irecipe.getRemainingItems(craftMatrix);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            nonnulllist.set(i, craftMatrix.getStackInSlot(i));
        }

        return nonnulllist;
    }

    @Nullable
    public static IRecipe getRecipe(ResourceLocation name) {
        return REGISTRY.getObject(name);
    }

    @Deprecated //DO NOT USE THIS
    public static int getIDForRecipe(IRecipe recipe) {
        return REGISTRY.getIDForObject(recipe);
    }

    @Deprecated //DO NOT USE THIS
    @Nullable
    public static IRecipe getRecipeById(int id) {
        return REGISTRY.getObjectById(id);
    }
}