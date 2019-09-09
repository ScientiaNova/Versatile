package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.materialsystem.materials.IMaterialItem
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.item.Item

//This class contains functions for interacting with the global list of material items
object MaterialItems {
    private val materialItems = HashBasedTable.create<Material, ObjectType, Item>()

    @JvmStatic
    fun getAll(): Collection<Item> = materialItems.values()

    @JvmStatic
    fun getItem(material: Material, type: ObjectType): Item? = materialItems.get(material, type)

    @JvmStatic
    fun contains(material: Material, type: ObjectType) = getItem(material, type) != null

    @JvmStatic
    operator fun contains(item: Item) = item in materialItems.values()

    @JvmStatic
    fun addItem(mat: Material, type: ObjectType, item: Item) = materialItems.put(mat, type, item)

    @JvmStatic
    fun addItem(item: IMaterialItem) {
        if (item is Item)
            addItem(item.mat, item.objType, item as Item)
    }

    @JvmStatic
    fun getItemCell(item: Item): Table.Cell<Material, ObjectType, Item>? = materialItems.cellSet().first { it.value === item }

    @JvmStatic
    fun getItemMaterial(item: Item): Material? = if (item is IMaterialItem) item.mat else getItemCell(item)?.rowKey

    @JvmStatic
    fun getItemObjType(item: Item): ObjectType? = if (item is IMaterialItem) item.objType else  getItemCell(item)?.columnKey
}