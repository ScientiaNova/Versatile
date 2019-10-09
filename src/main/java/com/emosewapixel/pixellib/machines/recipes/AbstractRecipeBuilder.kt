package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.machines.recipes.utility.TagStack
import com.emosewapixel.pixellib.machines.recipes.utility.WeightedMap
import com.emosewapixel.pixellib.machines.recipes.utility.plusAssign
import com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents.*
import com.emosewapixel.pixellib.machines.recipes.utility.weightedMapOf
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

//Recipe Builders are builders used for easily creating Machine Recipes
abstract class AbstractRecipeBuilder<T : SimpleMachineRecipe, R : AbstractRecipeBuilder<T, R>>(protected val recipeList: AbstractRecipeList<T, R>) {
    val inputs = mutableListOf<Pair<IRecipeStack<ItemStack>, Float>>()
    val fluidInputs = mutableListOf<Pair<IRecipeStack<FluidStack>, Float>>()
    val outputs = mutableListOf<WeightedMap<IRecipeStack<ItemStack>>>()
    val fluidOutputs = mutableListOf<WeightedMap<IRecipeStack<FluidStack>>>()
    var time = 0

    fun input(vararg inputs: IRecipeStack<ItemStack>): R {
        this.inputs += (listOf(*inputs)).map { it to 1f }
        return this as R
    }

    fun input(vararg inputs: ItemStack) = input(*inputs.map(::RecipeItemStack).toTypedArray())

    fun input(vararg inputs: TagStack<Item>) = input(*inputs.map(::RecipeItemTagStack).toTypedArray())

    fun potentiallyConsumable(input: IRecipeStack<ItemStack>, consumeChance: Float): R {
        this.inputs += input to consumeChance
        return this as R
    }

    fun potentiallyConsumable(input: ItemStack, consumeChance: Float) = potentiallyConsumable(RecipeItemStack(input), consumeChance)

    fun potentiallyConsumable(input: TagStack<Item>, consumeChance: Float) = potentiallyConsumable(RecipeItemTagStack(input), consumeChance)

    @JvmName("fluidInput")
    fun input(vararg inputs: IRecipeStack<FluidStack>): R {
        this.fluidInputs += (listOf(*inputs)).map { it to 1f }
        return this as R
    }

    @JvmName("fluidInput")
    fun input(vararg inputs: FluidStack) = input(*inputs.map(::RecipeFluidStack).toTypedArray())

    @JvmName("fluidInput")
    fun input(vararg inputs: TagStack<Fluid>) = input(*inputs.map(::RecipeFluidTagStack).toTypedArray())

    @JvmName("potentiallyConsumableFluid")
    fun potentiallyConsumable(input: IRecipeStack<FluidStack>, consumeChance: Float): R {
        this.fluidInputs += input to consumeChance
        return this as R
    }

    @JvmName("potentiallyConsumableFluid")
    fun potentiallyConsumable(input: FluidStack, consumeChance: Float) = potentiallyConsumable(RecipeFluidStack(input), consumeChance)

    @JvmName("potentiallyConsumableFluid")
    fun potentiallyConsumable(input: TagStack<Fluid>, consumeChance: Float) = potentiallyConsumable(RecipeFluidTagStack(input), consumeChance)

    fun output(vararg outputs: IRecipeStack<ItemStack>): R {
        this.outputs += (listOf(*outputs))
        return this as R
    }

    fun output(vararg outputs: ItemStack) = output(*outputs.map(::RecipeItemStack).toTypedArray())

    fun output(vararg outputs: TagStack<Item>) = output(*outputs.map(::RecipeItemTagStack).toTypedArray())

    fun chancedOutput(output: IRecipeStack<ItemStack>, chance: Float): R {
        this.outputs += weightedMapOf(chance to output, 1 - chance to RecipeItemStack.EMPTY)
        return this as R
    }

    fun chancedOutput(output: ItemStack, chance: Float) = chancedOutput(RecipeItemStack(output), chance)

    fun chancedOutput(output: TagStack<Item>, chance: Float) = chancedOutput(RecipeItemTagStack(output), chance)

    fun weightedOutput(vararg entries: Pair<Int, IRecipeStack<ItemStack>>): R {
        this.outputs += weightedMapOf(*entries)
        return this as R
    }

    @JvmName("weightedStackOutput")
    fun weightedOutput(vararg entries: Pair<Int, ItemStack>) = weightedOutput(*entries.map { Pair(it.first, RecipeItemStack(it.second)) }.toTypedArray())

    @JvmName("weightedTagOutput")
    fun weightedOutput(vararg entries: Pair<Int, TagStack<Item>>) = weightedOutput(*entries.map { Pair(it.first, RecipeItemTagStack(it.second)) }.toTypedArray())

    @JvmName("fluidOutput")
    fun output(vararg outputs: IRecipeStack<FluidStack>): R {
        this.fluidOutputs += (listOf(*outputs))
        return this as R
    }

    @JvmName("fluidOutput")
    fun output(vararg outputs: FluidStack) = output(*outputs.map(::RecipeFluidStack).toTypedArray())

    @JvmName("fluidOutput")
    fun output(vararg outputs: TagStack<Fluid>) = output(*outputs.map(::RecipeFluidTagStack).toTypedArray())

    @JvmName("fluidChancedOutput")
    fun chancedOutput(output: IRecipeStack<FluidStack>, chance: Float): R {
        this.fluidOutputs += weightedMapOf(chance to output, 1 - chance to RecipeFluidStack.EMPTY)
        return this as R
    }

    @JvmName("fluidChancedOutput")
    fun chancedOutput(output: FluidStack, chance: Float) = chancedOutput(RecipeFluidStack(output), chance)

    @JvmName("fluidChancedOutput")
    fun chancedOutput(output: TagStack<Fluid>, chance: Float) = chancedOutput(RecipeFluidTagStack(output), chance)

    @JvmName("weightedFluidOutput")
    fun weightedOutput(vararg entries: Pair<Int, IRecipeStack<FluidStack>>): R {
        this.fluidOutputs += weightedMapOf(*entries)
        return this as R
    }

    @JvmName("weightedFluidStackOutput")
    fun weightedOutput(vararg entries: Pair<Int, FluidStack>) = weightedOutput(*entries.map { Pair(it.first, RecipeFluidStack(it.second)) }.toTypedArray())

    @JvmName("weightedFluidTagOutput")
    fun weightedOutput(vararg entries: Pair<Int, TagStack<Fluid>>) = weightedOutput(*entries.map { Pair(it.first, RecipeFluidTagStack(it.second)) }.toTypedArray())


    fun time(amount: Int): R {
        time = amount
        return this as R
    }

    protected abstract fun build(): T

    fun buildAndRegister() {
        if (!build().isEmpty)
            recipeList.add(build())
        else
            PixelLib.LOGGER.error("Recipe with output {} is empty", outputs)
    }
}
