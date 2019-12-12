package com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.FluidSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.RecipeFluidSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.stacksuppliers.RecipeInputFluidStackSupplierSlot
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.fluids.TEFluidInputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.fluids.TEFluidInventoryProperty
import com.emosewapixel.pixellib.machines.properties.implementations.fluids.TERecipeFluidInputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.TEAutomationRecipeProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.StackToRecipeStackHashConversion
import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponent
import com.emosewapixel.pixellib.machines.recipes.components.grouping.RecipeComponentFamilies
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import net.minecraftforge.fluids.FluidStack
import kotlin.math.min

class FluidInputsComponent(val max: Int, val min: Int = 0, val capacity: Int = 10_000) : IRecipeComponent<List<Pair<IRecipeStack<FluidStack>, Float>>> {
    override val name = "fluidInputs"
    override val family = RecipeComponentFamilies.INPUT_SLOTS

    override fun isRecipeValid(recipe: Recipe): Boolean {
        val handler = recipe[this] ?: return min <= 0
        return handler.value.size in min..max
    }

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += properties.firstOrNull { it is TEAutomationRecipeProperty }?.let { TERecipeFluidInputProperty(max, it as RecipeProperty, "fluidInputs", te, capacity) }
                ?: TEFluidInputProperty(max, "fluidInputs", te, capacity)
    }

    override fun onRecipeAdded(recipe: Recipe) {
        recipe[this]?.value?.forEach {
            recipe.recipeList.inputMap.put(it.first.toString(), recipe)
        }
    }

    override fun onRecipeRemoved(recipe: Recipe) {
        recipe[this]?.value?.forEach {
            recipe.recipeList.inputMap.remove(it.first.toString(), recipe)
        }
    }

    override fun findRecipe(recipeList: RecipeList, recipes: List<Recipe>, machine: BaseTileEntity) = (machine.teProperties["fluidInputs"] as? TEFluidInventoryProperty)?.value?.let { stackHandler ->
        (0 until stackHandler.tanks).map { slot ->
            val stack = stackHandler.getFluidInTank(slot)
            StackToRecipeStackHashConversion.convertFluidStack(stack).mapNotNull { recipeList.inputMap[it]?.filterNotNull() }
                    .firstOrNull() ?: emptySet<Recipe>()
        }.fold(recipes as Iterable<Recipe>) { acc, set -> (acc intersect set) }.toList()
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
}