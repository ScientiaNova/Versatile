package com.emosewapixel.pixellib.machines.recipes.builders

import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidInputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidOutputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemOutputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.*
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.TagStack
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.WeightedMap
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.weightedMapOf
import com.emosewapixel.pixellib.machines.recipes.components.stats.EnergyPerTickHandler
import com.emosewapixel.pixellib.machines.recipes.components.stats.TimeHandler
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

open class DSLRecipeBuilder(val recipeList: RecipeList, val name: String) {
    protected val handlers = mutableListOf<IRecipeComponentHandler<*>>()

    var time = 0
        set(value) {
            handlers += TimeHandler(value)
            field = value
        }

    var energyPerTick = 0
        set(value) {
            handlers += EnergyPerTickHandler(value)
            field = value
        }

    fun itemInputs(builder: ItemInputsDSL.() -> Unit) {
        handlers += ItemInputsDSL().apply(builder).build()
    }

    fun fluidInputs(builder: FluidInputsDSL.() -> Unit) {
        handlers += FluidInputsDSL().apply(builder).build()
    }

    fun itemOutputs(builder: ItemOutputsDSL.() -> Unit) {
        handlers += ItemOutputsDSL().apply(builder).build()
    }

    fun fluidOutputs(builder: FluidOutputsDSL.() -> Unit) {
        handlers += FluidOutputsDSL().apply(builder).build()
    }

    open fun build() = Recipe(recipeList, name, *handlers.toTypedArray())
}

fun RecipeList.add(name: String, builder: DSLRecipeBuilder.() -> Unit) = DSLRecipeBuilder(this, name).apply(builder).build()

open class ItemInputsDSL {
    protected val stacks = mutableListOf<Pair<IRecipeStack<ItemStack>, Float>>()

    operator fun IRecipeStack<ItemStack>.unaryPlus() = this.apply { this@ItemInputsDSL.stacks += this to 1f }

    operator fun ItemStack.unaryPlus() = this.apply { stacks += RecipeItemStack(this) to 1f }

    operator fun TagStack<Item>.unaryPlus() = this.apply { stacks += RecipeItemTagStack(this) to 1f }

    @JvmName("plusRecipeStackList")
    operator fun List<IRecipeStack<ItemStack>>.unaryPlus() = this.apply { stacks += this.map { it to 1f } }

    @JvmName("plusStackList")
    operator fun List<ItemStack>.unaryPlus() = this.apply { stacks += this.map { RecipeItemStack(it) to 1f } }

    @JvmName("plusTagStackList")
    operator fun List<TagStack<Item>>.unaryPlus() = this.apply { stacks += this.map { RecipeItemTagStack(it) to 1f } }

    @JvmName("plusRecipeStackPairList")
    operator fun List<Pair<IRecipeStack<ItemStack>, Float>>.unaryPlus() = this.apply { stacks += this }

    @JvmName("plusStackPairList")
    operator fun List<Pair<ItemStack, Float>>.unaryPlus() = this.apply { stacks += this.map { RecipeItemStack(it.first) to it.second } }

    @JvmName("plusTagStackPairList")
    operator fun List<Pair<TagStack<Item>, Float>>.unaryPlus() = this.apply { stacks += this.map { RecipeItemTagStack(it.first) to it.second } }

    open fun build() = ItemInputsHandler(stacks)
}

open class FluidInputsDSL {
    protected val stacks = mutableListOf<Pair<IRecipeStack<FluidStack>, Float>>()

    operator fun IRecipeStack<FluidStack>.unaryPlus() = this.apply { this@FluidInputsDSL.stacks += this to 1f }

    operator fun FluidStack.unaryPlus() = this.apply { stacks += RecipeFluidStack(this) to 1f }

    operator fun TagStack<Fluid>.unaryPlus() = this.apply { stacks += RecipeFluidTagStack(this) to 1f }

    @JvmName("plusRecipeStackList")
    operator fun List<IRecipeStack<FluidStack>>.unaryPlus() = this.apply { stacks += this.map { it to 1f } }

    @JvmName("plusStackList")
    operator fun List<FluidStack>.unaryPlus() = this.apply { stacks += this.map { RecipeFluidStack(it) to 1f } }

    @JvmName("plusTagStackList")
    operator fun List<TagStack<Fluid>>.unaryPlus() = this.apply { stacks += this.map { RecipeFluidTagStack(it) to 1f } }

    @JvmName("plusRecipeStackPairList")
    operator fun List<Pair<IRecipeStack<FluidStack>, Float>>.unaryPlus() = this.apply { stacks += this }

    @JvmName("plusStackPairList")
    operator fun List<Pair<FluidStack, Float>>.unaryPlus() = this.apply { stacks += this.map { RecipeFluidStack(it.first) to it.second } }

    @JvmName("plusTagStackPairList")
    operator fun List<Pair<TagStack<Fluid>, Float>>.unaryPlus() = this.apply { stacks += this.map { RecipeFluidTagStack(it.first) to it.second } }

