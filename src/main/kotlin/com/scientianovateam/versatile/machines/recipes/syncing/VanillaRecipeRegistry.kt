package com.scientianovateam.versatile.machines.recipes.syncing

import com.scientianovateam.versatile.Versatile
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.IRecipeType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object VanillaRecipeRegistry {
    lateinit var MATERIAL_BASED_RECIPE_TYPE: IRecipeType<MaterialBasedCodeRecipe>
        private set

    @SubscribeEvent
    fun onRecipeSerializerRegistry(e: RegistryEvent.Register<IRecipeSerializer<*>>) {
        MATERIAL_BASED_RECIPE_TYPE = IRecipeType.register<MaterialBasedCodeRecipe>("versatile:material_based")
        e.registry.register(MaterialBasedCodeRecipeSerializer)
    }
}