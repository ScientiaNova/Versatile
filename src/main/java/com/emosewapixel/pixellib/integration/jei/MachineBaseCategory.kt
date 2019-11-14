package com.emosewapixel.pixellib.integration.jei

import com.emosewapixel.pixellib.extensions.isNotEmpty
import com.emosewapixel.pixellib.machines.gui.layout.components.FluidSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.ItemSlotComponent
import com.emosewapixel.pixellib.machines.recipes.AbstractRecipeList
import com.emosewapixel.pixellib.machines.recipes.SimpleMachineRecipe
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.FluidStack
import java.lang.reflect.ParameterizedType

open class MachineBaseCategory(helper: IGuiHelper, protected var recipeList: AbstractRecipeList<*, *>) : IRecipeCategory<SimpleMachineRecipe> {
    private val background: IDrawable = helper.createBlankDrawable(0, 0)

    val page = recipeList.createPage()

    private val icon = recipeList.blocksImplementing.firstOrNull()?.let(helper::createDrawableIngredient)

    override fun getUid() = recipeList.name

    override fun getTitle() = recipeList.localizedName.toString()

    override fun getBackground() = background

    override fun getIcon() = icon

    override fun setIngredients(recipe: SimpleMachineRecipe, ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.inputs.map { pair -> pair.first.stacks.filter(ItemStack::isNotEmpty).map { it.apply { orCreateTag.putFloat("consume_chance", pair.second) } } })
        ingredients.setInputLists(VanillaTypes.FLUID, recipe.fluidInputs.map { pair -> pair.first.stacks.filter(FluidStack::isNotEmpty).map { it.apply { orCreateTag.putFloat("consume_chance", pair.second) } } })
        ingredients.setOutputLists(VanillaTypes.ITEM, recipe.outputs.map { map ->
            map.map {
                it.value.stacks.firstOrNull()?.apply { if (isNotEmpty) orCreateTag.putDouble("output_chance", it.key / map.maxWeight.toDouble()) }
                        ?: ItemStack.EMPTY
            }.filter(ItemStack::isNotEmpty)
        })
        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.fluidOutputs.map { map ->
            map.map {
                it.value.stacks.firstOrNull()?.apply { if (isNotEmpty) orCreateTag.putDouble("output_chance", it.key / map.maxWeight.toDouble()) }
                        ?: FluidStack.EMPTY
            }.filter(FluidStack::isNotEmpty)
        })
    }

    override fun setRecipe(layout: IRecipeLayout, recipe: SimpleMachineRecipe, ingredients: IIngredients) {
        val guiItemStacks = layout.itemStacks
        val guiFluidStacks = layout.fluidStacks

        page.components.forEach {
            if (it is ItemSlotComponent)
                guiItemStacks.init(it.slotIndex, it.slotIndex < recipeList.maxInputs, it.x + (it.width - 16) / 2, it.y + (it.height - 16) / 2)

            if (it is FluidSlotComponent)
                guiFluidStacks.init(it.tankId, it.tankId < recipeList.maxFluidInputs, GUIFluidRenderer(), it.x + (it.width - 16) / 2, it.y + (it.height - 16) / 2, 16, 16, 0, 0)
        }

        guiItemStacks.addTooltipCallback { _, input, stack, tooltips ->
            if (input) {
                val consumeChance = stack.orCreateTag.getFloat("consume_chance")
                if (consumeChance <= 0) tooltips += TranslationTextComponent("recipe_stack.not_consumed").formattedText
                else if (consumeChance < 1) tooltips += TranslationTextComponent("recipe_stack.consume_chance", consumeChance * 100).formattedText
            } else {
                val outputChance = stack.orCreateTag.getFloat("output_chance")
                if (outputChance < 1) tooltips += TranslationTextComponent("recipe_stack.output_chance", outputChance * 100).formattedText
            }
        }
        guiFluidStacks.addTooltipCallback { _, input, stack, tooltips ->
            if (input) {
                val consumeChance = stack.orCreateTag.getFloat("consume_chance")
                if (consumeChance <= 0) tooltips += TranslationTextComponent("recipe_stack.not_consumed").formattedText
                else if (consumeChance < 1) tooltips += TranslationTextComponent("recipe_stack.consume_chance", consumeChance * 100).formattedText
            } else {
                val outputChance = stack.orCreateTag.getFloat("output_chance")
                if (outputChance < 1) tooltips += TranslationTextComponent("recipe_stack.output_chance", outputChance * 100).formattedText
            }
        }
    }

    override fun draw(recipe: SimpleMachineRecipe, mouseX: Double, mouseY: Double) {
        page.components.forEach { it.drawInBackground(mouseX, mouseY, 0, 0) }
        page.components.forEach { it.drawInForeground(mouseX, mouseY, 0, 0) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getRecipeClass() = (recipeList.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<out SimpleMachineRecipe>
}