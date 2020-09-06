package com.scientianova.versatile.materialsystem.materials

import com.scientianova.versatile.common.registry.materials
import net.minecraft.item.Item

val Item.material
    get() = tags.asSequence()
            .filter { '/' in it.path }.map { materials[it.path.takeLastWhile { char -> char != '/' }] }.firstOrNull()