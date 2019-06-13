package com.EmosewaPixel.pixellib.resourceAddition.recipeTypes;

import com.EmosewaPixel.pixellib.resourceAddition.RecipeInjector;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShapedRecipeSupplier implements Supplier<IRecipe> {
    private ResourceLocation name;
    private String group;
    private ItemStack output;
    private ArrayList<Object> shape = new ArrayList<>();

    public ShapedRecipeSupplier(ResourceLocation name, String group, ItemStack output, @Nonnull Object... shape) {
        this.name = name;
        this.group = group;
        this.output = output;
        this.shape.addAll(Arrays.asList(shape));
    }

    @Override
    public IRecipe get() {
        final Stream<String> shapeStringsStream = shape.stream().filter(o -> o instanceof String).map(o -> (String) o);
        int height = (int) shapeStringsStream.count();
        int width = shapeStringsStream.max(Comparator.comparingInt(String::length)).orElse("").length();
        Map<Character, Ingredient> stacks = shape.stream().filter(o -> (o instanceof Character && RecipeInjector.getIngredient(shape.get(shape.indexOf(o) + 1)) != Ingredient.EMPTY)).collect(Collectors.toMap(o -> (Character) o, o -> RecipeInjector.getIngredient(shape.get(shape.indexOf(o) + 1))));
        List<Character> charList = shapeStringsStream.flatMapToInt(String::chars).mapToObj(c -> (char) c).collect(Collectors.toList());
        stacks.put(' ', Ingredient.EMPTY);

        if (height > 3 || width > 3)
            return null;

        NonNullList<Ingredient> stackInputs = NonNullList.create();
        charList.stream().map(stacks::get).forEach(stackInputs::add);

        return new ShapedRecipe(name, group, width, height, stackInputs, output);
    }
}
