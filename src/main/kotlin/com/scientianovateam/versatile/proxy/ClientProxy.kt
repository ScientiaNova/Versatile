package com.scientianovateam.versatile.proxy

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.materialsystem.lists.FORMS
import com.scientianovateam.versatile.resources.FakeResourcePackFinder
import com.scientianovateam.versatile.resources.JSONAdder
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.ForgeRegistries

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ClientProxy : IModProxy {
    override fun init() {
        Minecraft.getInstance()?.resourcePackList?.addPackFinder(FakeResourcePackFinder)
    }

    @SubscribeEvent
    fun colorItems(e: ColorHandlerEvent.Item) = FORMS.forEach { form ->
        val indexBlacklist = form.indexBlackList
        form.properties.keys.forEach { mat ->
            ForgeRegistries.ITEMS.getValue(form.registryName(mat))?.let { item ->
                e.itemColors.register(IItemColor { _, index ->
                    if (index !in indexBlacklist) form.color(mat) else -1
                }, item)
            }
        }
    }

    @SubscribeEvent
    fun colorBlocks(e: ColorHandlerEvent.Block) = FORMS.forEach { form ->
        val indexBlacklist = form.indexBlackList
        form.properties.keys.forEach { mat ->
            ForgeRegistries.BLOCKS.getValue(form.registryName(mat))?.let { block ->
                e.blockColors.register(IBlockColor { _, _, _, index ->
                    if (index !in indexBlacklist) form.color(mat) else -1
                }, block)
            }
        }
    }
}

fun addModelJSONs() {
    FORMS.forEach { form ->
        form.properties.keys.forEach { mat ->
            val registryName = form.registryName(mat)
            if (ForgeRegistries.ITEMS.containsKey(registryName))
                JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), form.itemModel(mat))
            if (ForgeRegistries.BLOCKS.containsKey(registryName)) {
                JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), form.blockstateJSON(mat))
                form.blockModels(mat).forEach { (name, json) ->
                    JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/block/" + registryName.path + (if (name.isEmpty()) "" else "_$name") + ".json"), json)
                }
            }
        }
    }
}