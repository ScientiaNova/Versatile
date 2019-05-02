package com.EmosewaPixel.pixellib.resourceAddition;

import com.EmosewaPixel.pixellib.resourceAddition.recipeTypes.TagFurnaceRecipe;
import com.EmosewaPixel.pixellib.resourceAddition.recipeTypes.TagShapedRecipe;
import com.EmosewaPixel.pixellib.resourceAddition.recipeTypes.TagShapelessRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;

public class RecipeInjector {
    private RecipeManager recipeManager;

    public RecipeInjector(RecipeManager manager) {
        recipeManager = manager;
    }


    private static ArrayList<IRecipe> RECIPES = new ArrayList<>();

    public void injectRecipes(IResourceManager manager) {
        for (IRecipe recipe : RECIPES)
            recipeManager.addRecipe(recipe);
    }

    /***
     * @param name the name of the recipe
     * @param group the group of the recipe
     * @param output the output stack of the recipe
     * @param inputs the inputs of the recipe. They all need to be either stacks or tags, not mixed
     */
    public static void addShapelessRecipe(ResourceLocation name, String group, ItemStack output, @Nonnull Object... inputs) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        NonNullList<Tag<Item>> tags = NonNullList.create();
        for (Object input : inputs)
            if (input instanceof ItemStack)
                ingredients.add(Ingredient.fromStacks((ItemStack) input));
            else if (input instanceof Tag)
                tags.add((Tag<Item>) input);

        if (ingredients.size() > 0 && tags.size() > 0)
            return;

        if (ingredients.size() > 0)
            RECIPES.add(new ShapelessRecipe(name, group, output, ingredients));
        else
            RECIPES.add(new TagShapelessRecipe(name, group, output, tags));
    }


    public static void addShapelessRecipe(String name, String group, ItemStack output, @Nonnull Object... inputs) {
        addShapelessRecipe(new ResourceLocation("pixellib", name), group, output, inputs);
    }

    public static void addShapelessRecipe(String name, ItemStack output, @Nonnull Object... inputs) {
        addShapelessRecipe(name, "", output, inputs);
    }

    /***
     * @param name the name of the recipe
     * @param group the group of the recipe
     * @param output the output stack of the recipe
     * @param shape the pattern and keys in the recipe. For example: "XXX", "XAX", "XXX", 'A', new ItemStack(Items.APPLE), 'X', new ItemStack(Items.GOLDEN_INGOT)
     *              You can't have both stacks and tags as recipe inputs
     */
    public static void addShapedRecipe(ResourceLocation name, String group, ItemStack output, @Nonnull Object... shape) {
        int height = 0;
        int width = 0;
        HashMap<Character, Ingredient> stacks = new HashMap<>();
        HashMap<Character, Tag<Item>> tags = new HashMap<>();
        ArrayList<Character> charList = new ArrayList<>();
        for (int i = 0; i < shape.length; i++) {
            if (shape[i] instanceof String) {
                height++;
                if (((String) shape[i]).length() > width)
                    width = ((String) shape[i]).length();
                for (Character character : ((String) shape[i]).toCharArray())
                    charList.add(character);
            }
            if (shape[i] instanceof Character)
                if (shape[i + 1] instanceof ItemStack) {
                    stacks.put((char) shape[i], Ingredient.fromStacks((ItemStack) shape[i + 1]));
                    i++;
                } else if (shape[i + 1] instanceof Tag) {
                    tags.put((char) shape[i], (Tag<Item>) shape[i + 1]);
                    i++;
                }
        }
        stacks.put(' ', Ingredient.EMPTY);
        tags.put(' ', null);
        ;
        if (height > 3 || width > 3)
            return;
        NonNullList<Ingredient> stackInputs = NonNullList.create();
        NonNullList<Tag<Item>> tagInputs = NonNullList.create();
        for (Character character : charList)
            if (stacks.size() > tags.size())
                stackInputs.add(stacks.get(character));
            else
                tagInputs.add(tags.get(character));

        if (stackInputs.size() > 0 && tagInputs.size() > 0)
            return;

        if (stackInputs.size() > 0)
            RECIPES.add(new ShapedRecipe(name, group, width, height, stackInputs, output));
        else
            RECIPES.add(new TagShapedRecipe(name, group, width, height, tagInputs, output));
    }

    public static void addShapedRecipe(String name, String group, ItemStack output, @Nonnull Object... shape) {
        addShapedRecipe(new ResourceLocation("pixellib", name), group, output, shape);
    }

    public static void addShapedRecipe(String name, ItemStack output, @Nonnull Object... shape) {
        addShapedRecipe(name, "", output, shape);
    }

    public static void addFurnaceRecipe(ResourceLocation name, Object input, ItemStack output) {
        if (input instanceof ItemStack)
            RECIPES.add(new FurnaceRecipe(name, "", Ingredient.fromStacks((ItemStack) input), output, 0f, 200));
        else if (input instanceof Tag)
            RECIPES.add(new TagFurnaceRecipe(name, (Tag<Item>) input, output, 0f, 200));
    }

    public static void addFurnaceRecipe(String name, Object input, ItemStack output) {
        addFurnaceRecipe(new ResourceLocation("pixellib", name), input, output);
    }
}
