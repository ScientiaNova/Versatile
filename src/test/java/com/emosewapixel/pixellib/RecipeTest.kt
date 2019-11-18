package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.machines.recipes.EnergyRecipeList
import com.emosewapixel.pixellib.machines.recipes.SimpleRecipeList
import com.emosewapixel.pixellib.machines.recipes.utility.plusAssign
import com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents.toRStack
import com.emosewapixel.pixellib.machines.recipes.utility.weightedMapOf
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation

object RecipeTest {
    val TEST_RECIPES = SimpleRecipeList(ResourceLocation(PixelTest.MOD_ID, "test"), maxInputs = 8, maxFluidInputs = 1, maxOutputs = 4)
    val THIN_RECIPES = EnergyRecipeList(ResourceLocation(PixelTest.MOD_ID, "thin_test"), maxInputs = 1, maxFluidOutputs = 1)

    init {
        TEST_RECIPES.build {
            inputs += Items.DIRT.toRStack(8)
            inputs += Items.COAL.toRStack()
            fluidInputs += Fluids.WATER.toRStack()
            outputs += weightedMapOf(9 to Items.DIAMOND.toRStack(), 1 to Items.DIAMOND_BLOCK.toRStack())
            time = 20000
        }

        THIN_RECIPES.blocksImplementing += Blocks.IRON_BARS
        THIN_RECIPES.build {
            inputs += Items.COBBLESTONE.toRStack(8)
            fluidOutputs += Fluids.LAVA.toRStack()
            time = 200
            energyPerTick = 24
        }
    }
}