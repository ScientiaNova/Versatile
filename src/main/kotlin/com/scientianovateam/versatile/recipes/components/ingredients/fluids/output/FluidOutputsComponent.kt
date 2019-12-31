package com.scientianovateam.versatile.recipes.components.ingredients.fluids.output

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getPrimitiveOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.FluidSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.stacksuppliers.RecipeOutputFluidStackSupplierSlot
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TEFluidInventoryProperty
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TEFluidOutputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.FluidOutputsProcessingHandler
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.recipes.components.grouping.RecipeComponentFamilies
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraftforge.fluids.FluidStack
import kotlin.math.min

class FluidOutputsComponent(val min: Int, val max: Int, val capacity: Int) : IRecipeComponent<List<ChancedRecipeStack<FluidStack>>> {
    override val name = "fluidOutputs"
    override val family = RecipeComponentFamilies.OUTPUT_SLOTS
    override val serializer = Serializer

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

    object Serializer : IRegisterableJSONSerializer<FluidOutputsComponent, JsonObject> {
        override val registryName = "fluid_outputs".toResLocV()

        override fun read(json: JsonObject) = FluidOutputsComponent(
                json.getPrimitiveOrNull("min")?.asInt ?: 0,
                json.getPrimitiveOrNull("max")?.asInt ?: error("Missing maximum amount of fluid outputs"),
                json.getPrimitiveOrNull("capacity")?.asInt ?: 10_000
        )

        override fun write(obj: FluidOutputsComponent) = json {
            "min" to obj.min
            "max" to obj.max
            "capacity" to obj.capacity
        }
    }
}