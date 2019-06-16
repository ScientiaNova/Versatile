package com.EmosewaPixel.pixellib.resourceAddition;

import com.EmosewaPixel.pixellib.resourceAddition.recipeTypes.ShapedRecipeSupplier;
import com.EmosewaPixel.pixellib.resourceAddition.recipeTypes.ShapelessRecipeSupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.function.Supplier;

//This class contains functions used for adding recipes that get injected on world load
public class RecipeInjector implements IResourceManagerReloadListener {
    private RecipeManager recipeManager;

    public RecipeInjector(RecipeManager manager) {
        recipeManager = manager;
    }

    private static ArrayList<Supplier<IRecipe>> RECIPES = new ArrayList<>();

    public void onResourceManagerReload(IResourceManager manager) {
        RECIPES.stream().filter(s -> s.get() != null).forEach(s -> recipeManager.addRecipe(s.get()));
    }

    public static void addShapelessRecipe(ResourceLocation name, String group, ItemStack output, @Nonnull Object... inputs) {
        RECIPES.add(new ShapelessRecipeSupplier(name, group, output, inputs));
    }

    public static void addShapelessRecipe(ResourceLocation name, ItemStack output, @Nonnull Object... inputs) {
        addShapelessRecipe(name, "", output, inputs);
    }

    public static void addShapedRecipe(ResourceLocation name, String group, ItemStack output, @Nonnull Object... shape) {
        RECIPES.add(new ShapedRecipeSupplier(name, group, output, shape));
    }

    public static void addShapedRecipe(ResourceLocation name, ItemStack output, @Nonnull Object... shape) {
        addShapedRecipe(name, "", output, shape);
    }

    public static void addFurnaceRecipe(ResourceLocation name, Object input, ItemStack output) {
        RECIPES.add(() -> new FurnaceRecipe(name, "", getIngredient(input), output, 0f, 200));
    }

    public static Ingredient getIngredient(Object input) {
        if (input instanceof Ingredient)
            return (Ingredient) input;
        if (input instanceof ItemStack)
            return Ingredient.fromStacks((ItemStack) input);
        if (input instanceof Tag)
            return Ingredient.fromTag((Tag<Item>) input);
        return Ingredient.EMPTY;
    }
}