package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.machines.RecipeBasedMachine
import net.alexwells.kottle.KotlinEventBusSubscriber
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

@KotlinEventBusSubscriber(modid = PixelTest.MOD_ID, bus = KotlinEventBusSubscriber.Bus.MOD)
object MachineRegistry {
    val TEST_MACHINE = RecipeBasedMachine(RecipeTest.TEST_RECIPES, Block.Properties.from(Blocks.STONE))

    @SubscribeEvent
    fun onBlockRegistry(e: RegistryEvent.Register<Block>) {
        e.registry.register(TEST_MACHINE)
    }

    @SubscribeEvent
    fun onIteMRegistry(e: RegistryEvent.Register<Item>) {
        e.registry.register(BlockItem(TEST_MACHINE, Item.Properties().group(PixelLib.MAIN)).setRegistryName(TEST_MACHINE.registryName))
    }
}