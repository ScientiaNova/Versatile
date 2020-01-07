package com.scientianovateam.versatile.recipes

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.recipes.components.energy.consumption.EnergyConsumptionComponent
import com.scientianovateam.versatile.recipes.components.energy.consumption.EnergyConsumptionHandler
import com.scientianovateam.versatile.recipes.components.energy.generation.EnergyGenerationComponent
import com.scientianovateam.versatile.recipes.components.energy.generation.EnergyGenerationHandler
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.input.FluidInputsComponent
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.input.FluidInputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.output.FluidOutputsComponent
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.output.FluidOutputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.items.input.ItemInputsComponent
import com.scientianovateam.versatile.recipes.components.ingredients.items.input.ItemInputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.items.output.ItemOutputsComponent
import com.scientianovateam.versatile.recipes.components.ingredients.items.output.ItemOutputsHandler
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids.RecipeFluidStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids.RecipeFluidTagStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemTagStack
import com.scientianovateam.versatile.recipes.components.time.TimeComponent
import com.scientianovateam.versatile.recipes.components.time.TimeHandler
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object RecipeEventSubscriber {
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

        RECIPE_COMPONENT_SERIALIZERS.registerAll(
                TimeComponent.Serializer,
                EnergyConsumptionComponent.Serializer,
                EnergyGenerationComponent.Serializer,
                ItemInputsComponent.Serializer,
                ItemOutputsComponent.Serializer,
                FluidInputsComponent.Serializer,
                FluidOutputsComponent.Serializer
        )

        RECIPE_ITEM_STACK_SERIALIZERS.registerAll(RecipeItemStack.Serializer, RecipeItemTagStack.Serializer)

        RECIPE_FLUID_STACK_SERIALIZERS.registerAll(RecipeFluidStack.Serializer, RecipeFluidTagStack.Serializer)
    }
}