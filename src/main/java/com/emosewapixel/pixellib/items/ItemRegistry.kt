package com.emosewapixel.pixellib.items

import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes
import com.emosewapixel.pixellib.materialsystem.types.ItemType
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

//This class is used for generating Items for all the possible Material-Object Type combinations
object ItemRegistry {
    fun registry(e: RegistryEvent.Register<Item>) {
        Materials.getAll().forEach { mat ->
            ObjTypes.getAll()
                    .filter { type -> type is ItemType && type.isMaterialCompatible(mat) && !MaterialItems.contains(mat, type) && type !in mat.typeBlacklist }
                    .forEach { type -> register(MaterialItem(mat, type), e) }
        }
    }

    private fun register(item: Item, e: RegistryEvent.Register<Item>): Item {
        e.registry.register(item)
        return item
    }
}