    open fun build() = FluidInputsHandler(stacks)
}

open class ItemOutputsDSL {
    protected val maps = mutableListOf<WeightedMap<out IRecipeStack<ItemStack>>>()

    operator fun IRecipeStack<ItemStack>.unaryPlus() = this.apply { maps += weightedMapOf(1 to this) }

    operator fun ItemStack.unaryPlus() = this.apply { maps += weightedMapOf(1 to RecipeItemStack(this)) }

    operator fun TagStack<Item>.unaryPlus() = this.apply { maps += weightedMapOf(1 to RecipeItemTagStack(this)) }

    @JvmName("plusRecipeStackList")
    operator fun List<IRecipeStack<ItemStack>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(1 to it) } }

    @JvmName("plusStackList")
    operator fun List<ItemStack>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(1 to RecipeItemStack(it)) } }

    @JvmName("plusTagStackList")
    operator fun List<TagStack<Item>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(1 to RecipeItemTagStack(it)) } }

    @JvmName("plusRecipeStackPairList")
    operator fun List<Pair<IRecipeStack<ItemStack>, Float>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(it.second to it.first, 1 - it.second to RecipeItemStack.EMPTY) } }

    @JvmName("plusStackPairList")
    operator fun List<Pair<ItemStack, Float>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(it.second to RecipeItemStack(it.first), 1 - it.second to RecipeItemStack.EMPTY) } }

    @JvmName("plusTagStackPairList")
    operator fun List<Pair<TagStack<Item>, Float>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(it.second to RecipeItemTagStack(it.first), 1 - it.second to RecipeItemStack.EMPTY) } }

    @JvmName("plusRecipeStackMap")
    operator fun WeightedMap<out IRecipeStack<ItemStack>>.unaryPlus() = this.apply { maps += this }

    @JvmName("plusStackMap")
    operator fun WeightedMap<ItemStack>.unaryPlus() = this.apply { maps += weightedMapOf(*this.weightedEntries.map { it.first to RecipeItemStack(it.second) }.toTypedArray()) }

    @JvmName("plusTagStackMap")
    operator fun WeightedMap<TagStack<Item>>.unaryPlus() = this.apply { maps += weightedMapOf(*this.weightedEntries.map { it.first to RecipeItemTagStack(it.second) }.toTypedArray()) }

    open fun build() = ItemOutputsHandler(maps)
}

open class FluidOutputsDSL {
    protected val maps = mutableListOf<WeightedMap<out IRecipeStack<FluidStack>>>()

    operator fun IRecipeStack<FluidStack>.unaryPlus() = this.apply { maps += weightedMapOf(1 to this) }

    operator fun FluidStack.unaryPlus() = this.apply { maps += weightedMapOf(1 to RecipeFluidStack(this)) }

    operator fun TagStack<Fluid>.unaryPlus() = this.apply { maps += weightedMapOf(1 to RecipeFluidTagStack(this)) }

    @JvmName("plusRecipeStackList")
    operator fun List<IRecipeStack<FluidStack>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(1 to it) } }

    @JvmName("plusStackList")
    operator fun List<FluidStack>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(1 to RecipeFluidStack(it)) } }

    @JvmName("plusTagStackList")
    operator fun List<TagStack<Fluid>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(1 to RecipeFluidTagStack(it)) } }

    @JvmName("plusRecipeStackPairList")
    operator fun List<Pair<IRecipeStack<FluidStack>, Float>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(it.second to it.first, 1 - it.second to RecipeFluidStack.EMPTY) } }

    @JvmName("plusStackPairList")
    operator fun List<Pair<FluidStack, Float>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(it.second to RecipeFluidStack(it.first), 1 - it.second to RecipeFluidStack.EMPTY) } }

    @JvmName("plusTagStackPairList")
    operator fun List<Pair<TagStack<Fluid>, Float>>.unaryPlus() = this.apply { maps += this.map { weightedMapOf(it.second to RecipeFluidTagStack(it.first), 1 - it.second to RecipeFluidStack.EMPTY) } }

    @JvmName("plusRecipeStackMap")
    operator fun WeightedMap<out IRecipeStack<FluidStack>>.unaryPlus() = this.apply { maps += this }

    @JvmName("plusStackMap")
    operator fun WeightedMap<FluidStack>.unaryPlus() = this.apply { maps += weightedMapOf(*this.weightedEntries.map { it.first to RecipeFluidStack(it.second) }.toTypedArray()) }

    @JvmName("plusTagStackMap")
    operator fun WeightedMap<TagStack<Fluid>>.unaryPlus() = this.apply { maps += weightedMapOf(*this.weightedEntries.map { it.first to RecipeFluidTagStack(it.second) }.toTypedArray()) }

    open fun build() = FluidOutputsHandler(maps)
}