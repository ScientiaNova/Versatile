package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.extensions.toStack
import com.emosewapixel.pixellib.machines.recipes.builders.AutomationRecipeListBuilder
import com.emosewapixel.pixellib.machines.recipes.builders.StandardRecipeListBuilder
import com.emosewapixel.pixellib.machines.recipes.builders.add
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.weightedMapOf
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation

object RecipeTest {
    val TEST_RECIPES = StandardRecipeListBuilder("pixeltest:test".toResLoc()).itemInputs(8).fluidInputs(1).itemOutputs(1).time().build()
    val THIN_RECIPES = AutomationRecipeListBuilder(ResourceLocation(PixelTest.MOD_ID, "thin_test")).itemInputs(3).fluidOutputs(2).time().energyPerTick(100).build()

    init {
        TEST_RECIPES.add("standard_recipe_test") {
            time = 20000
            itemInputs {
                +Items.DIRT.toStack(8)
                +Items.COAL.toStack()
            }
            fluidInputs {
                +Fluids.WATER.toStack()
            }
            itemOutputs {
                +weightedMapOf(9 to Items.DIAMOND.toStack(), 1 to Items.DIAMOND_BLOCK.toStack())
            }
        }

        THIN_RECIPES.add("automation_recipe_test") {
            time = 200
            energyPerTick = 24
            itemInputs {
                +Items.COBBLESTONE.toStack(8)
            }
            fluidOutputs {
                +Fluids.LAVA.toStack()
            }
        }
    }
}