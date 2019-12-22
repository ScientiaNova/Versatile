package com.scientianovateam.versatile

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.machines.recipes.builders.AutomationRecipeListBuilder
import com.scientianovateam.versatile.machines.recipes.builders.StandardRecipeListBuilder
import com.scientianovateam.versatile.machines.recipes.builders.add
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation

object RecipeTest {
    val TEST_RECIPES = StandardRecipeListBuilder("versatiletest:test".toResLoc()).itemInputs(8).fluidInputs(1).itemOutputs(1).time().build()
    val THIN_RECIPES = AutomationRecipeListBuilder(ResourceLocation(VersatileTest.MOD_ID, "thin_test")).itemInputs(3).fluidOutputs(2).time().consumeEnergy(100).build()
    val GENERATOR_RECIPES = AutomationRecipeListBuilder(ResourceLocation(VersatileTest.MOD_ID, "generator_test")).fluidInputs(1).time().generateEnergy(10000).build()

    init {
        TEST_RECIPES.add("standard_recipe_test") {
            time = 200
            itemInputs {
                +Items.DIRT.toStack(8)
                +Items.COAL.toStack()
            }
            fluidInputs {
                +Fluids.WATER.toStack()
            }
            itemOutputs {
                +Items.DIAMOND.toStack()
            }
        }

        THIN_RECIPES.add("automation_recipe_test") {
            time = 200
            energyConsumedPerTick = 24
            itemInputs {
                +Items.COBBLESTONE.toStack(8)
            }
            fluidOutputs {
                +Fluids.LAVA.toStack()
            }
        }

        THIN_RECIPES.add("automation_recipe_test_2") {
            time = 30
            energyConsumedPerTick = 24
            itemInputs {
                +listOf(Items.ICE.toStack() to 0.5f, Items.HEART_OF_THE_SEA.toStack() to 0f)
            }
            fluidOutputs {
                +Fluids.WATER.toStack()
            }
        }

        GENERATOR_RECIPES.add("lava_to_power") {
            time = 2000
            energyGeneratedPerTick = 200
            fluidInputs {
                +Fluids.LAVA.toStack()
            }
        }
    }
}