package com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.FluidSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.stacksuppliers.RecipeOutputFluidStackSupplierSlot
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.fluids.TEFluidInventoryProperty
import com.emosewapixel.pixellib.machines.properties.implementations.fluids.TEFluidOutputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers.FluidOutputsProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponent
import com.emosewapixel.pixellib.machines.recipes.components.grouping.RecipeComponentFamilies
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
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