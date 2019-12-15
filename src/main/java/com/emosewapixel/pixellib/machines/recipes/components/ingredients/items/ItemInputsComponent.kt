package com.emosewapixel.pixellib.machines.recipes.components.ingredients.items

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.ItemSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.RecipeItemSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.stacksuppliers.RecipeInputItemStackSupplierSlot
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.items.TEItemInputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.items.TEItemInventoryProperty
import com.emosewapixel.pixellib.machines.properties.implementations.items.TERecipeItemInputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers.ItemInputsProcessingHandler
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.TEAutomationRecipeProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.StackToRecipeStackHashConversion
import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponent
import com.emosewapixel.pixellib.machines.recipes.components.grouping.RecipeComponentFamilies
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraft.item.ItemStack
import kotlin.math.min

class ItemInputsComponent(val max: Int, val min: Int = 0) : IRecipeComponent<List<ChancedRecipeStack<ItemStack>>> {
    override val name = "itemInputs"
    override val family = RecipeComponentFamilies.INPUT_SLOTS

    override fun isRecipeValid(recipe: Recipe): Boolean {
        val handler = recipe[this] ?: return min <= 0
        return handler.value.size in min..max
    }

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += properties.firstOrNull { it is TEAutomationRecipeProperty }?.let { TERecipeItemInputProperty(max, it as RecipeProperty, "itemInputs", te) }
                ?: TEItemInputProperty(max, "itemInputs", te)
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

    override fun findRecipe(recipeList: RecipeList, recipes: List<Recipe>, machine: BaseTileEntity) = (machine.teProperties["itemInputs"] as? TEItemInventoryProperty)?.value?.let { stackHandler ->
        (0 until stackHandler.slots).map { slot ->
            val stack = stackHandler.getStackInSlot(slot)
            StackToRecipeStackHashConversion.convertItemStack(stack).mapNotNull { recipeList.inputMap[it]?.filterNotNull() }
                    .firstOrNull() ?: emptySet<Recipe>()
        }.fold(recipes as Iterable<Recipe>) { acc, set -> (acc intersect set) }.toList()
    } ?: emptyList()

    override fun addGUIComponents(machine: BaseTileEntity?): List<IGUIComponent> =
            machine?.let {
                val property = it.teProperties["itemInputs"] as? TEItemInventoryProperty ?: return emptyList()
                (0 until property.value.slots).map { index -> ItemSlotComponent(property, index) }
            } ?: (0 until max).map(::RecipeInputItemStackSupplierSlot)

    override fun addRecipeGUIComponents(machine: BaseTileEntity?, recipe: Recipe): List<IGUIComponent> {
        val handler = recipe[this]?.value ?: return emptyList()
        return machine?.let {
            val property = it.teProperties["itemInputs"] as? TEItemInventoryProperty ?: return emptyList()
            if (property is TERecipeItemInputProperty)
                (0 until min(property.value.slots, handler.size)).map { index -> RecipeItemSlotComponent(property, index) }
            else
                (0 until min(property.value.slots, handler.size)).map { index -> ItemSlotComponent(property, index) }
        } ?: (handler.indices).map(::RecipeInputItemStackSupplierSlot)
    }

    override fun getProcessingHandler(machine: BaseTileEntity) = (machine.teProperties["itemInputs"] as? TEItemInputProperty)?.let {
        ItemInputsProcessingHandler(it)
    }
}