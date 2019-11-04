package com.emosewapixel.pixellib.materialsystem.lists

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.materials.utility.ct.ItemSupplier
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.item.Item
import org.openzen.zencode.java.ZenCodeGlobals
import org.openzen.zencode.java.ZenCodeType

//This class contains functions for interacting with the global list of material items
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.lists.MaterialItems")
object MaterialItems {
    private val materialItems = HashBasedTable.create<Material, ObjectType<*, *>, Item>()
    val additionSuppliers = HashBasedTable.create<Material, ObjectType<*, *>, ItemSupplier>()

    @ZenCodeGlobals.Global("materialBlocks")
    val instance = this

    @JvmStatic
    val all: Collection<Item>
        @ZenCodeType.Getter get() = materialItems.values()

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(material: Material, type: ObjectType<*, *>): Item? = materialItems.get(material, type)

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(material: Material): MutableMap<ObjectType<*, *>, Item>? = materialItems.row(material)

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(type: ObjectType<*, *>): MutableMap<Material, Item>? = materialItems.column(type)

    @JvmStatic
    @ZenCodeType.Method
    fun contains(material: Material, type: ObjectType<*, *>) = materialItems.contains(material, type)

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    operator fun contains(item: Item) = item in materialItems.values()

    @JvmStatic
    fun addItem(mat: Material, type: ObjectType<*, *>, item: Item) = materialItems.put(mat, type, item)

    @JvmStatic
    @ZenCodeType.Method
    fun addItem(mat: Material, type: ObjectType<*, *>, item: ItemSupplier) = additionSuppliers.put(mat, type, item)

    @JvmStatic
    @ZenCodeType.Method
    fun <O> addItem(item: O) where O : IMaterialObject, O : Item = addItem(item.mat, item.objType, item)

    @JvmStatic
    fun getItemCell(item: Item): Table.Cell<Material, ObjectType<*, *>, Item>? = materialItems.cellSet().firstOrNull { it.value === item }

    @JvmStatic
    @ZenCodeType.Method
    fun getItemMaterial(item: Item): Material? = if (item is IMaterialObject) item.mat else getItemCell(item)?.rowKey

    @JvmStatic
    @ZenCodeType.Method
    fun getItemObjType(item: Item): ObjectType<*, *>? = if (item is IMaterialObject) item.objType else getItemCell(item)?.columnKey
}