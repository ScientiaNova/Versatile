package com.scientianovateam.versatile

import com.scientianovateam.versatile.blocks.BlockRegistry
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.fluids.FluidRegistry
import com.scientianovateam.versatile.items.ItemRegistry
import com.scientianovateam.versatile.items.MaterialItem
import com.scientianovateam.versatile.machines.BaseMachineRegistry
import com.scientianovateam.versatile.machines.gui.BaseScreen
import com.scientianovateam.versatile.machines.packets.NetworkHandler
import com.scientianovateam.versatile.machines.recipes.RecipeList
import com.scientianovateam.versatile.machines.recipes.RecipeLists
import com.scientianovateam.versatile.materialsystem.commands.FluidContainerCommand
import com.scientianovateam.versatile.materialsystem.commands.FormCommand
import com.scientianovateam.versatile.materialsystem.commands.MaterialCommand
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.proxy.ClientProxy
import com.scientianovateam.versatile.proxy.IModProxy
import com.scientianovateam.versatile.proxy.ServerProxy
import com.scientianovateam.versatile.proxy.addModelJSONs
import com.scientianovateam.versatile.resources.BaseDataAddition
import com.scientianovateam.versatile.resources.FakeDataPackFinder
import com.scientianovateam.versatile.worldgen.OreGen
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraft.block.Block
import net.minecraft.client.gui.ScreenManager
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.RecipesUpdatedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import net.minecraftforge.fml.event.server.FMLServerStartingEvent
import net.minecraftforge.resource.ISelectiveResourceReloadListener
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.function.Supplier

@Mod(Versatile.MOD_ID)
object Versatile {
    const val MOD_ID = "versatile"

    val LOGGER: Logger = LogManager.getLogger()

    val MAIN: ItemGroup = object : ItemGroup(MOD_ID) {
        override fun createIcon() = ItemStack(MaterialItems.all.first { it is MaterialItem })
    }

    private val proxy = DistExecutor.runForDist<IModProxy>({ Supplier { ClientProxy } }, { Supplier { ServerProxy } })

    init {
        FMLKotlinModLoadingContext.get().modEventBus.addListener<FMLClientSetupEvent> { clientSetup() }
        FMLKotlinModLoadingContext.get().modEventBus.addListener<FMLCommonSetupEvent> { commonSetup() }
        FMLKotlinModLoadingContext.get().modEventBus.addListener<InterModEnqueueEvent> { enqueueIMC(it) }
        FMLKotlinModLoadingContext.get().modEventBus.addListener<InterModProcessEvent> { processIMC(it) }

        proxy.init()
    }

    private fun clientSetup() {
        ScreenManager.registerFactory(BaseMachineRegistry.BASE_CONTAINER, ::BaseScreen)
    }

    private fun commonSetup() {
        NetworkHandler
    }

    private fun enqueueIMC(e: InterModEnqueueEvent) {
        proxy.enque(e)
        OreGen.register()
    }

    private fun processIMC(e: InterModProcessEvent) {
        proxy.process(e)
        BaseDataAddition.register()
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
    object RegistryEvents {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onEarlyBlockRegistry(e: RegistryEvent.Register<Block>) {
            MinecraftForge.EVENT_BUS.post(VersatileRegistryEvent())
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateBlockRegistry(e: RegistryEvent.Register<Block>) {
            BlockRegistry.registerBlocks(e)
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateItemRegistry(e: RegistryEvent.Register<Item>) {
            ItemRegistry.registerItems(e)
            addModelJSONs()
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateFluidRegistry(e: RegistryEvent.Register<Fluid>) = FluidRegistry.registerFluids(e)
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MOD_ID)
    object GameEvents {
        @SubscribeEvent(priority = EventPriority.HIGH)
        fun onEarlyServerAboutToStart(e: FMLServerAboutToStartEvent) {
            e.server.resourcePacks.addPackFinder(FakeDataPackFinder)
            e.server.recipeManager.recipes = e.server.recipeManager.recipes.mapValues { it.value.toMutableMap() }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateServerAboutToStart(e: FMLServerAboutToStartEvent) {
            e.server.resourceManager.addReloadListener(ISelectiveResourceReloadListener { _, _ ->
                //TODO
            })
        }

        @SubscribeEvent
        fun onServerStart(e: FMLServerStartingEvent) {
            MaterialCommand(e.commandDispatcher)
            FormCommand(e.commandDispatcher)
            FluidContainerCommand(e.commandDispatcher)
        }

        @SubscribeEvent
        fun fuelTime(e: FurnaceFuelBurnTimeEvent) {
            val item = e.itemStack.item
            if (item is IMaterialObject) item.objType.burnTime(item.mat).let { if (it > 0) e.burnTime = it }
        }
    }
}