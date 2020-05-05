package com.scientianova.versatile.proxy

import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.materialsystem.lists.Forms
import com.scientianova.versatile.resources.FakeResourcePackFinder
import com.scientianova.versatile.resources.JSONAdder
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
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
        Forms.all.forEach { global ->
            global.specialized.forEach inner@{ regular ->
                if (regular.alreadyImplemented) return@inner

                val item = regular.item ?: return@inner

                Minecraft.getInstance().itemColors.register(IItemColor { _, index ->
                    if (index !in global.indexBlacklist)
                        regular.color
                    else -1
                }, item)

                val block = regular.block ?: return@inner
                Minecraft.getInstance().blockColors.register(IBlockColor { _, _, _, index ->
                    if (index !in global.indexBlacklist)
                        regular.color
                    else -1
                }, block)
            }
        }
    }
}

fun addModelJSONs() {
    Forms.all.forEach { global ->
        global.specialized.forEach inner@{ regular ->
            if (regular.alreadyImplemented) return@inner

            val itemReg = (regular.item ?: return@inner).registryName!!
            JSONAdder.addAssetsJSON("${itemReg.namespace}:models/item/${itemReg.path}.json".toResLoc(), regular.itemModel)

            val blockReg = (regular.block ?: return@inner).registryName!!
            JSONAdder.addAssetsJSON("${blockReg.namespace}:blockstates/${blockReg.path}.json".toResLoc(), regular.itemModel)
        }
    }
}