package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.builders.AutomationRecipeListBuilder
import com.emosewapixel.pixellib.machines.recipes.builders.StandardRecipeListBuilder
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidInputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidOutputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemOutputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.toRStack
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.WeightedMap
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.weightedMapOf
import com.emosewapixel.pixellib.machines.recipes.components.stats.EnergyPerTickHandler
import com.emosewapixel.pixellib.machines.recipes.components.stats.TimeHandler
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidStack

object RecipeTest {
    val TEST_RECIPES = StandardRecipeListBuilder("pixeltest:test".toResLoc()).itemInputs(8).fluidInputs(1).itemOutputs(1).time().build()
    val THIN_RECIPES = AutomationRecipeListBuilder(ResourceLocation(PixelTest.MOD_ID, "thin_test")).itemInputs(3).fluidInputs(2).time().energyPerTick(100).build()

    init {
        Recipe(TEST_RECIPES, "standard_recipe_test",
                ItemInputsHandler(listOf(Items.DIRT.toRStack(8) to 1f, Items.COAL.toRStack() to 1f)),
                FluidInputsHandler(listOf(Fluids.WATER.toRStack() to 1f)),
                ItemOutputsHandler(listOf<WeightedMap<IRecipeStack<ItemStack>>>(weightedMapOf(9 to Items.DIAMOND.toRStack(), 1 to Items.DIAMOND_BLOCK.toRStack()))),
                TimeHandler(20000)
        )

        Recipe(THIN_RECIPES, "automation_recipe_test",
                ItemInputsHandler(listOf(Items.COBBLESTONE.toRStack(8) to 1f)),
                FluidOutputsHandler(listOf<WeightedMap<IRecipeStack<FluidStack>>>(weightedMapOf(1 to Fluids.LAVA.toRStack()))),
                TimeHandler(200),
                EnergyPerTickHandler(24)
        )
    }
}