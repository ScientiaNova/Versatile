package com.scientianovateam.versatile.recipes.builders

import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.components.energy.consumption.EnergyConsumptionHandler
import com.scientianovateam.versatile.recipes.components.energy.generation.EnergyGenerationHandler
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.input.FluidInputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.output.FluidOutputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.items.input.ItemInputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.items.output.ItemOutputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.IRecipeStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.chanced
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids.RecipeFluidStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids.RecipeFluidTagStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemTagStack
import com.scientianovateam.versatile.recipes.components.ingredients.utility.TagStack
import com.scientianovateam.versatile.recipes.components.time.TimeHandler
import com.scientianovateam.versatile.recipes.lists.IRecipeLIst
import com.scientianovateam.versatile.recipes.lists.machines.StandardRecipeList
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidStack

open class DSLRecipeBuilder(val recipeList: IRecipeLIst, val name: ResourceLocation) {
    protected val handlers = mutableListOf<IRecipeComponentHandler<*>>()

    var time = 0
        set(value) {
            handlers += TimeHandler(value)
            field = value
        }

    var energyConsumedPerTick = 0
        set(value) {
            handlers += EnergyConsumptionHandler(value)
            field = value
        }

    var energyGeneratedPerTick = 0
        set(value) {
            handlers += EnergyGenerationHandler(value)
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

    open fun build() = Recipe(recipeList, name, handlers)
}

fun StandardRecipeList.add(name: ResourceLocation, builder: DSLRecipeBuilder.() -> Unit) = DSLRecipeBuilder(this, name).apply(builder).build()

open class ItemInputsDSL {
    protected val stacks = mutableListOf<ChancedRecipeStack<ItemStack>>()

    operator fun ChancedRecipeStack<ItemStack>.unaryPlus() = this.apply { this@ItemInputsDSL.stacks += this }

    operator fun IRecipeStack<ItemStack>.unaryPlus() = this.apply { this@ItemInputsDSL.stacks += this.chanced() }

    operator fun ItemStack.unaryPlus() = this.apply { stacks += RecipeItemStack(this).chanced() }

    operator fun TagStack<Item>.unaryPlus() = this.apply { stacks += RecipeItemTagStack(this).chanced() }

    @JvmName("plusChancedRecipeStackList")
    operator fun List<ChancedRecipeStack<ItemStack>>.unaryPlus() = this.apply { stacks += this }

    @JvmName("plusRecipeStackList")
    operator fun List<IRecipeStack<ItemStack>>.unaryPlus() = this.apply { stacks += this.map { it.chanced() } }

    @JvmName("plusStackList")
    operator fun List<ItemStack>.unaryPlus() = this.apply { stacks += this.map { RecipeItemStack(it).chanced() } }

    @JvmName("plusTagStackList")
    operator fun List<TagStack<Item>>.unaryPlus() = this.apply { stacks += this.map { RecipeItemTagStack(it).chanced() } }

    @JvmName("plusRecipeStackPairList")
    operator fun List<Pair<IRecipeStack<ItemStack>, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(it.first, it.second) } }

    @JvmName("plusStackPairList")
    operator fun List<Pair<ItemStack, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(RecipeItemStack(it.first), it.second) } }

    @JvmName("plusTagStackPairList")
    operator fun List<Pair<TagStack<Item>, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(RecipeItemTagStack(it.first), it.second) } }

    open fun build() = ItemInputsHandler(stacks)
}

open class FluidInputsDSL {
    protected val stacks = mutableListOf<ChancedRecipeStack<FluidStack>>()

    operator fun ChancedRecipeStack<FluidStack>.unaryPlus() = this.apply { this@FluidInputsDSL.stacks += this }

    operator fun IRecipeStack<FluidStack>.unaryPlus() = this.apply { this@FluidInputsDSL.stacks += this.chanced() }

    operator fun FluidStack.unaryPlus() = this.apply { stacks += RecipeFluidStack(this).chanced() }

    operator fun TagStack<Fluid>.unaryPlus() = this.apply { stacks += RecipeFluidTagStack(this).chanced() }

    @JvmName("plusChancedRecipeStackList")
    operator fun List<ChancedRecipeStack<FluidStack>>.unaryPlus() = this.apply { stacks += this }

    @JvmName("plusRecipeStackList")
    operator fun List<IRecipeStack<FluidStack>>.unaryPlus() = this.apply { stacks += this.map { it.chanced() } }

    @JvmName("plusStackList")
    operator fun List<FluidStack>.unaryPlus() = this.apply { stacks += this.map { RecipeFluidStack(it).chanced() } }

