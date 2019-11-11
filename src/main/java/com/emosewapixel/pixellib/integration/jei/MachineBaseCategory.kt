package com.emosewapixel.pixellib.integration.jei

import com.emosewapixel.pixellib.machines.recipes.AbstractRecipeList
import com.emosewapixel.pixellib.machines.recipes.SimpleMachineRecipe
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import java.lang.reflect.ParameterizedType

open class MachineBaseCategory(helper: IGuiHelper, protected var recipeList: AbstractRecipeList<*, *>) : IRecipeCategory<SimpleMachineRecipe> {
    open val backGround: IDrawable = helper.createBlankDrawable(0, 0)

    private val icon = recipeList.blocksImplementing.firstOrNull()?.let(helper::createDrawableIngredient)

    override fun getUid() = recipeList.name

    override fun getTitle() = recipeList.localizedName.toString()

    override fun getBackground() = backGround

    override fun getIcon() = icon

    override fun setIngredients(recipe: SimpleMachineRecipe, ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.inputStackLists)
        ingredients.setInputLists(VanillaTypes.FLUID, recipe.fluidInputStackLists)
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.outputStacks)
        ingredients.setOutputs(VanillaTypes.FLUID, recipe.fluidOutputStacks)
    }

    override fun setRecipe(layout: IRecipeLayout, recipe: SimpleMachineRecipe, ingredients: IIngredients) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Suppress("UNCHECKED_CAST")
    override fun getRecipeClass() = (recipeList.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<out SimpleMachineRecipe>
}