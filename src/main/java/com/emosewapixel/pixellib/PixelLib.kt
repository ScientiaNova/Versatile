package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.blocks.BlockRegistry
import com.emosewapixel.pixellib.commands.FluidContainerCommand
import com.emosewapixel.pixellib.commands.MaterialCommand
import com.emosewapixel.pixellib.commands.ObjTypeCommand
import com.emosewapixel.pixellib.fluids.FluidRegistry
import com.emosewapixel.pixellib.items.ItemRegistry
import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.machines.BaseMachineRegistry
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials
import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistryInitializer
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.types.FluidType
import com.emosewapixel.pixellib.proxy.ClientProxy
import com.emosewapixel.pixellib.proxy.IModProxy
import com.emosewapixel.pixellib.proxy.ServerProxy
import com.emosewapixel.pixellib.proxy.addModelJSONs
import com.emosewapixel.pixellib.resources.BaseDataAddition
import com.emosewapixel.pixellib.resources.FakeDataPackFinder
import com.emosewapixel.pixellib.worldgen.OreGen
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.alexwells.kottle.KotlinEventBusSubscriber
import net.minecraft.block.Block
import net.minecraft.client.gui.ScreenManager
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
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
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.function.Supplier

@Mod(PixelLib.ModId)
object PixelLib {
    const val ModId = "pixellib"

    val LOGGER: Logger = LogManager.getLogger()

    val MAIN: ItemGroup = object : ItemGroup(ModId) {
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

    @KotlinEventBusSubscriber(bus = KotlinEventBusSubscriber.Bus.MOD, modid = ModId)
    object RegistryEvents {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onEarlyBlockRegistry(e: RegistryEvent.Register<Block>) {
            MaterialRegistryInitializer
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateBlockRegistry(e: RegistryEvent.Register<Block>) {
            BlockRegistry.registerBlocks(e)
            FluidRegistry.registerBlocks(e)
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateItemRegistry(e: RegistryEvent.Register<Item>) {
            ItemRegistry.registerItems(e)
            BlockRegistry.registerItems(e)
            FluidRegistry.registerItems(e)
            addModelJSONs()
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateFluidRegistry(e: RegistryEvent.Register<Fluid>) = FluidRegistry.registerFluids(e)
    }

    @KotlinEventBusSubscriber(bus = KotlinEventBusSubscriber.Bus.FORGE, modid = ModId)
    object GameEvents {
        @SubscribeEvent(priority = EventPriority.HIGH)
        fun onServerAboutToStart(e: FMLServerAboutToStartEvent) = e.server.resourcePacks.addPackFinder(FakeDataPackFinder)

        @SubscribeEvent
        fun onServerStart(e: FMLServerStartingEvent) {
            MaterialCommand(e.commandDispatcher)
            ObjTypeCommand(e.commandDispatcher)
            FluidContainerCommand(e.commandDispatcher)
        }

        @SubscribeEvent
        fun fuelTime(e: FurnaceFuelBurnTimeEvent) {
            val item = e.itemStack.item
            if (item is IMaterialObject)
                if (!item.objType.hasTag(BaseMaterials.HAS_NO_FUEL_VALUE) && item.objType.bucketVolume != 0)
                    e.burnTime = if (item.objType is FluidType) item.objType.bucketVolume / 1000 else item.objType.bucketVolume / 144 * item.mat.standardBurnTime
        }
    }
}