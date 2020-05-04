package com.scientianova.versatile.machines.recipes.components.ingredients.items

import com.scientianovateam.versatile.common.extensions.toList
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.ItemSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.RecipeItemSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.stacksuppliers.RecipeInputItemStackSupplierSlot
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.items.TEItemInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.items.TEItemInventoryProperty
import com.scientianovateam.versatile.machines.properties.implementations.items.TENonRepeatingItemInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.items.TERecipeItemInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.ItemInputsProcessingHandler
import com.scientianovateam.versatile.machines.properties.implementations.recipes.RecipeProperty
import com.scientianovateam.versatile.machines.properties.implementations.recipes.TEAutomationRecipeProperty
import com.scientianovateam.versatile.machines.recipes.Recipe
import com.scientianovateam.versatile.machines.recipes.RecipeList
import com.scientianovateam.versatile.machines.recipes.StackToRecipeStackHashConversion
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.machines.recipes.components.grouping.RecipeComponentFamilies
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
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
                ?: TENonRepeatingItemInputProperty(max, "itemInputs", te)
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
        val inputStacks = stackHandler.toList()
        inputStacks.map { stack ->
            StackToRecipeStackHashConversion.convertItemStack(stack).asSequence().mapNotNull {
                recipeList.inputMap[it]?.filter { recipe ->
                    (recipe[this] ?: 0) == inputStacks.size
                }
            }.firstOrNull(List<Recipe>::isNotEmpty) ?: emptySet<Recipe>()
        }.fold(recipes as Iterable<Recipe>) { acc, set -> (set intersect acc) }.toList()
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