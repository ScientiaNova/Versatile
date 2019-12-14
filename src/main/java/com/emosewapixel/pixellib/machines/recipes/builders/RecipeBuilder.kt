package com.emosewapixel.pixellib.machines.recipes.builders

import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler
import com.emosewapixel.pixellib.machines.recipes.components.energy.EnergyConsumptionHandler
import com.emosewapixel.pixellib.machines.recipes.components.energy.EnergyGenerationHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidInputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidOutputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemOutputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.*
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.TagStack
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
        protected val stacks = mutableListOf<ChancedRecipeStack<ItemStack>>()

        fun inputs(vararg stacks: IRecipeStack<ItemStack>): Builder {
            this.stacks += stacks.map { it.chanced() }
            return this
        }

        fun inputs(vararg stacks: ItemStack): Builder {
            this.stacks += stacks.map { RecipeItemStack(it).chanced() }
            return this
        }

        fun inputs(vararg stacks: TagStack<Item>): Builder {
            this.stacks += stacks.map { RecipeItemTagStack(it).chanced() }
            return this
        }

        fun chancedInputs(stack: IRecipeStack<ItemStack>, consumeChance: Float): Builder {
            this.stacks += stack.chanced(consumeChance)
            return this
        }

        fun chancedInputs(stack: ItemStack, consumeChance: Float): Builder {
            this.stacks += RecipeItemStack(stack).chanced(consumeChance)
            return this
        }

        fun chancedInputs(stack: TagStack<Item>, consumeChance: Float): Builder {
            this.stacks += RecipeItemTagStack(stack).chanced(consumeChance)
            return this
        }

        fun notConsumable(vararg stacks: IRecipeStack<ItemStack>): Builder {
            this.stacks += stacks.map { it.chanced(0f) }
            return this
        }

        fun notConsumable(vararg stacks: ItemStack): Builder {
            this.stacks += stacks.map { RecipeItemStack(it).chanced(0f) }
            return this
        }

        fun notConsumable(vararg stacks: TagStack<Item>): Builder {
            this.stacks += stacks.map { RecipeItemTagStack(it).chanced(0f) }
            return this
        }

        fun chancedInputs(vararg chancedStacks: Pair<IRecipeStack<ItemStack>, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(it.first, it.second) }
            return this
        }

        fun chancedStacks(vararg chancedStacks: Pair<ItemStack, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(RecipeItemStack(it.first), it.second) }
            return this
        }

        fun chancedTagStacks(vararg chancedStacks: Pair<TagStack<Item>, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(RecipeItemTagStack(it.first), it.second) }
            return this
        }

        open fun build() = ItemInputsHandler(stacks.toList())
    }
}

object FluidInputs {
    fun builder() = Builder()

    open class Builder {
        protected val stacks = mutableListOf<ChancedRecipeStack<FluidStack>>()

        fun inputs(vararg stacks: IRecipeStack<FluidStack>): Builder {
            this.stacks += stacks.map { it.chanced() }
            return this
        }

        fun inputs(vararg stacks: FluidStack): Builder {
            this.stacks += stacks.map { RecipeFluidStack(it).chanced() }
            return this
        }

        fun inputs(vararg stacks: TagStack<Fluid>): Builder {
            this.stacks += stacks.map { RecipeFluidTagStack(it).chanced() }
            return this
        }

        fun chancedInputs(stack: IRecipeStack<FluidStack>, consumeChance: Float): Builder {
            this.stacks += stack.chanced(consumeChance)
            return this
        }

        fun chancedInputs(stack: FluidStack, consumeChance: Float): Builder {
            this.stacks += RecipeFluidStack(stack).chanced(consumeChance)
            return this
        }

        fun chancedInputs(stack: TagStack<Fluid>, consumeChance: Float): Builder {
            this.stacks += RecipeFluidTagStack(stack).chanced(consumeChance)
            return this
        }

        fun notConsumable(vararg stacks: IRecipeStack<FluidStack>): Builder {
            this.stacks += stacks.map { it.chanced(0f) }
            return this
        }

        fun notConsumable(vararg stacks: FluidStack): Builder {
            this.stacks += stacks.map { RecipeFluidStack(it).chanced(0f) }
            return this
        }

