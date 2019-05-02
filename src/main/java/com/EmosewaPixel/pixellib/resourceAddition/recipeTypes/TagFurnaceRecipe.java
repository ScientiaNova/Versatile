package com.EmosewaPixel.pixellib.resourceAddition.recipeTypes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TagFurnaceRecipe extends FurnaceRecipe {
    private Tag<Item> tagInput;

    public TagFurnaceRecipe(ResourceLocation name, Tag<Item> input, ItemStack output, float xp, int time) {
        super(name, "", Ingredient.fromTag(input), output, xp, time);
        tagInput = input;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return tagInput.contains(inv.getStackInSlot(0).getItem());
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(Ingredient.fromTag(tagInput));
        return nonnulllist;
    }
}
