package com.emosewapixel.pixellib.integration.jei

import com.emosewapixel.pixellib.extensions.isNotEmpty
import com.emosewapixel.pixellib.extensions.shorten
import com.emosewapixel.pixellib.extensions.toStack
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

open class MachineBaseCategory<T : SimpleMachineRecipe>(helper: IGuiHelper, protected val recipeList: AbstractRecipeList<T, *>) : IRecipeCategory<T> {
    val page = recipeList.createPage()

    private val background: IDrawable = helper.createBlankDrawable(page.width, page.height)

    private val icon = recipeList.blocksImplementing.firstOrNull()?.let { helper.createDrawableIngredient(it.toStack()) }

    override fun getUid() = recipeList.name

    override fun getTitle(): String = recipeList.localizedName.string

    override fun getBackground() = background

    override fun getIcon() = icon

    override fun setIngredients(recipe: T, ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.inputs.map { pair ->
            pair.first.stacks.filter(ItemStack::isNotEmpty).map { it.apply { orCreateTag.putFloat("consume_chance", pair.second) } }
        })
        ingredients.setInputLists(VanillaTypes.FLUID, recipe.fluidInputs.map { pair ->
            pair.first.stacks.filter(FluidStack::isNotEmpty).map { it.apply { orCreateTag.putFloat("consume_chance", pair.second) } }
        })
        ingredients.setOutputLists(VanillaTypes.ITEM, recipe.outputs.map { map ->
            map.weightedEntries.map {
                it.second.stacks.firstOrNull()?.apply { if (isNotEmpty) orCreateTag.putDouble("output_chance", it.first / map.maxWeight.toDouble()) }
                        ?: ItemStack.EMPTY
            }.filter(ItemStack::isNotEmpty)
        })
        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.fluidOutputs.map { map ->
            map.weightedEntries.map {
                it.second.stacks.firstOrNull()?.apply { if (isNotEmpty) orCreateTag.putDouble("output_chance", it.first / map.maxWeight.toDouble()) }
                        ?: FluidStack.EMPTY
            }.filter(FluidStack::isNotEmpty)
        })
    }

    override fun setRecipe(layout: IRecipeLayout, recipe: T, ingredients: IIngredients) {
        val guiItemStacks = layout.itemStacks
        val guiFluidStacks = layout.fluidStacks
        val itemInputs = ingredients.getInputs(VanillaTypes.ITEM)
        val fluidInputs = ingredients.getInputs(VanillaTypes.FLUID)
        val itemOutputs = ingredients.getOutputs(VanillaTypes.ITEM)
        val fluidOutputs = ingredients.getOutputs(VanillaTypes.FLUID)

        page.components.forEach {
            if (it is ItemSlotComponent) {
                val isInput = it.slotIndex < recipeList.maxInputs
                guiItemStacks.init(it.slotIndex, isInput, it.x, it.y)
                (if (isInput) itemInputs.getOrNull(it.slotIndex) else itemOutputs.getOrNull(it.slotIndex - recipeList.maxInputs))?.let { ingredient ->
                    guiItemStacks.set(it.slotIndex, ingredient)
                }
            }
            if (it is FluidSlotComponent) {
                val isInput = it.tankId < recipeList.maxFluidInputs
                guiFluidStacks.init(it.tankId, isInput, GUIFluidRenderer(), it.x, it.y, 18, 18, 1, 1)
                (if (isInput) fluidInputs.getOrNull(it.tankId) else fluidOutputs.getOrNull(it.tankId - recipeList.maxFluidInputs))?.let { ingredient ->
                    guiFluidStacks.set(it.tankId, ingredient)
                }
            }
        }

        guiItemStacks.addTooltipCallback { _, input, stack, tooltips ->
            if (input) {
                val consumeChance = stack.orCreateTag.getFloat("consume_chance")
                if (consumeChance <= 0) tooltips += TranslationTextComponent("extra_recipe_info.not_consumed").string
                else if (consumeChance < 1) tooltips += TranslationTextComponent("extra_recipe_info.consume_chance", (consumeChance * 100).shorten()).string
            } else {
                val outputChance = stack.orCreateTag.getFloat("output_chance")
                if (outputChance < 1) tooltips += TranslationTextComponent("extra_recipe_info.output_chance", (outputChance * 100).shorten()).string
            }
        }
        guiFluidStacks.addTooltipCallback { _, input, stack, tooltips ->
            if (input) {
                val consumeChance = stack.orCreateTag.getFloat("consume_chance")
                if (consumeChance <= 0) tooltips += TranslationTextComponent("extra_recipe_info.not_consumed").string
                else if (consumeChance < 1) tooltips += TranslationTextComponent("extra_recipe_info.consume_chance", (consumeChance * 100).shorten()).string
            } else {
                val outputChance = stack.orCreateTag.getFloat("output_chance")
                if (outputChance < 1) tooltips += TranslationTextComponent("extra_recipe_info.output_chance", (outputChance * 100).shorten()).string
            }
        }
    }

    override fun draw(recipe: T, mouseX: Double, mouseY: Double) {
        page.components.forEach { it.drawInBackground(mouseX, mouseY, 0, 0) }
        recipeList.renderExtraInfo(page, recipe)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getRecipeClass() = (recipeList.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
}