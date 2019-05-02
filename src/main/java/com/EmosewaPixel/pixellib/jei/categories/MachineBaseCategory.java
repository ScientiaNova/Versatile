package com.EmosewaPixel.pixellib.jei.categories;

import com.EmosewaPixel.pixellib.PixelLib;
import com.EmosewaPixel.pixellib.recipes.MachineRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.config.Constants;
import mezz.jei.util.Translator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class MachineBaseCategory implements IRecipeCategory<MachineRecipe> {
    private String name;
    private IDrawable icon;
    protected IDrawable backGround;
    private Class<? extends MachineRecipe> recipeClass;
    protected IDrawableAnimated arrow;
    protected IDrawableAnimated flame;

    public MachineBaseCategory(IGuiHelper helper, String name, Item icon, Class<? extends MachineRecipe> recipeClass) {
        this.name = name;
        this.icon = helper.createDrawableIngredient(new ItemStack(icon));
        this.recipeClass = recipeClass;

        arrow = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);

        flame = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14)
                .buildAnimated(300, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(PixelLib.ModId, name);
    }

    @Override
    public Class<? extends MachineRecipe> getRecipeClass() {
        return recipeClass;
    }

    @Override
    public String getTitle() {
        return Translator.translateToLocal("gui.jei.category." + name);
    }

    @Override
    public IDrawable getBackground() {
        return backGround;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }


    @Override
    public void setIngredients(MachineRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getInputsAsList());
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputsAsList());
    }
}
