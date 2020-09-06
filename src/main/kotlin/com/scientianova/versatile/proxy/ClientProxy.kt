package com.scientianova.versatile.proxy

import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.common.registry.forms
import com.scientianova.versatile.common.registry.materials
import com.scientianova.versatile.materialsystem.forms.FormInstance
import com.scientianova.versatile.materialsystem.properties.*
import com.scientianova.versatile.resources.FakeResourcePackFinder
import com.scientianova.versatile.resources.addAssetsJSON
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
        forms.forEach { form ->
            materials.forEach inner@ { mat ->
                val instance = FormInstance(mat, form)
                if (instance[alreadyImplemented]) return@inner

                val item = instance[item] ?: return@inner

                Minecraft.getInstance().itemColors.register(IItemColor { _, index ->
                    if (index !in form[indexBlacklist])
                        instance[formColor]
                    else -1
                }, item)

                val block = instance[block] ?: return@inner
                Minecraft.getInstance().blockColors.register(IBlockColor { _, _, _, index ->
                    if (index !in form[indexBlacklist])
                        instance[formColor]
                    else -1
                }, block)
            }
        }
    }
}

fun addModelJSONs() = forms.forEach { form ->
    materials.forEach inner@ { mat ->
        val instance = FormInstance(mat, form)
        if (instance[alreadyImplemented]) return@inner

        val itemReg = (instance[item] ?: return@inner).registryName!!
        addAssetsJSON("${itemReg.namespace}:models/item/${itemReg.path}.json".toResLoc(), instance[itemModel])

        val blockReg = (instance[block] ?: return@inner).registryName!!
        addAssetsJSON("${blockReg.namespace}:blockstates/${blockReg.path}.json".toResLoc(), instance[blockstateJSON])
    }
}