    @JvmName("plusTagStackList")
    operator fun List<TagStack<Fluid>>.unaryPlus() = this.apply { stacks += this.map { RecipeFluidTagStack(it).chanced() } }

    @JvmName("plusRecipeStackPairList")
    operator fun List<Pair<IRecipeStack<FluidStack>, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(it.first, it.second) } }

    @JvmName("plusStackPairList")
    operator fun List<Pair<FluidStack, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(RecipeFluidStack(it.first), it.second) } }

    @JvmName("plusTagStackPairList")
    operator fun List<Pair<TagStack<Fluid>, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(RecipeFluidTagStack(it.first), it.second) } }

    open fun build() = FluidInputsHandler(stacks)
}

open class ItemOutputsDSL {
    protected val stacks = mutableListOf<ChancedRecipeStack<ItemStack>>()

    operator fun ChancedRecipeStack<ItemStack>.unaryPlus() = this.apply { this@ItemOutputsDSL.stacks += this }

    operator fun IRecipeStack<ItemStack>.unaryPlus() = this.apply { this@ItemOutputsDSL.stacks += this.chanced() }

    operator fun ItemStack.unaryPlus() = this.apply { stacks += RecipeItemStack(this).chanced() }

    operator fun TagStack<Item>.unaryPlus() = this.apply { stacks += RecipeItemTagStack(this).chanced() }

    @JvmName("plusChancedRecipeStackList")
    operator fun List<ChancedRecipeStack<ItemStack>>.unaryPlus() = this.apply { stacks += this }

    @JvmName("plusRecipeStackList")
    operator fun List<IRecipeStack<ItemStack>>.unaryPlus() = this.apply { stacks += this.map { it.chanced() } }

    @JvmName("plusStackList")
    operator fun List<ItemStack>.unaryPlus() = this.apply { stacks += this.map { RecipeItemStack(it).chanced() } }

    @JvmName("plusTagStackList")
    operator fun List<TagStack<Item>>.unaryPlus() = this.apply { stacks += this.map { RecipeItemTagStack(it).chanced() } }

    @JvmName("plusRecipeStackPairList")
    operator fun List<Pair<IRecipeStack<ItemStack>, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(it.first, it.second) } }

    @JvmName("plusStackPairList")
    operator fun List<Pair<ItemStack, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(RecipeItemStack(it.first), it.second) } }

    @JvmName("plusTagStackPairList")
    operator fun List<Pair<TagStack<Item>, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(RecipeItemTagStack(it.first), it.second) } }

    open fun build() = ItemOutputsHandler(stacks)
}

open class FluidOutputsDSL {
    protected val stacks = mutableListOf<ChancedRecipeStack<FluidStack>>()

    operator fun ChancedRecipeStack<FluidStack>.unaryPlus() = this.apply { this@FluidOutputsDSL.stacks += this }

    operator fun IRecipeStack<FluidStack>.unaryPlus() = this.apply { this@FluidOutputsDSL.stacks += this.chanced() }

    operator fun FluidStack.unaryPlus() = this.apply { stacks += RecipeFluidStack(this).chanced() }

    operator fun TagStack<Fluid>.unaryPlus() = this.apply { stacks += RecipeFluidTagStack(this).chanced() }

    @JvmName("plusChancedRecipeStackList")
    operator fun List<ChancedRecipeStack<FluidStack>>.unaryPlus() = this.apply { stacks += this }

    @JvmName("plusRecipeStackList")
    operator fun List<IRecipeStack<FluidStack>>.unaryPlus() = this.apply { stacks += this.map { it.chanced() } }

    @JvmName("plusStackList")
    operator fun List<FluidStack>.unaryPlus() = this.apply { stacks += this.map { RecipeFluidStack(it).chanced() } }

    @JvmName("plusTagStackList")
    operator fun List<TagStack<Fluid>>.unaryPlus() = this.apply { stacks += this.map { RecipeFluidTagStack(it).chanced() } }

    @JvmName("plusRecipeStackPairList")
    operator fun List<Pair<IRecipeStack<FluidStack>, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(it.first, it.second) } }

    @JvmName("plusStackPairList")
    operator fun List<Pair<FluidStack, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(RecipeFluidStack(it.first), it.second) } }

    @JvmName("plusTagStackPairList")
    operator fun List<Pair<TagStack<Fluid>, Float>>.unaryPlus() = this.apply { stacks += this.map { ChancedRecipeStack(RecipeFluidTagStack(it.first), it.second) } }

    open fun build() = FluidOutputsHandler(stacks)
}