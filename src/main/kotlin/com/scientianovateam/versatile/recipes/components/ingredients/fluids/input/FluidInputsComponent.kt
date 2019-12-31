package com.scientianovateam.versatile.recipes.components.ingredients.fluids.input

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getPrimitiveOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toList
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.FluidSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.RecipeFluidSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.stacksuppliers.RecipeInputFluidStackSupplierSlot
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TEFluidInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TEFluidInventoryProperty
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TENonRepeatingFluidInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TERecipeFluidInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.FluidInputsProcessingHandler
import com.scientianovateam.versatile.machines.properties.implementations.recipes.RecipeProperty
import com.scientianovateam.versatile.machines.properties.implementations.recipes.TEAutomationRecipeProperty
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.recipes.components.grouping.RecipeComponentFamilies
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import com.scientianovateam.versatile.recipes.lists.IRecipeLIst
import com.scientianovateam.versatile.recipes.lists.StackToRecipeStackHashConversion
import net.minecraftforge.fluids.FluidStack
import kotlin.math.min

data class FluidInputsComponent(val min: Int, val max: Int, val capacity: Int) : IRecipeComponent<List<ChancedRecipeStack<FluidStack>>> {
    override val name = "fluidInputs"
    override val family = RecipeComponentFamilies.INPUT_SLOTS
    override val serializer = Serializer

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

    override fun findRecipe(recipeList: IRecipeLIst, recipes: List<Recipe>, machine: BaseTileEntity) = (machine.teProperties["fluidInputs"] as? TEFluidInventoryProperty)?.value?.let { stackHandler ->
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

    object Serializer : IRegisterableJSONSerializer<FluidInputsComponent, JsonObject> {
        override val registryName = "fluid_inputs".toResLocV()

        override fun read(json: JsonObject) = FluidInputsComponent(
                json.getPrimitiveOrNull("min")?.asInt ?: 0,
                json.getPrimitiveOrNull("max")?.asInt ?: error("Missing maximum amount of fluid inputs"),
                json.getPrimitiveOrNull("capacity")?.asInt ?: 10_000
        )

        override fun write(obj: FluidInputsComponent) = json {
            "min" to obj.min
            "max" to obj.max
            "capacity" to obj.capacity
        }
    }
}