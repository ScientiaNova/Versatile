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
import com.emosewapixel.pixellib.machines.recipes.components.energy.EnergyConsumptionHandler
import com.emosewapixel.pixellib.machines.recipes.components.energy.EnergyGenerationHandler
import com.emosewapixel.pixellib.machines.recipes.components.stats.TimeHandler
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

object Recipe {
    fun builder(recipeList: RecipeList, name: String) = Builder(recipeList, name)

    open class Builder internal constructor(val recipeList: RecipeList, val name: String) {
        protected val handlers = mutableListOf<IRecipeComponentHandler<*>>()

        fun time(amount: Int): Builder {
            handlers += TimeHandler(amount)
            return this
        }

        fun energyConsumedPerTick(amount: Int): Builder {
            handlers += EnergyConsumptionHandler(amount)
            return this
        }

        fun energyGeneratedPerTick(amount: Int): Builder {
            handlers += EnergyGenerationHandler(amount)
            return this
        }

        fun itemInputs(builder: ItemInputs.Builder): Builder {
            handlers += builder.build()
            return this
        }

        fun fluidInputs(builder: FluidInputs.Builder): Builder {
            handlers += builder.build()
            return this
        }

        fun itemOutputs(builder: ItemOutputs.Builder): Builder {
            handlers += builder.build()
            return this
        }

        fun fluidOutputs(builder: FluidOutputs.Builder): Builder {
            handlers += builder.build()
            return this
        }

        fun build() = Recipe(recipeList, name, *handlers.toTypedArray())
    }
}

object ItemInputs {
    fun builder() = Builder()

    open class Builder {
        protected val stacks = mutableListOf<Pair<IRecipeStack<ItemStack>, Float>>()

        fun inputs(vararg stacks: IRecipeStack<ItemStack>): Builder {
            this.stacks += stacks.map { it to 1f }
            return this
        }

        fun inputs(vararg stacks: ItemStack): Builder {
            this.stacks += stacks.map { RecipeItemStack(it) to 1f }
            return this
        }

        fun inputs(vararg stacks: TagStack<Item>): Builder {
            this.stacks += stacks.map { RecipeItemTagStack(it) to 1f }
            return this
        }

        fun chancedInputs(stack: IRecipeStack<ItemStack>, consumeChance: Float): Builder {
            this.stacks += stack to consumeChance
            return this
        }

        fun chancedInputs(stack: ItemStack, consumeChance: Float): Builder {
            this.stacks += RecipeItemStack(stack) to consumeChance
            return this
        }

        fun chancedInputs(stack: TagStack<Item>, consumeChance: Float): Builder {
            this.stacks += RecipeItemTagStack(stack) to consumeChance
            return this
        }

        fun notConsumable(vararg stacks: IRecipeStack<ItemStack>): Builder {
            this.stacks += stacks.map { it to 0f }
            return this
        }

        fun notConsumable(vararg stacks: ItemStack): Builder {
            this.stacks += stacks.map { RecipeItemStack(it) to 0f }
            return this
        }

        fun notConsumable(vararg stacks: TagStack<Item>): Builder {
            this.stacks += stacks.map { RecipeItemTagStack(it) to 0f }
            return this
        }

        fun chancedInputs(vararg chancedStacks: Pair<IRecipeStack<ItemStack>, Float>): Builder {
            this.stacks += chancedStacks
            return this
        }

        fun chancedStacks(vararg chancedStacks: Pair<ItemStack, Float>): Builder {
            this.stacks += chancedStacks.map { RecipeItemStack(it.first) to it.second }
            return this
        }

        fun chancedTagStacks(vararg chancedStacks: Pair<TagStack<Item>, Float>): Builder {
            this.stacks += chancedStacks.map { RecipeItemTagStack(it.first) to it.second }
            return this
        }

        open fun build() = ItemInputsHandler(stacks.toList())
    }
}

object FluidInputs {
    fun builder() = Builder()

    open class Builder {
        protected val stacks = mutableListOf<Pair<IRecipeStack<FluidStack>, Float>>()

