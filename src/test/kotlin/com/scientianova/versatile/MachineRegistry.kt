package com.scientianova.versatile

import com.scientianova.versatile.machines.defaults.AutomationRecipeBasedMachine
import com.scientianova.versatile.machines.defaults.StandardRecipeBasedMachine
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = VersatileTest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object MachineRegistry {
    val TEST_MACHINE = StandardRecipeBasedMachine(RecipeTest.TEST_RECIPES, Block.Properties.from(Blocks.STONE))
    val THIN_TEST_MACHINE = AutomationRecipeBasedMachine(RecipeTest.THIN_RECIPES, Block.Properties.from(Blocks.STONE))
    val TEST_GENERATOR = AutomationRecipeBasedMachine(RecipeTest.GENERATOR_RECIPES, Block.Properties.from(Blocks.STONE))

    @SubscribeEvent
    fun onBlockRegistry(e: RegistryEvent.Register<Block>) {
        e.registry.register(TEST_MACHINE)
        e.registry.register(THIN_TEST_MACHINE)
        e.registry.register(TEST_GENERATOR)
    }

    @SubscribeEvent
    fun onIteMRegistry(e: RegistryEvent.Register<Item>) {
        e.registry.register(BlockItem(TEST_MACHINE, Item.Properties().group(Versatile.MAIN)).setRegistryName(TEST_MACHINE.registryName))
        e.registry.register(BlockItem(THIN_TEST_MACHINE, Item.Properties().group(Versatile.MAIN)).setRegistryName(THIN_TEST_MACHINE.registryName))
        e.registry.register(BlockItem(TEST_GENERATOR, Item.Properties().group(Versatile.MAIN)).setRegistryName(TEST_GENERATOR.registryName))
    }
}