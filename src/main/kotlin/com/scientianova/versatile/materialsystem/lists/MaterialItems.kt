package com.scientianova.versatile.materialsystem.lists

import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.ObjectType
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.item.Item

object MaterialItems {
    private val materialItems = HashBasedTable.create<Material, ObjectType, Item>()
    val additionSuppliers: HashBasedTable<Material, ObjectType, () -> Item> = HashBasedTable.create<Material, ObjectType, () -> Item>()

    @JvmStatic
    val all: Collection<Item>
        get() = materialItems.values()

    @JvmStatic
    operator fun get(material: Material, type: ObjectType): Item? = materialItems.get(material, type)

    @JvmStatic
    operator fun get(material: Material): MutableMap<ObjectType, Item>? = materialItems.row(material)

    @JvmStatic
    operator fun get(type: ObjectType): MutableMap<Material, Item>? = materialItems.column(type)

    @JvmStatic
    fun contains(material: Material, type: ObjectType) = materialItems.contains(material, type)

    @JvmStatic
    operator fun contains(item: Item) = item in materialItems.values()

    @JvmStatic
    fun addItem(mat: Material, type: ObjectType, item: Item) = materialItems.put(mat, type, item)

    @JvmStatic
    fun addItem(mat: Material, type: ObjectType, item: () -> Item) = additionSuppliers.put(mat, type, item)

    @JvmStatic
    fun <O> addItem(item: O) where O : IMaterialObject, O : Item = addItem(item.mat, item.objType, item)

    @JvmStatic
    fun getItemCell(item: Item): Table.Cell<Material, ObjectType, Item>? = materialItems.cellSet().firstOrNull { it.value === item }

    @JvmStatic
    fun getItemMaterial(item: Item): Material? = if (item is IMaterialObject) item.mat else item.tags.asSequence()
            .filter { '/' in it.path }.map { Materials[it.path.takeLastWhile { char -> char != '/' }] }.firstOrNull()

    @JvmStatic
    fun getItemObjType(item: Item): ObjectType? = if (item is IMaterialObject) item.objType else getItemCell(item)?.columnKey
}