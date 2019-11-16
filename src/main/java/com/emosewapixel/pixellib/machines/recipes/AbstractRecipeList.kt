package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.extensions.shorten
import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import com.emosewapixel.pixellib.machines.gui.layout.GUIPage
import com.emosewapixel.pixellib.machines.gui.textures.ProgressBar
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.emosewapixel.pixellib.machines.properties.implementations.FluidInventoryProperty
import com.emosewapixel.pixellib.machines.properties.implementations.IncrementingDoubleProperty
import com.emosewapixel.pixellib.machines.properties.implementations.ItemInventoryProperty
import com.emosewapixel.pixellib.machines.recipes.utility.MachineInput
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.FluidStack
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.sqrt

/*
Recipe Lists are objects used for storing recipes with a maximum amount of inputs and outputs.
Recipe Lists are intertwined with Recipe Builders and the Machine Recipes themselves
*/
abstract class AbstractRecipeList<T : SimpleMachineRecipe, B : AbstractRecipeBuilder<T, B>>(
        val name: ResourceLocation,
        val maxInputs: Int,
        val maxFluidInputs: Int,
        val maxOutputs: Int,
        val maxFluidOutputs: Int,
        val progressBar: ProgressBar
) {
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

    open val extraTextRows = 1

    open fun createPage(items: ItemInventoryProperty = ItemInventoryProperty(ImprovedItemStackHandler(maxInputs, maxOutputs)), fluids: FluidInventoryProperty = FluidInventoryProperty(FluidStackHandler(maxFluidInputs, maxFluidOutputs)), progressUpdater: IValueProperty<Double> = IncrementingDoubleProperty()): GUIPage {
        val totalInputs = maxInputs + maxFluidInputs
        val inputColumns = ceil(sqrt(totalInputs.toDouble())).toInt()
        val inputRows = ceil(totalInputs / inputColumns.toDouble()).toInt()

        val totalOutputs = maxOutputs + maxFluidOutputs
        val outputColumns = ceil(sqrt(totalOutputs.toDouble())).toInt()
        val outputRows = ceil(totalOutputs / outputColumns.toDouble()).toInt()

        val inputXStart = 0
        val progressBarX = inputColumns * 18 + 10
        val outputXStart = progressBarX + 32

        val inputYStart = if (inputRows < outputRows) (outputRows - inputRows) * 9 else 0
        val progressBarY = (if (inputRows > outputRows) inputRows else outputRows) * 9 - 8
        val outputYStart = if (outputRows < inputRows) (inputRows - outputRows) * 9 else 0

        return GUIPage(minHeight = max(inputRows, outputRows) * 18 + extraTextRows * 10) {
            for (inputIndex in 0 until totalInputs) {
                val x = inputXStart + inputIndex % inputColumns * 18
                val y = inputYStart + inputIndex / inputColumns * 18
                if (inputIndex < maxInputs)
                    itemSlot(items, x, y) {
                        slotIndex = inputIndex
                    }
                else
                    fluidSlot(fluids, x, y) {
                        tankId = inputIndex - maxInputs
                    }
            }

            progressBar(progressUpdater) {
                bar = progressBar
                x = progressBarX
                y = progressBarY
            }

            for (outputIndex in 0 until totalOutputs) {
                val x = outputXStart + outputIndex % outputColumns * 18
                val y = outputYStart + outputIndex / outputColumns * 18
                if (outputIndex < maxOutputs)
                    itemSlot(items, x, y) {
                        slotIndex = outputIndex + maxInputs
                    }
                else
                    fluidSlot(fluids, x, y) {
                        tankId = outputIndex + maxFluidInputs - maxOutputs
                    }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    open fun renderExtraInfo(page: GUIPage, recipe: T) {
        val time = TranslationTextComponent("extra_recipe_info.duration", (recipe.time / 20.0).shorten()).string
        val fontRenderer = Minecraft.getInstance().fontRenderer
        fontRenderer.drawString(time, 0f, page.height - 8f, 0x404040)
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