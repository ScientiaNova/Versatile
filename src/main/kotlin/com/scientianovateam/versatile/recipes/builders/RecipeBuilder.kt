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
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidStack

object Recipe {
    fun builder(recipeList: IRecipeLIst, name: ResourceLocation) = Builder(recipeList, name)

    open class Builder internal constructor(val recipeList: IRecipeLIst, val name: ResourceLocation) {
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

        fun build() = Recipe(recipeList, name, handlers)
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