        fun notConsumable(vararg stacks: TagStack<Fluid>): Builder {
            this.stacks += stacks.map { RecipeFluidTagStack(it).chanced(0f) }
            return this
        }

        fun chancedInputs(vararg chancedStacks: Pair<IRecipeStack<FluidStack>, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(it.first, it.second) }
            return this
        }

        fun chancedStacks(vararg chancedStacks: Pair<FluidStack, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(RecipeFluidStack(it.first), it.second) }
            return this
        }

        fun chancedTagStacks(vararg chancedStacks: Pair<TagStack<Fluid>, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(RecipeFluidTagStack(it.first), it.second) }
            return this
        }

        open fun build() = FluidInputsHandler(stacks.toList())
    }
}

object ItemOutputs {
    fun builder() = Builder()

    open class Builder {
        protected val stacks = mutableListOf<ChancedRecipeStack<ItemStack>>()

        fun outputs(vararg stacks: IRecipeStack<ItemStack>): Builder {
            this.stacks += stacks.map { it.chanced() }
            return this
        }

        fun outputs(vararg stacks: ItemStack): Builder {
            this.stacks += stacks.map { RecipeItemStack(it).chanced() }
            return this
        }

        fun outputs(vararg stacks: TagStack<Item>): Builder {
            this.stacks += stacks.map { RecipeItemTagStack(it).chanced() }
            return this
        }

        fun chancedOutputs(stack: IRecipeStack<ItemStack>, consumeChance: Float): Builder {
            this.stacks += stack.chanced(consumeChance)
            return this
        }

        fun chancedOutputs(stack: ItemStack, consumeChance: Float): Builder {
            this.stacks += RecipeItemStack(stack).chanced(consumeChance)
            return this
        }

        fun chancedOutputs(stack: TagStack<Item>, consumeChance: Float): Builder {
            this.stacks += RecipeItemTagStack(stack).chanced(consumeChance)
            return this
        }

        fun chancedOutputs(vararg chancedStacks: Pair<IRecipeStack<ItemStack>, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(it.first, it.second) }
            return this
        }

        fun chancedStacks(vararg chancedStacks: Pair<ItemStack, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(RecipeItemStack(it.first), it.second) }
            return this
        }

        fun chancedTagStacks(vararg chancedStacks: Pair<TagStack<Item>, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(RecipeItemTagStack(it.first), it.second) }
            return this
        }

        open fun build() = ItemOutputsHandler(stacks.toList())
    }
}

object FluidOutputs {
    fun builder() = Builder()

    open class Builder {
        protected val stacks = mutableListOf<ChancedRecipeStack<FluidStack>>()

        fun outputs(vararg stacks: IRecipeStack<FluidStack>): Builder {
            this.stacks += stacks.map { it.chanced() }
            return this
        }

        fun outputs(vararg stacks: FluidStack): Builder {
            this.stacks += stacks.map { RecipeFluidStack(it).chanced() }
            return this
        }

        fun outputs(vararg stacks: TagStack<Fluid>): Builder {
            this.stacks += stacks.map { RecipeFluidTagStack(it).chanced() }
            return this
        }

        fun chancedOutputs(stack: IRecipeStack<FluidStack>, consumeChance: Float): Builder {
            this.stacks += stack.chanced(consumeChance)
            return this
        }

        fun chancedOutputs(stack: FluidStack, consumeChance: Float): Builder {
            this.stacks += RecipeFluidStack(stack).chanced(consumeChance)
            return this
        }

        fun chancedOutputs(stack: TagStack<Fluid>, consumeChance: Float): Builder {
            this.stacks += RecipeFluidTagStack(stack).chanced(consumeChance)
            return this
        }

        fun chancedOutputs(vararg chancedStacks: Pair<IRecipeStack<FluidStack>, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(it.first, it.second) }
            return this
        }

        fun chancedStacks(vararg chancedStacks: Pair<FluidStack, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(RecipeFluidStack(it.first), it.second) }
            return this
        }

        fun chancedTagStacks(vararg chancedStacks: Pair<TagStack<Fluid>, Float>): Builder {
            this.stacks += chancedStacks.map { ChancedRecipeStack(RecipeFluidTagStack(it.first), it.second) }
            return this
        }

        open fun build() = FluidOutputsHandler(stacks.toList())
    }
}