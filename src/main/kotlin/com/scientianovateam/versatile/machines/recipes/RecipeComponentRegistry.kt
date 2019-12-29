package com.scientianovateam.versatile.machines.recipes

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.common.serialization.RECIPE_COMPONENT_HANDLER_SERIALIZERS
import com.scientianovateam.versatile.common.serialization.RECIPE_FLUID_STACK_SERIALIZERS
import com.scientianovateam.versatile.common.serialization.RECIPE_ITEM_STACK_SERIALIZERS
import com.scientianovateam.versatile.common.serialization.registerAll
import com.scientianovateam.versatile.machines.recipes.components.energy.consumption.EnergyConsumptionHandler
import com.scientianovateam.versatile.machines.recipes.components.energy.generation.EnergyGenerationHandler
import com.scientianovateam.versatile.machines.recipes.components.ingredients.fluids.FluidInputsHandler
import com.scientianovateam.versatile.machines.recipes.components.ingredients.fluids.FluidOutputsHandler
import com.scientianovateam.versatile.machines.recipes.components.ingredients.items.ItemInputsHandler
import com.scientianovateam.versatile.machines.recipes.components.ingredients.items.ItemOutputsHandler
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.RecipeFluidStack
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.RecipeFluidTagStack
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.RecipeItemStack
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.RecipeItemTagStack
import com.scientianovateam.versatile.machines.recipes.components.time.TimeHandler
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