package com.scientianova.versatile.proxy

import com.scientianova.versatile.materialsystem.lists.MaterialBlocks
import com.scientianova.versatile.materialsystem.lists.MaterialFluids
import com.scientianova.versatile.materialsystem.lists.MaterialItems
import com.scientianova.versatile.materialsystem.main.IMaterialObject
import com.scientianova.versatile.resources.FakeResourcePackFinder
import com.scientianova.versatile.resources.JSONAdder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent

object ClientProxy : IModProxy {
    override fun init() {
        Minecraft.getInstance().resourcePackList.addPackFinder(FakeResourcePackFinder)
    }

    override fun enque(e: InterModEnqueueEvent) {
        color()
    }

    override fun process(e: InterModProcessEvent) {}

    private fun color() {
        MaterialItems.all.filterIsInstance<IMaterialObject>().forEach {
            Minecraft.getInstance().itemColors.register(IItemColor { stack: ItemStack, index ->
                val sItem = stack.item as IMaterialObject
                if (index !in sItem.form.global.indexBlacklist)
                    sItem.form.color
                else -1
            }, it as Item)
        }

        MaterialBlocks.all.filterIsInstance<IMaterialObject>().forEach {
            Minecraft.getInstance().blockColors.register(IBlockColor { state: BlockState, _, _, index: Int ->
                val sBlock = state.block as IMaterialObject
                if (index !in sBlock.form.global.indexBlacklist)
                    sBlock.form.color
                else -1
            }, it as Block)
        }
    }
}

fun addModelJSONs() {
    MaterialItems.all.filterIsInstance<IMaterialObject>().forEach {
        val registryName = (it as Item).registryName!!
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "models/item/" + registryName.path + ".json"), it.form.itemModel)
    }

    MaterialBlocks.all.filter { it is IMaterialObject && it !is FlowingFluidBlock }.forEach {
        val registryName = it.registryName!!
        val form = (it as IMaterialObject).form
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), form.blockStateJSON)
    }

    MaterialFluids.all.filterIsInstance<IMaterialObject>().forEach {
        val form = it.form
        val registryName = it.form.registryName
        JSONAdder.addAssetsJSON(ResourceLocation(registryName.namespace, "blockstates/" + registryName.path + ".json"), form.blockStateJSON)
    }
}