        fun inputs(vararg stacks: IRecipeStack<FluidStack>): Builder {
            this.stacks += stacks.map { it to 1f }
            return this
        }

        fun inputs(vararg stacks: FluidStack): Builder {
            this.stacks += stacks.map { RecipeFluidStack(it) to 1f }
            return this
        }

        fun inputs(vararg stacks: TagStack<Fluid>): Builder {
            this.stacks += stacks.map { RecipeFluidTagStack(it) to 1f }
            return this
        }

        fun chancedInput(stack: IRecipeStack<FluidStack>, consumeChance: Float): Builder {
            this.stacks += stack to consumeChance
            return this
        }

        fun chancedInput(stack: FluidStack, consumeChance: Float): Builder {
            this.stacks += RecipeFluidStack(stack) to consumeChance
            return this
        }

        fun chancedInput(stack: TagStack<Fluid>, consumeChance: Float): Builder {
            this.stacks += RecipeFluidTagStack(stack) to consumeChance
            return this
        }

        fun notConsumable(vararg stacks: IRecipeStack<FluidStack>): Builder {
            this.stacks += stacks.map { it to 0f }
            return this
        }

        fun notConsumable(vararg stacks: FluidStack): Builder {
            this.stacks += stacks.map { RecipeFluidStack(it) to 0f }
            return this
        }

        fun notConsumable(vararg stacks: TagStack<Fluid>): Builder {
            this.stacks += stacks.map { RecipeFluidTagStack(it) to 0f }
            return this
        }

        fun chancedInputs(vararg chancedStacks: Pair<IRecipeStack<FluidStack>, Float>): Builder {
            this.stacks += chancedStacks
            return this
        }

        fun chancedStacks(vararg chancedStacks: Pair<FluidStack, Float>): Builder {
            this.stacks += chancedStacks.map { RecipeFluidStack(it.first) to it.second }
            return this
        }

        fun chancedTagStacks(vararg chancedStacks: Pair<TagStack<Fluid>, Float>): Builder {
            this.stacks += chancedStacks.map { RecipeFluidTagStack(it.first) to it.second }
            return this
        }

        open fun build() = FluidInputsHandler(stacks.toList())
    }
}

object ItemOutputs {
    fun builder() = Builder()

    open class Builder {
        protected val maps = mutableListOf<WeightedMap<out IRecipeStack<ItemStack>>>()

        fun outputs(vararg stacks: IRecipeStack<ItemStack>): Builder {
            maps += stacks.map { weightedMapOf(1 to it) }
            return this
        }

        fun outputs(vararg stacks: ItemStack): Builder {
            maps += stacks.map { weightedMapOf(1 to RecipeItemStack(it)) }
            return this
        }

        fun outputs(vararg stacks: TagStack<Item>): Builder {
            maps += stacks.map { weightedMapOf(1 to RecipeItemTagStack(it)) }
            return this
        }

        fun chancedOutput(stack: IRecipeStack<ItemStack>, chance: Float): Builder {
            maps += weightedMapOf(chance to stack, 1 - chance to RecipeItemStack.EMPTY)
            return this
        }

        fun chancedOutput(stack: ItemStack, chance: Float): Builder {
            maps += weightedMapOf(chance to RecipeItemStack(stack), 1 - chance to RecipeItemStack.EMPTY)
            return this
        }

        fun chancedOutput(stack: TagStack<Item>, chance: Float): Builder {
            maps += weightedMapOf(chance to RecipeItemTagStack(stack), 1 - chance to RecipeItemStack.EMPTY)
            return this
        }

        fun chancedOutputs(vararg chancedStacks: Pair<IRecipeStack<ItemStack>, Float>): Builder {
            maps += chancedStacks.map { weightedMapOf(it.second to it.first, 1 - it.second to RecipeItemStack.EMPTY) }
            return this
        }

        fun chancedStacks(vararg chancedStacks: Pair<ItemStack, Float>): Builder {
            maps += chancedStacks.map { weightedMapOf(it.second to RecipeItemStack(it.first), 1 - it.second to RecipeItemStack.EMPTY) }
            return this
        }

