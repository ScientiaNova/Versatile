package com.EmosewaPixel.pixellib.resourceAddition.recipeTypes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class TagShapedRecipe implements IShapedRecipe {
    private final int recipeWidth;
    private final int recipeHeight;
    private final NonNullList<Tag<Item>> recipeItems;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;

    public TagShapedRecipe(ResourceLocation idIn, String group, int recipeWidthIn, int recipeHeightIn, NonNullList<Tag<Item>> recipeItemsIn, ItemStack recipeOutputIn) {
        this.id = idIn;
        this.recipeWidth = recipeWidthIn;
        this.group = group;
        this.recipeHeight = recipeHeightIn;
        this.recipeItems = recipeItemsIn;
        this.recipeOutput = recipeOutputIn;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
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

    @Override
    public boolean canFit(int width, int height) {
        return width >= recipeWidth && height >= recipeHeight;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        for (int i = 0; i <= inv.getWidth() - recipeWidth; ++i) {
            for (int j = 0; j <= inv.getHeight() - recipeHeight; ++j) {
                if (checkMatch(inv, i, j, true)) {
                    return true;
                }

                if (checkMatch(inv, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMatch(IInventory craftingInventory, int width, int height, boolean p_77573_4_) {
        for (int i = 0; i < craftingInventory.getWidth(); ++i) {
            for (int j = 0; j < craftingInventory.getHeight(); ++j) {
                int k = i - width;
                int l = j - height;
                Tag<Item> tag = null;
                if (k >= 0 && l >= 0 && k < recipeWidth && l < this.recipeHeight) {
                    if (p_77573_4_) {
                        tag = recipeItems.get(recipeWidth - k - 1 + l * recipeWidth);
                    } else {
                        tag = recipeItems.get(k + l * recipeWidth);
                    }
                }

                if (tag != null) {
                    if (!tag.contains(craftingInventory.getStackInSlot(i + j * craftingInventory.getWidth()).getItem()))
                        return false;
                } else if (!craftingInventory.getStackInSlot(i + j * craftingInventory.getWidth()).isEmpty())
                    return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.getRecipeOutput().copy();
    }

    public int getWidth() {
        return this.recipeWidth;
    }

    @Override
    public int getRecipeWidth() {
        return getWidth();
    }

    public int getHeight() {
        return this.recipeHeight;
    }

    @Override
    public int getRecipeHeight() {
        return getHeight();
    }
}
