package com.scientianova.versatile.machines.recipes.components.ingredients.fluids

import com.scientianova.versatile.common.extensions.toList
import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.gui.layout.IGUIComponent
import com.scientianova.versatile.machines.gui.layout.components.slots.FluidSlotComponent
import com.scientianova.versatile.machines.gui.layout.components.slots.RecipeFluidSlotComponent
import com.scientianova.versatile.machines.gui.layout.components.stacksuppliers.RecipeInputFluidStackSupplierSlot
import com.scientianova.versatile.machines.properties.ITEBoundProperty
import com.scientianova.versatile.machines.properties.implementations.fluids.TEFluidInputProperty
import com.scientianova.versatile.machines.properties.implementations.fluids.TEFluidInventoryProperty
import com.scientianova.versatile.machines.properties.implementations.fluids.TENonRepeatingFluidInputProperty
import com.scientianova.versatile.machines.properties.implementations.fluids.TERecipeFluidInputProperty
import com.scientianova.versatile.machines.properties.implementations.processing.handlers.FluidInputsProcessingHandler
import com.scientianova.versatile.machines.properties.implementations.recipes.RecipeProperty
import com.scientianova.versatile.machines.properties.implementations.recipes.TEAutomationRecipeProperty
import com.scientianova.versatile.machines.recipes.Recipe
import com.scientianova.versatile.machines.recipes.RecipeList
import com.scientianova.versatile.machines.recipes.StackToRecipeStackHashConversion
import com.scientianova.versatile.machines.recipes.components.IRecipeComponent
import com.scientianova.versatile.machines.recipes.components.grouping.RecipeComponentFamilies
import com.scientianova.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraftforge.fluids.FluidStack
import kotlin.math.min

class FluidInputsComponent(val max: Int, val min: Int = 0, val capacity: Int = 10_000) : IRecipeComponent<List<ChancedRecipeStack<FluidStack>>> {
    override val name = "fluidInputs"
    override val family = RecipeComponentFamilies.INPUT_SLOTS

    override fun isRecipeValid(recipe: Recipe): Boolean {
        val handler = recipe[this] ?: return min <= 0
        return handler.value.size in min..max
    }

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += properties.firstOrNull { it is TEAutomationRecipeProperty }?.let { TERecipeFluidInputProperty(max, it as RecipeProperty, "fluidInputs", te, capacity) }
                ?: TENonRepeatingFluidInputProperty(max, "fluidInputs", te, capacity)
    }

    override fun onRecipeAdded(recipe: Recipe) {
        recipe[this]?.value?.forEach {
            recipe.recipeList.inputMap.put(it.value.toString(), recipe)
        }
    }

    override fun onRecipeRemoved(recipe: Recipe) {
        recipe[this]?.value?.forEach {
            recipe.recipeList.inputMap.remove(it.value.toString(), recipe)
        }
    }

    override fun findRecipe(recipeList: RecipeList, recipes: List<Recipe>, machine: BaseTileEntity) = (machine.teProperties["fluidInputs"] as? TEFluidInventoryProperty)?.value?.let { stackHandler ->
        val inputStacks = stackHandler.toList()
        inputStacks.map { stack ->
            StackToRecipeStackHashConversion.convertFluidStack(stack).asSequence().mapNotNull {
                recipeList.inputMap[it]?.filter { recipe ->
                    (recipe[this] ?: 0) == inputStacks.size
                }
            }.firstOrNull(List<Recipe>::isNotEmpty) ?: emptySet<Recipe>()
        }.fold(recipes as Iterable<Recipe>) { acc, set -> (set intersect acc) }.toList()
    } ?: emptyList()

    override fun addGUIComponents(machine: BaseTileEntity?): List<IGUIComponent> =
            machine?.let {
                val property = it.teProperties["fluidInputs"] as? TEFluidInventoryProperty ?: return emptyList()
                (0 until property.value.tanks).map { index -> FluidSlotComponent(property, index) }
            } ?: (0 until max).map(::RecipeInputFluidStackSupplierSlot)

    override fun addRecipeGUIComponents(machine: BaseTileEntity?, recipe: Recipe): List<IGUIComponent> {
        val handler = recipe[this]?.value ?: return emptyList()
        return machine?.let {
            val property = it.teProperties["fluidInputs"] as? TEFluidInventoryProperty ?: return emptyList()
            if (property is TERecipeFluidInputProperty)
                (0 until min(property.value.tanks, handler.size)).map { index -> RecipeFluidSlotComponent(property, index) }
            else
                (0 until min(property.value.tanks, handler.size)).map { index -> FluidSlotComponent(property, index) }
        } ?: (handler.indices).map(::RecipeInputFluidStackSupplierSlot)
    }

    override fun getProcessingHandler(machine: BaseTileEntity) = (machine.teProperties["fluidInputs"] as? TEFluidInputProperty)?.let {
        FluidInputsProcessingHandler(it)
    }
}