        fun chancedTagStacks(vararg chancedStacks: Pair<TagStack<Item>, Float>): Builder {
            maps += chancedStacks.map { weightedMapOf(it.second to RecipeItemTagStack(it.first), 1 - it.second to RecipeItemStack.EMPTY) }
            return this
        }

        fun weightedOutput(vararg chancedStacks: Pair<Int, IRecipeStack<ItemStack>>): Builder {
            maps += weightedMapOf(*chancedStacks)
            return this
        }

        fun weightedStacks(vararg chancedStacks: Pair<Int, ItemStack>): Builder {
            maps += weightedMapOf(*chancedStacks.map { it.first to RecipeItemStack(it.second) }.toTypedArray())
            return this
        }

        fun weightedTagStacks(vararg chancedStacks: Pair<Int, TagStack<Item>>): Builder {
            maps += weightedMapOf(*chancedStacks.map { it.first to RecipeItemTagStack(it.second) }.toTypedArray())
            return this
        }

        fun build() = ItemOutputsHandler(maps)
    }
}

object FluidOutputs {
    fun builder() = Builder()

    open class Builder {
        protected val maps = mutableListOf<WeightedMap<out IRecipeStack<FluidStack>>>()

        fun outputs(vararg stacks: IRecipeStack<FluidStack>): Builder {
            maps += stacks.map { weightedMapOf(1 to it) }
            return this
        }

        fun outputs(vararg stacks: FluidStack): Builder {
            maps += stacks.map { weightedMapOf(1 to RecipeFluidStack(it)) }
            return this
        }

        fun outputs(vararg stacks: TagStack<Fluid>): Builder {
            maps += stacks.map { weightedMapOf(1 to RecipeFluidTagStack(it)) }
            return this
        }

        fun chancedOutput(stack: IRecipeStack<FluidStack>, chance: Float): Builder {
            maps += weightedMapOf(chance to stack, 1 - chance to RecipeFluidStack.EMPTY)
            return this
        }

        fun chancedOutput(stack: FluidStack, chance: Float): Builder {
            maps += weightedMapOf(chance to RecipeFluidStack(stack), 1 - chance to RecipeFluidStack.EMPTY)
            return this
        }

        fun chancedOutput(stack: TagStack<Fluid>, chance: Float): Builder {
            maps += weightedMapOf(chance to RecipeFluidTagStack(stack), 1 - chance to RecipeFluidStack.EMPTY)
            return this
        }

        fun chancedOutputs(vararg chancedStacks: Pair<IRecipeStack<FluidStack>, Float>): Builder {
            maps += chancedStacks.map { weightedMapOf(it.second to it.first, 1 - it.second to RecipeFluidStack.EMPTY) }
            return this
        }

        fun chancedStacks(vararg chancedStacks: Pair<FluidStack, Float>): Builder {
            maps += chancedStacks.map { weightedMapOf(it.second to RecipeFluidStack(it.first), 1 - it.second to RecipeFluidStack.EMPTY) }
            return this
        }

        fun chancedTagStacks(vararg chancedStacks: Pair<TagStack<Fluid>, Float>): Builder {
            maps += chancedStacks.map { weightedMapOf(it.second to RecipeFluidTagStack(it.first), 1 - it.second to RecipeFluidStack.EMPTY) }
            return this
        }

        fun weightedOutput(vararg chancedStacks: Pair<Int, IRecipeStack<FluidStack>>): Builder {
            maps += weightedMapOf(*chancedStacks)
            return this
        }

        fun weightedStacks(vararg chancedStacks: Pair<Int, FluidStack>): Builder {
            maps += weightedMapOf(*chancedStacks.map { it.first to RecipeFluidStack(it.second) }.toTypedArray())
            return this
        }

        fun weightedTagStacks(vararg chancedStacks: Pair<Int, TagStack<Fluid>>): Builder {
            maps += weightedMapOf(*chancedStacks.map { it.first to RecipeFluidTagStack(it.second) }.toTypedArray())
            return this
        }

        fun build() = FluidOutputsHandler(maps)
    }
}