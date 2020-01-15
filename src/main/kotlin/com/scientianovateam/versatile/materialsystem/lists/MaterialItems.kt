package com.scientianovateam.versatile.materialsystem.lists

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.Material
import net.minecraft.item.Item

object MaterialItems {
    private val materialItems = HashBasedTable.create<Material, Form, Item>()
    val additionSuppliers: HashBasedTable<Material, Form, () -> Item> = HashBasedTable.create<Material, Form, () -> Item>()

    @JvmStatic
    val all: Collection<Item>
        get() = materialItems.values()

    @JvmStatic
    operator fun get(material: Material, type: Form): Item? = materialItems.get(material, type)

    @JvmStatic
    operator fun get(material: Material): MutableMap<Form, Item>? = materialItems.row(material)

    @JvmStatic
    operator fun get(type: Form): MutableMap<Material, Item>? = materialItems.column(type)

    @JvmStatic
    fun contains(material: Material, type: Form) = materialItems.contains(material, type)

    @JvmStatic
    operator fun contains(item: Item) = item in materialItems.values()

    @JvmStatic
    fun addItem(mat: Material, type: Form, item: Item) = materialItems.put(mat, type, item)

    @JvmStatic
    fun addItem(mat: Material, type: Form, item: () -> Item) = additionSuppliers.put(mat, type, item)

    @JvmStatic
    fun getItemCell(item: Item): Table.Cell<Material, Form, Item>? = materialItems.cellSet().firstOrNull { it.value === item }

    @JvmStatic
    fun getItemMaterial(item: Item): Material? = item.tags.asSequence().filter { '/' in it.path }
            .map { MATERIALS[it.path.takeLastWhile { char -> char != '/' }] }.firstOrNull()

    @JvmStatic
    fun getItemForm(item: Item): Form? = getItemCell(item)?.columnKey
}