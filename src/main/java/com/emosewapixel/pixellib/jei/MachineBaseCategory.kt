package com.emosewapixel.pixellib.jei

import com.emosewapixel.pixellib.machines.recipes.AbstractRecipeList
import com.emosewapixel.pixellib.machines.recipes.SimpleMachineRecipe
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.drawable.IDrawableAnimated
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import mezz.jei.config.Constants
import mezz.jei.util.Translator
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import java.lang.reflect.ParameterizedType

abstract class MachineBaseCategory(helper: IGuiHelper, icon: Item, protected var list: AbstractRecipeList<*, *>) : IRecipeCategory<SimpleMachineRecipe> {
    abstract var backGround: IDrawable

    private val icon: IDrawable = helper.createDrawableIngredient(ItemStack(icon))

    protected var arrow: IDrawableAnimated = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17)
            .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false)

    protected var flame: IDrawableAnimated = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14)
            .buildAnimated(300, IDrawableAnimated.StartDirection.TOP, true)

    override fun getUid() = list.name

    override fun getTitle() = Translator.translateToLocal("gui.jei.category." + list.name.toString())

    override fun getBackground() = backGround

    override fun getIcon() = icon

    override fun setIngredients(recipe: SimpleMachineRecipe, ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.inputStackLists)
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.outputStacks)
    }

    override fun getRecipeClass() = (list.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<out SimpleMachineRecipe>
}