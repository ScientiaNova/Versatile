package com.scientianova.versatile

import com.scientianova.versatile.common.extensions.toStack
import com.scientianova.versatile.common.registry.*
import com.scientianova.versatile.machines.BaseMachineRegistry
import com.scientianova.versatile.machines.gui.BaseScreen
import com.scientianova.versatile.machines.packets.NetworkHandler
import com.scientianova.versatile.materialsystem.commands.FluidContainerCommand
import com.scientianova.versatile.materialsystem.commands.FormCommands
import com.scientianova.versatile.materialsystem.commands.MaterialCommand
import com.scientianova.versatile.materialsystem.elements.elemReg
import com.scientianova.versatile.materialsystem.events.*
import com.scientianova.versatile.materialsystem.forms.DUST_FORM
import com.scientianova.versatile.materialsystem.forms.formReg
import com.scientianova.versatile.materialsystem.materials.COAL
import com.scientianova.versatile.materialsystem.materials.addVanilla
import com.scientianova.versatile.materialsystem.materials.matReg
import com.scientianova.versatile.materialsystem.properties.ITEM
import com.scientianova.versatile.proxy.ClientProxy
import com.scientianova.versatile.proxy.IModProxy
import com.scientianova.versatile.proxy.ServerProxy
import com.scientianova.versatile.proxy.addModelJSONs
import com.scientianova.versatile.resources.FakeDataPackFinder
import com.scientianova.versatile.resources.registerData
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
import net.minecraftforge.fml.ModLoader
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
        val bus = FMLKotlinModLoadingContext.get().modEventBus

        bus.addListener<FMLClientSetupEvent> { clientSetup() }
        bus.addListener<FMLCommonSetupEvent> { commonSetup() }
        bus.addListener<InterModEnqueueEvent> { enqueueIMC(it) }
        bus.addListener<InterModProcessEvent> { processIMC(it) }

        elemReg.register(bus)
        matReg.register(bus)
        formReg.register(bus)

        proxy.init()
    }

    private fun clientSetup() {
        ScreenManager.registerFactory(BaseMachineRegistry.BASE_CONTAINER, ::BaseScreen)
        FORMS.forEach { global ->
            global.specialized.forEach inner@{ regular ->
                if (regular.alreadyImplemented) return@inner
                regular.block?.let { RenderTypeLookup.setRenderLayer(it, regular.renderType ?: return@inner) }
                regular.stillFluid?.let { RenderTypeLookup.setRenderLayer(it, regular.renderType ?: return@inner) }
                regular.flowingFluid?.let { RenderTypeLookup.setRenderLayer(it, regular.renderType ?: return@inner) }
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
        registerData()
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
    object RegistryEvents {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onEarlyBlockRegistry(e: RegistryEvent.Register<Block>) {
            val elements = ElementRegistry()
            ModLoader.get().postEvent(ElementRegistryEvent(elements))
            ELEMENTS = StringBasedRegistry(elements.map)

            val materials = MaterialRegistry()
            ModLoader.get().postEvent(MaterialRegistryEvent(materials))
            MATERIALS = StringBasedRegistry(materials.map)

            val forms = FormRegistry()
            ModLoader.get().postEvent(FormRegistryEvent(forms))
            FORMS = StringBasedRegistry(forms.map)

            addVanilla()
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateBlockRegistry(e: RegistryEvent.Register<Block>) {
            MATERIALS.forEach { mat ->
                FORMS.forEach { form ->
                    form[mat]?.let { regular ->
                        if (regular.alreadyImplemented) return@let
                        e.registry.register(regular.block ?: return@let)
                    }
                }
            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateItemRegistry(e: RegistryEvent.Register<Item>) {
            MATERIALS.forEach { mat ->
                FORMS.forEach { form ->
                    form[mat]?.let { regular ->
                        if (regular.alreadyImplemented) return@let
                        e.registry.register(regular.item ?: return@let)
                    }
                }
            }
            addModelJSONs()
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateFluidRegistry(e: RegistryEvent.Register<Fluid>) = MATERIALS.forEach { mat ->
            FORMS.forEach inner@{ form ->
                form[mat]?.let { regular ->
                    if (regular.alreadyImplemented) return@let
                    e.registry.register(regular.stillFluid ?: return@inner)
                    e.registry.register(regular.flowingFluid ?: return@inner)
                }
            }
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MOD_ID)
    object GameEvents {
        @SubscribeEvent
        fun onServerAboutToStart(e: FMLServerAboutToStartEvent) = e.server.resourcePacks.addPackFinder(FakeDataPackFinder)

        @SubscribeEvent
        fun onServerStart(e: FMLServerStartingEvent) {
            MaterialCommand(e.commandDispatcher)
            FormCommands(e.commandDispatcher)
            FluidContainerCommand(e.commandDispatcher)
        }
    }
}