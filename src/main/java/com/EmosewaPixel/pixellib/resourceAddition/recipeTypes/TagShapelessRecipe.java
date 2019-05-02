package com.EmosewaPixel.pixellib.resourceAddition.recipeTypes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;

public class TagShapelessRecipe implements IRecipe {
    private final ResourceLocation id;
    private final String group;
    private final ItemStack recipeOutput;
    private final NonNullList<Tag<Item>> recipeItems;

    public TagShapelessRecipe(ResourceLocation idIn, String group, ItemStack recipeOutputIn, NonNullList<Tag<Item>> recipeItemsIn) {
        this.id = idIn;
        this.group = group;
        this.recipeOutput = recipeOutputIn;
        this.recipeItems = recipeItemsIn;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    public String getGroup() {
        return group;
    }

    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (Tag<Item> tag : recipeItems)
            if (tag == null) {
                ingredients.add(Ingredient.EMPTY);
            } else
                ingredients.add(Ingredient.fromTag(tag));
        return ingredients;
    }

    public boolean matches(IInventory inv, World worldIn) {
        ArrayList<ItemStack> inputs = new ArrayList<>();

        for (int j = 0; j < inv.getHeight(); ++j)
            for (int k = 0; k < inv.getWidth(); ++k) {
                ItemStack itemstack = inv.getStackInSlot(k + j * inv.getWidth());
                if (!itemstack.isEmpty())
                    inputs.add(itemstack);
            }

        if (inputs.size() != recipeItems.size())
            return false;

        int matches = 0;
        for (Tag<Item> tag : recipeItems)
            for (ItemStack input : inputs)
                if (tag.contains(input.getItem()))
                    matches++;

        return matches == inputs.size();
    }

    public ItemStack getCraftingResult(IInventory inv) {
        return this.recipeOutput.copy();
    }

    public boolean canFit(int width, int height) {
        return width * height >= this.recipeItems.size();
    }
}
