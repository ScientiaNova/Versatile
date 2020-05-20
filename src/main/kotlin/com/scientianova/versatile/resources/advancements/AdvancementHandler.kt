package com.scientianova.versatile.resources.advancements

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementList
import net.minecraft.util.ResourceLocation

class AdvancementHandler(private val list: AdvancementList) {
    fun add(advancement: Advancement) {
        list.advancements[advancement.id] = advancement
        if (advancement.parent == null) {
            list.roots += advancement
            list.listener.rootAdvancementAdded(advancement)
        } else {
            list.nonRoots += advancement
            list.listener.nonRootAdvancementAdded(advancement)
        }
    }

    fun remove(advancement: Advancement) {
        advancement.children.forEach(::remove)
        list.advancements.remove(advancement.id)
        if (advancement.parent == null) {
            list.roots.remove(advancement)
            list.listener.rootAdvancementRemoved(advancement)
        } else {
            list.nonRoots.remove(advancement)
            list.listener.nonRootAdvancementRemoved(advancement)
        }
    }

    fun remove(name: ResourceLocation) {
        list.getAdvancement(name)?.let(::remove)
    }
}