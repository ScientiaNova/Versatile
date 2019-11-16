package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.machines.recipes.SimpleRecipeList
import com.emosewapixel.pixellib.machines.recipes.utility.plusAssign
import com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents.toRStack
import net.minecraft.block.Blocks
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation

object RecipeTest {
    val TEST_RECIPES = SimpleRecipeList(ResourceLocation(PixelTest.MOD_ID, "test"), maxInputs = 9, maxOutputs = 4)

    init {
        TEST_RECIPES.blocksImplementing += Blocks.TNT
        TEST_RECIPES.build {
            inputs += Items.DIRT.toRStack(8)
            inputs += Items.COAL.toRStack()
            outputs += Items.DIAMOND.toRStack()
        }
    }
}