package com.EmosewaPixel.pixellib.resourceAddition.recipeTypes;

import com.EmosewaPixel.pixellib.resourceAddition.RecipeInjector;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class ShapedRecipeSupplier implements Supplier<IRecipe> {
    private ResourceLocation name;
    private String group;
    private ItemStack output;
    private ArrayList<Object> shape = new ArrayList<>();

    public ShapedRecipeSupplier(ResourceLocation name, String group, ItemStack output, @Nonnull Object... shape) {
        this.name = name;
        this.group = group;
        this.output = output;
        for (Object input : shape)
            this.shape.add(input);
    }

    @Override
    public IRecipe get() {
        int height = 0;
        int width = 0;
        HashMap<Character, Ingredient> stacks = new HashMap<>();
        ArrayList<Character> charList = new ArrayList<>();
        for (int i = 0; i < shape.size(); i++) {
            if (shape.get(i) instanceof String) {
                height++;
                if (((String) shape.get(i)).length() > width)
                    width = ((String) shape.get(i)).length();
                for (Character character : ((String) shape.get(i)).toCharArray())
                    charList.add(character);
            }
            if (shape.get(i) instanceof Character && RecipeInjector.getIngredient(shape.get(i + 1)) != null) {
                stacks.put((char) shape.get(i), RecipeInjector.getIngredient(shape.get(i + 1)));
                i++;
            }
        }
        stacks.put(' ', Ingredient.EMPTY);

        if (height > 3 || width > 3)
            return null;

        NonNullList<Ingredient> stackInputs = NonNullList.create();
        for (Character character : charList)
            stackInputs.add(stacks.get(character));

        return new ShapedRecipe(name, group, width, height, stackInputs, output);
    }
}
