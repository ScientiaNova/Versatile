package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import com.emosewapixel.pixellib.machines.gui.layout.GUIPage
import com.emosewapixel.pixellib.machines.properties.implementations.FluidInventoryProperty
import com.emosewapixel.pixellib.machines.properties.implementations.ItemInventoryProperty
import com.emosewapixel.pixellib.machines.recipes.utility.MachineInput
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.FluidStack

/*
Recipe Lists are objects used for storing recipes with a maximum amount of inputs and outputs.
Recipe Lists are intertwined with Recipe Builders and the Machine Recipes themselves
*/
abstract class AbstractRecipeList<T : SimpleMachineRecipe, B : AbstractRecipeBuilder<T, B>>(val name: ResourceLocation, val maxInputs: Int, val maxFluidInputs: Int, val maxOutputs: Int, val maxFluidOutputs: Int) {
    val recipes = mutableListOf<T>()
    val blocksImplementing = mutableListOf<Block>()
    protected val inputMap = mutableMapOf<String, MutableSet<T>>()
    open val localizedName = TranslationTextComponent("recipe_list.$name")
    open val genJEIPage = true

    val maxRecipeSlots = maxInputs + maxOutputs

    val maxRecipeTanks = maxFluidInputs + maxFluidOutputs

    init {
        RecipeLists += this
    }

    open fun isInRecipe(stack: ItemStack) = itemKeys.flatMap { it(stack) }.any(inputMap::contains)

    open fun isInRecipe(stack: FluidStack) = fluidKeys.flatMap { it(stack) }.any(inputMap::contains)

    open fun add(recipe: T) {
        recipes.add(recipe)
        recipe.inputRecipeStacks.forEach {
            val key = it.toString()
            inputMap[key]?.add(recipe) ?: inputMap.put(key, mutableSetOf(recipe))
        }
    }

    open fun find(input: MachineInput): T? {
        val items = input.items.flatMap { item -> itemKeys.map { it(item) } }.map { strs ->
            strs.asSequence().map(inputMap::get).firstOrNull { it != null } ?: mutableSetOf()
        }
        val fluids = input.fluids.flatMap { fluid -> fluidKeys.map { it(fluid) } }.map { strs ->
            strs.asSequence().map(inputMap::get).firstOrNull { it != null } ?: mutableSetOf()
        }

        val intersection = items.firstOrNull() ?: fluids.first()
        items.forEach { intersection.retainAll(it) }
        fluids.forEach { intersection.retainAll(it) }

        return intersection.firstOrNull { it.matches(input) }
    }

    open fun remove(recipe: T) {
        recipes.remove(recipe)
        recipe.inputRecipeStacks.forEach {
            val list = inputMap[it.toString()]
            list?.remove(recipe)
            if (list?.isEmpty() == true)
                inputMap.remove(it.toString())
        }
    }

    open fun createPage(items: ItemInventoryProperty = ItemInventoryProperty(ImprovedItemStackHandler(maxInputs, maxOutputs)), fluids: FluidInventoryProperty = FluidInventoryProperty(FluidStackHandler(maxFluidInputs, maxFluidOutputs))) = GUIPage {

    }

    abstract fun recipeBuilder(): B
    abstract fun build(dsl: B.() -> Unit)

    companion object {
        @JvmField
        val itemKeys = mutableListOf<(ItemStack) -> Collection<String>>(
                { listOf("item:" + it.item.registryName) },
                { it.item.tags.map { rl -> "item_tag:$rl" } }
        )

        @JvmField
        val fluidKeys = mutableListOf<(FluidStack) -> Collection<String>>(
                { listOf("item:" + it.fluid.registryName) },
                { it.fluid.tags.map { rl -> "item_tag:$rl" } }
        )
    }
}