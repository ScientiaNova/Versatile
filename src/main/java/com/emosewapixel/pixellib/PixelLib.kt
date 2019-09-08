package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.blocks.BlockRegistry
import com.emosewapixel.pixellib.items.ItemRegistry
import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialItem
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
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
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
    val main: ItemGroup = object : ItemGroup(ModId) {
        override fun createIcon() = ItemStack(MaterialItems.getAll().filterIsInstance<IMaterialItem>().first() as Item)
    }

    init {
        FMLKotlinModLoadingContext.get().modEventBus.addListener<InterModEnqueueEvent> { this.enqueueIMC(it) }
        FMLKotlinModLoadingContext.get().modEventBus.addListener<InterModProcessEvent> { this.processIMC(it) }

        MaterialRegistry

        proxy.init()
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
    fun onBlockRegistry(e: RegistryEvent.Register<Block>) = BlockRegistry.registry(e)

    @SubscribeEvent
    fun onItemRegistry(e: RegistryEvent.Register<Item>) {
        ItemRegistry.registry(e)
        BlockRegistry.itemRegistry(e)
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onLateItemRegistry(e: RegistryEvent.Register<Item>) = addModelJSONs()
}

@KotlinEventBusSubscriber(bus = KotlinEventBusSubscriber.Bus.FORGE, modid = PixelLib.ModId)
object GameEvents {
    @SubscribeEvent
    fun onServerAboutToStart(e: FMLServerAboutToStartEvent) = e.server.resourcePacks.addPackFinder(FakeDataPackFinder())

    @SubscribeEvent
    fun fuelTime(e: FurnaceFuelBurnTimeEvent) {
        val item = e.itemStack.item
        if (item is BlockItem) {
            val block = Block.getBlockFromItem(item)
            if (block is IMaterialItem) {
                val type = (block as IMaterialItem).objType
                if (MaterialRegistry.HAS_NO_FUEL_VALUE !in type.typeTags && (block as IMaterialItem).objType.bucketVolume != 0)
                    e.burnTime = (type.bucketVolume / 144 + type.bucketVolume / 1296) * (block as IMaterialItem).mat.standardBurnTime
            }
        } else if (item is IMaterialItem)
            if (MaterialRegistry.HAS_NO_FUEL_VALUE !in (item as IMaterialItem).objType.typeTags && (item as IMaterialItem).objType.bucketVolume != 0)
                e.burnTime = (item as IMaterialItem).objType.bucketVolume / 144 * (item as IMaterialItem).mat.standardBurnTime
    }
}