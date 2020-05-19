package com.scientianova.versatile

import com.scientianova.versatile.common.extensions.toStack
import com.scientianova.versatile.common.registry.ELEMENTS
import com.scientianova.versatile.common.registry.FORMS
import com.scientianova.versatile.common.registry.MATERIALS
import com.scientianova.versatile.common.registry.StringBasedRegistry
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
import com.scientianova.versatile.resources.recipes.RecipeEvent
import com.scientianova.versatile.resources.recipes.RecipeHandler
import com.scientianova.versatile.resources.tags.TagEvent
import com.scientianova.versatile.resources.tags.TagHandler
import com.scientianova.versatile.resources.tags.tagEvent
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraft.block.Block
import net.minecraft.client.gui.ScreenManager
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.resources.SimpleReloadableResourceManager
import net.minecraft.tags.BlockTags
import net.minecraft.tags.EntityTypeTags
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.MinecraftForge
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
import net.minecraftforge.resource.ISelectiveResourceReloadListener
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
        fun onServerAboutToStart(e: FMLServerAboutToStartEvent) {
            val server = e.server
            val listeners = (server.resourceManager as SimpleReloadableResourceManager).reloadListeners

            listeners.add(listeners.indexOf(server.networkTagManager) + 1, ISelectiveResourceReloadListener { _, _ ->
                val newItemTags = ItemTags.getCollection().getTagMap().toMutableMap()
                MinecraftForge.EVENT_BUS.post(tagEvent(TagHandler(newItemTags)))
                ItemTags.getCollection().tagMap = newItemTags

                val newBlockTags = BlockTags.getCollection().getTagMap().toMutableMap()
                MinecraftForge.EVENT_BUS.post(tagEvent(TagHandler(newBlockTags)))
                BlockTags.getCollection().tagMap = newBlockTags

                val newFluidTags = FluidTags.getCollection().getTagMap().toMutableMap()
                MinecraftForge.EVENT_BUS.post(tagEvent(TagHandler(newFluidTags)))
                FluidTags.getCollection().tagMap = newFluidTags

                val newEntityTags = EntityTypeTags.getCollection().getTagMap().toMutableMap()
                MinecraftForge.EVENT_BUS.post(tagEvent(TagHandler(newEntityTags)))
                EntityTypeTags.getCollection().tagMap = newEntityTags
            })

            listeners.add(listeners.indexOf(server.recipeManager) + 1, ISelectiveResourceReloadListener { _, _ ->
                val replacement = mutableMapOf<IRecipeType<*>, MutableMap<ResourceLocation, IRecipe<*>>>()
                server.recipeManager.recipes.forEach { (key, value) ->
                    replacement[key] = value.toMutableMap()
                }
                MinecraftForge.EVENT_BUS.post(RecipeEvent(RecipeHandler(replacement)))
                server.recipeManager.recipes = replacement
            })
        }

        @SubscribeEvent
        fun onServerStart(e: FMLServerStartingEvent) {
            MaterialCommand(e.commandDispatcher)
            FormCommands(e.commandDispatcher)
            FluidContainerCommand(e.commandDispatcher)
        }

        @SubscribeEvent
        fun onItemTagAddition(e: TagEvent<Item>) = FORMS.forEach { global ->
            global.specialized.forEach inner@{ regular ->
                if (regular.alreadyImplemented) return@inner
                regular.item?.let {
                    e.handler.addTo(global.itemTagName, it)
                    regular.itemTagNames.forEach { tag -> e.handler.addTo(tag, it) }
                }
            }
        }

        @SubscribeEvent
        fun onBlockTagAddition(e: TagEvent<Block>) = FORMS.forEach { global ->
            global.specialized.forEach inner@{ regular ->
                if (regular.alreadyImplemented) return@inner
                regular.block?.let {
                    e.handler.addTo(global.blockTagName, it)
                    regular.blockTagNames.forEach { tag -> e.handler.addTo(tag, it) }
                }
            }
        }

        @SubscribeEvent
        fun onFluidTagAddition(e: TagEvent<Fluid>) = FORMS.forEach { global ->
            global.specialized.forEach inner@{ regular ->
                if (regular.alreadyImplemented) return@inner
                regular.fluidTagNames.forEach { tag ->
                    regular.stillFluid?.let { e.handler.addTo(tag, it) }
                    regular.flowingFluid?.let { e.handler.addTo(tag, it) }
                }
            }
        }
    }
}