package com.scientianovateam.versatile

import com.scientianovateam.versatile.blocks.SERIALIZED_BLOCKS
import com.scientianovateam.versatile.blocks.properties.renderTypeFromString
import com.scientianovateam.versatile.common.loaders.loadForms
import com.scientianovateam.versatile.common.loaders.loadMaterials
import com.scientianovateam.versatile.common.loaders.loadProperties
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.fluids.FluidEventBusSubscriber
import com.scientianovateam.versatile.machines.BaseMachineRegistry
import com.scientianovateam.versatile.machines.gui.BaseScreen
import com.scientianovateam.versatile.machines.packets.NetworkHandler
import com.scientianovateam.versatile.materialsystem.commands.FluidContainerCommand
import com.scientianovateam.versatile.materialsystem.commands.FormCommand
import com.scientianovateam.versatile.materialsystem.commands.MaterialCommand
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.proxy.ClientProxy
import com.scientianovateam.versatile.proxy.IModProxy
import com.scientianovateam.versatile.proxy.ServerProxy
import com.scientianovateam.versatile.recipes.lists.IRecipeLIst
import com.scientianovateam.versatile.recipes.lists.RecipeLists
import com.scientianovateam.versatile.recipes.syncing.MaterialBasedRecipe
import com.scientianovateam.versatile.recipes.syncing.SingleRecipe
import com.scientianovateam.versatile.recipes.syncing.VanillaRecipeRegistry
import com.scientianovateam.versatile.resources.BaseDataAddition
import com.scientianovateam.versatile.resources.FakeDataPackFinder
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraft.block.Block
import net.minecraft.client.gui.ScreenManager
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.RecipesUpdatedEvent
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
        override fun createIcon() = ItemStack(MaterialItems.all.firstOrNull { it.registryName?.namespace == MOD_ID }
                ?: Items.AIR)
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
        SERIALIZED_BLOCKS.forEach { block ->
            RenderTypeLookup.setRenderLayer(block as Block, renderTypeFromString(block.renderType))
        }
    }

    private fun commonSetup() {
        NetworkHandler
    }

    private fun enqueueIMC(e: InterModEnqueueEvent) {
        proxy.enqueue(e)
    }

    private fun processIMC(e: InterModProcessEvent) {
        proxy.process(e)
        BaseDataAddition.register()
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
    object RegistryEvents {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onEarlyBlockRegistry(e: RegistryEvent.Register<Block>) {
            ModLoader.get().postEvent(VersatileRegistryEvent())
            loadProperties()
            loadMaterials()
            loadForms()
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateFluidRegistry(e: RegistryEvent.Register<Fluid>) = FluidEventBusSubscriber.registerFluids(e)
    }

    @Suppress("DEPRECATION")
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MOD_ID)
    object GameEvents {
        @SubscribeEvent(priority = EventPriority.HIGH)
        fun onEarlyServerAboutToStart(e: FMLServerAboutToStartEvent) {
            e.server.resourcePacks.addPackFinder(FakeDataPackFinder)
            e.server.recipeManager.recipes = e.server.recipeManager.recipes.mapValues { it.value.toMutableMap() }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLateServerAboutToStart(e: FMLServerAboutToStartEvent) {
            e.server.resourceManager.addReloadListener(net.minecraft.resources.IResourceManagerReloadListener {
                val singleRecipe = e.server.recipeManager.recipes[VanillaRecipeRegistry.SINGLE_RECIPE_TYPE]
                        ?: return@IResourceManagerReloadListener
                e.server.recipeManager.recipes[VanillaRecipeRegistry.MATERIAL_BASED_RECIPE_TYPE]?.forEach {
                    (it.value as? MaterialBasedRecipe)?.eval(singleRecipe)
                }
            })
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onLatestServerAboutToStart(e: FMLServerAboutToStartEvent) {
            RecipeLists.all.forEach(IRecipeLIst::clear)
            e.server.resourceManager.addReloadListener(net.minecraft.resources.IResourceManagerReloadListener {
                e.server.recipeManager.recipes[VanillaRecipeRegistry.SINGLE_RECIPE_TYPE]?.forEach {
                    val recipe = (it.value as? SingleRecipe)?.recipe ?: return@forEach
                    recipe.recipeList.addRecipe(recipe, e.server.recipeManager.recipes)
                }
            })
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onRecipesUpdated(e: RecipesUpdatedEvent) {
            RecipeLists.all.forEach(IRecipeLIst::clear)
            e.recipeManager.recipes[VanillaRecipeRegistry.SINGLE_RECIPE_TYPE]?.forEach {
                val recipe = (it.value as? SingleRecipe)?.recipe ?: return@forEach
                recipe.recipeList.addRecipe(recipe, null)
            }
        }

        @SubscribeEvent
        fun onServerStart(e: FMLServerStartingEvent) {
            MaterialCommand(e.commandDispatcher)
            FormCommand(e.commandDispatcher)
            FluidContainerCommand(e.commandDispatcher)
        }
    }
}