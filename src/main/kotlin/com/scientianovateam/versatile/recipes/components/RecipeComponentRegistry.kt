package com.scientianovateam.versatile.recipes.components

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.common.serialization.RECIPE_COMPONENT_HANDLER_SERIALIZERS
import com.scientianovateam.versatile.common.serialization.RECIPE_FLUID_STACK_SERIALIZERS
import com.scientianovateam.versatile.common.serialization.RECIPE_ITEM_STACK_SERIALIZERS
import com.scientianovateam.versatile.common.serialization.registerAll
import com.scientianovateam.versatile.recipes.components.energy.consumption.EnergyConsumptionHandler
import com.scientianovateam.versatile.recipes.components.energy.generation.EnergyGenerationHandler
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.input.FluidInputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.output.FluidOutputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.items.input.ItemInputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.items.output.ItemOutputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids.RecipeFluidStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids.RecipeFluidTagStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemTagStack
import com.scientianovateam.versatile.recipes.components.time.TimeHandler
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID)
object RecipeComponentRegistry {
    @SubscribeEvent
    fun onVersatileRegistry(e: VersatileRegistryEvent) {
        RECIPE_COMPONENT_HANDLER_SERIALIZERS.registerAll(
                TimeHandler.Serializer,
                EnergyConsumptionHandler.Serializer,
                EnergyGenerationHandler.Serializer,
                ItemInputsHandler.Serializer,
                ItemOutputsHandler.Serializer,
                FluidInputsHandler.Serializer,
                FluidOutputsHandler.Serializer
        )

        RECIPE_ITEM_STACK_SERIALIZERS.registerAll(RecipeItemStack.Serializer, RecipeItemTagStack.Serializer)

        RECIPE_FLUID_STACK_SERIALIZERS.registerAll(RecipeFluidStack.Serializer, RecipeFluidTagStack.Serializer)
    }
}