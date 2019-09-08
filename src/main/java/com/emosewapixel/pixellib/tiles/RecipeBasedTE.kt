package com.emosewapixel.pixellib.tiles

import com.emosewapixel.pixellib.recipes.SimpleMachineRecipe
import com.emosewapixel.pixellib.recipes.SimpleRecipeList
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntityType

open class RecipeBasedTE(type: TileEntityType<*>, recipeList: SimpleRecipeList) : AbstractRecipeBasedTE<SimpleMachineRecipe>(type, recipeList) {
    override var currentRecipe = SimpleMachineRecipe.EMPTY

    public override val recipeByInput: SimpleMachineRecipe
        get() {
            val stacksStream = (0 until recipeList.maxInputs).map { recipeInventory.getStackInSlot(it) }

            if (stacksStream.any { it.isEmpty })
                return SimpleMachineRecipe.EMPTY

            val chosenRecipe = recipeList.recipes.first { it.isInputValid(stacksStream.toTypedArray()) }
                    ?: return SimpleMachineRecipe.EMPTY

            val recipeIndexes = (0 until recipeList.maxInputs)
                    .map { chosenRecipe.getIndexOfInput(recipeInventory.getStackInSlot(it)) }

            return if (recipeIndexes.contains(-1)) SimpleMachineRecipe.EMPTY else SimpleMachineRecipe(
                    (0 until recipeList.maxInputs).map { ItemStack(recipeInventory.getStackInSlot(it).item, chosenRecipe.getCountOfInput(recipeIndexes[it])) }.toTypedArray(),
                    recipeIndexes.map { chosenRecipe.getConsumeChance(it) }.toTypedArray(),
                    chosenRecipe.outputs,
                    chosenRecipe.outputChances,
                    chosenRecipe.time)
        }
}