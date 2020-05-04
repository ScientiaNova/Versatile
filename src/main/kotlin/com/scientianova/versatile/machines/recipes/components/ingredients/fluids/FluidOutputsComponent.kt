package com.scientianova.versatile.machines.recipes.components.ingredients.fluids

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.FluidSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.stacksuppliers.RecipeOutputFluidStackSupplierSlot
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TEFluidInventoryProperty
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TEFluidOutputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.FluidOutputsProcessingHandler
import com.scientianovateam.versatile.machines.recipes.Recipe
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.machines.recipes.components.grouping.RecipeComponentFamilies
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraftforge.fluids.FluidStack
import kotlin.math.min

class FluidOutputsComponent(val max: Int, val min: Int = 0, val capacity: Int = 10_000) : IRecipeComponent<List<ChancedRecipeStack<FluidStack>>> {
    override val name = "fluidOutputs"
    override val family = RecipeComponentFamilies.OUTPUT_SLOTS

    override fun isRecipeValid(recipe: Recipe): Boolean {
        val handler = recipe[this] ?: return min <= 0
        return handler.value.size in min..max
    }

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += TEFluidOutputProperty(max, "fluidOutputs", te)
    }

    override fun addGUIComponents(machine: BaseTileEntity?): List<IGUIComponent> =
            machine?.let {
                val property = it.teProperties["fluidOutputs"] as? TEFluidInventoryProperty ?: return emptyList()
                (0 until property.value.tanks).map { index -> FluidSlotComponent(property, index) }
            } ?: (0 until max).map(::RecipeOutputFluidStackSupplierSlot)

    override fun addRecipeGUIComponents(machine: BaseTileEntity?, recipe: Recipe): List<IGUIComponent> {
        val handler = recipe[this]?.value ?: return emptyList()
        return machine?.let {
            val property = it.teProperties["fluidOutputs"] as? TEFluidInventoryProperty ?: return emptyList()
            (0 until min(property.value.tanks, handler.size)).map { index -> FluidSlotComponent(property, index) }
        } ?: (handler.indices).map(::RecipeOutputFluidStackSupplierSlot)
    }

    override fun getProcessingHandler(machine: BaseTileEntity) = (machine.teProperties["fluidOutputs"] as? TEFluidOutputProperty)?.let {
        FluidOutputsProcessingHandler(it)
    }
}