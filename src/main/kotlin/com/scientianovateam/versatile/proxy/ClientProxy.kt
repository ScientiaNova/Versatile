package com.scientianovateam.versatile.proxy

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.resources.FakeResourcePackFinder
import com.scientianovateam.versatile.resources.JSONAdder
import net.minecraft.block.Block
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ClientProxy : IModProxy {
    override fun init() {
        Minecraft.getInstance()?.resourcePackList?.addPackFinder(FakeResourcePackFinder)
    }

    @SubscribeEvent
    fun colorItems(e: ColorHandlerEvent.Item) {
        MaterialItems.all.filterIsInstance<IMaterialObject>().forEach {
            e.itemColors.register(IItemColor { stack, index ->
                val sItem = stack.item as IMaterialObject
                if (index !in sItem.form.indexBlackList)
                    sItem.form.color(sItem.mat)
                else -1
            }, it as Item)
        }
    }

    @SubscribeEvent
    fun colorBlocks(e: ColorHandlerEvent.Block) {
        MaterialBlocks.all.filterIsInstance<IMaterialObject>().forEach {
            e.blockColors.register(IBlockColor { state, _, _, index ->
                val sBlock = state.block as IMaterialObject
                if (index !in sBlock.form.indexBlackList)
                    sBlock.form.color(sBlock.mat)
                else -1
            }, it as Block)
        }
    }
}

fun addModelJSONs() {
    MaterialItems.all.filterIsInstance<IMaterialObject>().forEach {
        val registryName = (it as Item).registryName!!
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), it.form.itemModel(it.mat))
    }

    MaterialBlocks.all.filter { it is IMaterialObject && it !is FlowingFluidBlock }.forEach {
        val registryName = it.registryName!!
        val type = (it as IMaterialObject).form
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), type.blockStateJSON(it.mat))
    }

    MaterialFluids.all.filterIsInstance<IMaterialObject>().forEach {
        val type = it.form
        val registryName = it.form.registryName(it.mat)
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), type.blockStateJSON(it.mat))
    }
}