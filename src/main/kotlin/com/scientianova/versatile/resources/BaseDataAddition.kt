package com.scientianova.versatile.resources

import com.scientianova.versatile.materialsystem.lists.allForms
import net.minecraft.util.ResourceLocation

fun registerData() {
    allForms.forEach { global ->
        global.specialized.forEach inner@{ regular ->
            if (regular.alreadyImplemented) return@inner
            regular.item?.let {
                addItemToTag(global.itemTagName, it)
                regular.itemTagNames.forEach { tag -> addItemToTag(tag, it) }
            }
            regular.block?.let {
                addBlockToTag(global.blockTagName, it)
                regular.blockTagNames.forEach { tag -> addBlockToTag(tag, it) }
            }
            regular.stillFluid?.let {
                regular.fluidTagNames.forEach { tag ->
                    addFluidToTag(tag, it)
                }
            }
            regular.flowingFluid?.let {
                regular.fluidTagNames.forEach { tag ->
                    addFluidToTag(tag, it)
                }
            }
        }
    }
}

private fun location(name: String) = ResourceLocation("versatile", name)