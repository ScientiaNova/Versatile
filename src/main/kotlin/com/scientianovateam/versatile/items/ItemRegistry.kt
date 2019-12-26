package com.scientianovateam.versatile.items

import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.materialsystem.lists.Forms
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

//This class is used for generating Items for all the possible Material-Object Type combinations
object ItemRegistry {
    fun registerItems(e: RegistryEvent.Register<Item>) {
        MaterialItems.additionSuppliers.cellSet()
                .forEach { MaterialItems.addItem(it.rowKey!!, it.columnKey!!, it.value!!()) }
        Materials.all.forEach { mat ->
            Forms.all.filter { type -> type.isMaterialCompatible(mat) && !MaterialItems.contains(mat, type) && if (mat.invertedBlacklist) type in mat.typeBlacklist else type !in mat.typeBlacklist }
                    .forEach { type -> type.itemConstructor?.invoke(mat)?.let { e.registry.register(it) } }
        }
    }
}