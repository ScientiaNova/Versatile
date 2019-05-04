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
        for (Object input : inputs)
            this.inputs.add(input);
    }

    @Override
    public IRecipe get() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (Object input : inputs)
            if (RecipeInjector.getIngredient(input) != null)
                ingredients.add(RecipeInjector.getIngredient(input));

        return new ShapelessRecipe(name, group, output, ingredients);
    }
}
