package com.EmosewaPixel.pixellib.resourceAddition.recipeTypes;

import com.EmosewaPixel.pixellib.resourceAddition.RecipeInjector;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class ShapelessRecipeSupplier implements Supplier<IRecipe> {
    private ResourceLocation name;
    private String group;
    private ItemStack output;
    private ArrayList<Object> inputs = new ArrayList<>();

    public ShapelessRecipeSupplier(ResourceLocation name, String group, ItemStack output, @Nonnull Object... inputs) {
        this.name = name;
        this.group = group;
        this.output = output;
        this.inputs.addAll(Arrays.asList(inputs));
    }

    @Override
    public IRecipe get() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        inputs.stream().map(RecipeInjector::getIngredient).forEach(ingredients::add);

        return new ShapelessRecipe(name, group, output, ingredients);
    }
}
