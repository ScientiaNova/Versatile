package com.scientianova.versatile

import com.scientianova.versatile.blocks.registerBlocks
import com.scientianova.versatile.common.extensions.toStack
import com.scientianova.versatile.fluids.registerFluids
import com.scientianova.versatile.items.registerItems
import com.scientianova.versatile.machines.BaseMachineRegistry
import com.scientianova.versatile.machines.gui.BaseScreen
import com.scientianova.versatile.machines.packets.NetworkHandler
import com.scientianova.versatile.materialsystem.addition.COAL
import com.scientianova.versatile.materialsystem.addition.DUST_FORM
import com.scientianova.versatile.materialsystem.addition.ITEM
import com.scientianova.versatile.materialsystem.commands.FluidContainerCommand
import com.scientianova.versatile.materialsystem.commands.MaterialCommand
import com.scientianova.versatile.materialsystem.commands.ObjTypeCommand
import com.scientianova.versatile.materialsystem.lists.Forms
import com.scientianova.versatile.proxy.ClientProxy
import com.scientianova.versatile.proxy.IModProxy
import com.scientianova.versatile.proxy.ServerProxy
import com.scientianova.versatile.proxy.addModelJSONs
import com.scientianova.versatile.resources.BaseDataAddition
import com.scientianova.versatile.resources.FakeDataPackFinder
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraft.block.Block
import net.minecraft.client.gui.ScreenManager
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraftforge.event.RegistryEvent
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

@Mod(Versatile.MOD_ID)
object Versatile {
    const val MOD_ID = "versatile"

    val LOGGER: Logger = LogManager.getLogger()

    val MAIN: ItemGroup = object : ItemGroup(MOD_ID) {
        override fun createIcon() = DUST_FORM[COAL]!![ITEM]?.toStack() ?: ItemStack.EMPTY
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
        Forms.all.forEach { global ->
            global.specialized.forEach { regular ->
                if (!regular.alreadyImplemented) regular.block?.let {
                    RenderTypeLookup.setRenderLayer(it, regular.renderType ?: return@let)
                }
            }
        }
    }

    private fun commonSetup() {
        NetworkHandler
    }

    private fun enqueueIMC(e: InterModEnqueueEvent) {
        proxy.enque(e)
    }

    private fun processIMC(e: InterModProcessEvent) {
        proxy.process(e)
        BaseDataAddition.register()
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
    object RegistryEvents {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateBlockRegistry(e: RegistryEvent.Register<Block>) {
            registerBlocks(e)
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateItemRegistry(e: RegistryEvent.Register<Item>) {
            registerItems(e)
            addModelJSONs()
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateFluidRegistry(e: RegistryEvent.Register<Fluid>) = registerFluids(e)
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MOD_ID)
    object GameEvents {
        @SubscribeEvent(priority = EventPriority.HIGH)
        fun onServerAboutToStart(e: FMLServerAboutToStartEvent) = e.server.resourcePacks.addPackFinder(FakeDataPackFinder)

        @SubscribeEvent
        fun onServerStart(e: FMLServerStartingEvent) {
            MaterialCommand(e.commandDispatcher)
            ObjTypeCommand(e.commandDispatcher)
            FluidContainerCommand(e.commandDispatcher)
        }
    }
}