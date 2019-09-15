package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.item.Item

//This class contains functions for interacting with the global list of material items
object MaterialItems {
    private val materialItems = HashBasedTable.create<Material, ObjectType<*, *>, Item>()

    @JvmStatic
    val all: Collection<Item>
        get() = materialItems.values()

    @JvmStatic
    operator fun get(material: Material, type: ObjectType<*, *>): Item? = materialItems.get(material, type)

    @JvmStatic
    fun contains(material: Material, type: ObjectType<*, *>) = get(material, type) != null

    @JvmStatic
    operator fun contains(item: Item) = item in materialItems.values()

    @JvmStatic
    fun addItem(mat: Material, type: ObjectType<*, *>, item: Item) = materialItems.put(mat, type, item)

    @JvmStatic
    fun <O> addItem(item: O) where O : IMaterialObject, O : Item = addItem(item.mat, item.objType, item)

    @JvmStatic
    fun getItemCell(item: Item): Table.Cell<Material, ObjectType<*, *>, Item>? = materialItems.cellSet().first { it.value === item }

    @JvmStatic
    fun getItemMaterial(item: Item): Material? = if (item is IMaterialObject) item.mat else getItemCell(item)?.rowKey

    @JvmStatic
    fun getItemObjType(item: Item): ObjectType<*, *>? = if (item is IMaterialObject) item.objType else getItemCell(item)?.columnKey
}