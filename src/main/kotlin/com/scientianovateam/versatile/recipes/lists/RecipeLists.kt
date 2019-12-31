package com.scientianovateam.versatile.recipes.lists

import com.scientianovateam.versatile.common.extensions.toResLoc
import net.minecraft.util.ResourceLocation

object RecipeLists {
    private val map = mutableMapOf<ResourceLocation, IRecipeLIst>()

    @JvmStatic
    operator fun get(key: ResourceLocation) = map[key]

    @JvmStatic
    operator fun get(key: String) = map[key.toResLoc()]

    @JvmStatic
    operator fun plusAssign(list: IRecipeLIst) {
        map[list.name] = list
    }

    @JvmStatic
    val all
        get() = map.values
}