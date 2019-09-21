package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.blocks.BlockRegistry
import com.emosewapixel.pixellib.commands.FluidContainerCommand
import com.emosewapixel.pixellib.commands.MaterialItemCommand
import com.emosewapixel.pixellib.fluids.FluidRegistry
import com.emosewapixel.pixellib.items.ItemRegistry
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.types.FluidType
import com.emosewapixel.pixellib.proxy.ClientProxy
import com.emosewapixel.pixellib.proxy.IModProxy
import com.emosewapixel.pixellib.proxy.ServerProxy
import com.emosewapixel.pixellib.proxy.addModelJSONs
import com.emosewapixel.pixellib.resources.DataAddition
import com.emosewapixel.pixellib.resources.FakeDataPackFinder
import com.emosewapixel.pixellib.worldgen.OreGen
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.alexwells.kottle.KotlinEventBusSubscriber
import net.minecraft.block.Block
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

    @JvmField
    val LOGGER: Logger = LogManager.getLogger()

    private val proxy = DistExecutor.runForDist<IModProxy>({ Supplier { ClientProxy } }, { Supplier { ServerProxy } })

    @JvmField
    val MAIN: ItemGroup = object : ItemGroup(ModId) {
        override fun createIcon() = ItemStack(MaterialItems.all.filterIsInstance<IMaterialObject>().first() as Item)
    }

    init {
        FMLKotlinModLoadingContext.get().modEventBus.addListener(this::commonSetup)
        FMLKotlinModLoadingContext.get().modEventBus.addListener(this::enqueueIMC)
        FMLKotlinModLoadingContext.get().modEventBus.addListener(this::processIMC)

        MaterialRegistry

        proxy.init()
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        NetworkHandler
    }

    private fun enqueueIMC(event: InterModEnqueueEvent) {
        proxy.enque(event)
        OreGen.register()
    }

    private fun processIMC(event: InterModProcessEvent) {
        proxy.process(event)
        DataAddition.register()
    }
}

@KotlinEventBusSubscriber(bus = KotlinEventBusSubscriber.Bus.MOD, modid = PixelLib.ModId)
object RegistryEvents {
    @SubscribeEvent
    fun onBlockRegistry(e: RegistryEvent.Register<Block>) {
        BlockRegistry.registerBlocks(e)
        FluidRegistry.registerBlocks(e)
    }

    @SubscribeEvent
    fun onItemRegistry(e: RegistryEvent.Register<Item>) {
        ItemRegistry.registerItems(e)
        BlockRegistry.registerItems(e)
        FluidRegistry.registerItems(e)
    }

    @SubscribeEvent
    fun onFluidRegistry(e: RegistryEvent.Register<Fluid>) = FluidRegistry.registerFluids(e)

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onLateItemRegistry(e: RegistryEvent.Register<Item>) = addModelJSONs()
}

@KotlinEventBusSubscriber(bus = KotlinEventBusSubscriber.Bus.FORGE, modid = PixelLib.ModId)
object GameEvents {
    @SubscribeEvent
    fun onServerAboutToStart(e: FMLServerAboutToStartEvent) = e.server.resourcePacks.addPackFinder(FakeDataPackFinder())

    @SubscribeEvent
    fun onServerStart(e: FMLServerStartingEvent) {
        MaterialItemCommand(e.commandDispatcher)
        FluidContainerCommand(e.commandDispatcher)
    }

    @SubscribeEvent
    fun fuelTime(e: FurnaceFuelBurnTimeEvent) {
        val item = e.itemStack.item
        if (item is IMaterialObject)
            if (!item.objType.hasTag(MaterialRegistry.HAS_NO_FUEL_VALUE) && item.objType.bucketVolume != 0)
                e.burnTime = if (item.objType is FluidType) item.objType.bucketVolume / 1000 else item.objType.bucketVolume / 144 * item.mat.standardBurnTime
    }
}