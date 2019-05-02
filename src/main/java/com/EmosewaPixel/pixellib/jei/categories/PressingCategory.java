package com.EmosewaPixel.pixellib.jei.categories;

import com.EmosewaPixel.pixellib.recipes.MachineRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class PressingCategory extends MachineBaseCategory {
    public PressingCategory(IGuiHelper helper) {
        super(helper, "pressing", Blocks.PISTON.asItem(), RecipeTypes.PressingRecipe.class);
        this.backGround = helper.createDrawable(new ResourceLocation("pixellib:textures/gui/jei/pressing.png"), 0, 0, 82, 54);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, MachineRecipe recipe, IIngredients ingredients) {
        List<List<ItemStack>> inputs = recipe.getInputsAsList();
        List<ItemStack> outputs = recipe.getOutputsAsList();

        layout.getItemStacks().init(0, true, 0, 36);
        layout.getItemStacks().set(0, inputs.get(0));

        layout.getItemStacks().init(1, false, 60, 18);
        layout.getItemStacks().set(1, outputs.get(0));
    }

    @Override
    public void draw(MachineRecipe recipe, double mouseX, double mouseY) {
        arrow.draw(24, 18);
    }
}