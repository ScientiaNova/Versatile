package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.components.EnergyPerTickComponent
import com.emosewapixel.pixellib.machines.recipes.components.EnergyPerTickHandler
import com.emosewapixel.pixellib.machines.recipes.components.TimeComponent
import com.emosewapixel.pixellib.machines.recipes.components.TimeHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidOutputsComponent
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidOutputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsComponent
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.toRStack
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.WeightedMap
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.weightedMapOf
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidStack

object RecipeTest {
    val THIN_RECIPES = RecipeList(ResourceLocation(PixelTest.MOD_ID, "thin_test"), ItemInputsComponent(1), FluidOutputsComponent(1), TimeComponent, EnergyPerTickComponent(100))

    init {
        THIN_RECIPES.blocksImplementing += Blocks.IRON_BARS
        Recipe(THIN_RECIPES, "thin_recipe_test",
                ItemInputsHandler(listOf(Items.COBBLESTONE.toRStack(8) to 1f)),
                FluidOutputsHandler(listOf<WeightedMap<IRecipeStack<FluidStack>>>(weightedMapOf(1 to Fluids.LAVA.toRStack()))),
                TimeHandler(200),
                EnergyPerTickHandler(24)
        )
    }
}