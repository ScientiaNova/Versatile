package com.EmosewaPixel.pixellib.jei.categories;

import com.EmosewaPixel.pixellib.blocks.BlockRegistry;
import com.EmosewaPixel.pixellib.recipes.MachineRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class SawmillCategory extends MachineBaseCategory {
    public SawmillCategory(IGuiHelper helper) {
        super(helper, "sawmill", BlockRegistry.SAWMILL.asItem(), RecipeTypes.SawmillRecipe.class);
        this.backGround = helper.createDrawable(new ResourceLocation("pixellib:textures/gui/container/sawmill.png"), 55, 16, 82, 54);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, MachineRecipe recipe, IIngredients ingredients) {
        List<List<ItemStack>> inputs = recipe.getInputsAsList();
        List<ItemStack> outputs = recipe.getOutputsAsList();

        layout.getItemStacks().init(0, true, 0, 18);
        layout.getItemStacks().set(0, inputs.get(0));

        layout.getItemStacks().init(1, false, 60, 18);
        layout.getItemStacks().set(1, outputs.get(0));
    }

    @Override
    public void draw(MachineRecipe recipe, double mouseX, double mouseY) {
        arrow.draw(24, 18);
        if (((RecipeTypes.SawmillRecipe) recipe).isHighSignal())
            Minecraft.getInstance().fontRenderer.drawString("Requires a high signal", -12, 4, 0x000000);
        else
            Minecraft.getInstance().fontRenderer.drawString("Requires a low signal", -10, 4, 0x000000);
    }
}