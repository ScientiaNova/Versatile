package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.block.Block
import net.minecraft.item.Items

//This class contains functions for interacting with the global list of material blocks
object MaterialBlocks {
    private val materialBlocks = HashBasedTable.create<Material, ObjectType<*, *>, Block>()

    @JvmStatic
    val all: MutableCollection<Block>
        get() = materialBlocks.values()

    @JvmStatic
    operator fun get(material: Material, type: ObjectType<*, *>): Block? = materialBlocks.get(material, type)

    @JvmStatic
    operator fun get(material: Material): MutableMap<ObjectType<*, *>, Block>? = materialBlocks.row(material)

    @JvmStatic
    operator fun get(type: ObjectType<*, *>): MutableMap<Material, Block>? = materialBlocks.column(type)

    @JvmStatic
    fun contains(material: Material, type: ObjectType<*, *>) = get(material, type) != null

    @JvmStatic
    operator fun contains(block: Block) = block in materialBlocks.values()

    @JvmStatic
    fun addBlock(mat: Material, type: ObjectType<*, *>, block: Block) {
        materialBlocks.put(mat, type, block)
        if (block.asItem() != Items.AIR)
            MaterialItems.addItem(mat, type, block.asItem())
    }

    @JvmStatic
    fun <O> addBlock(item: O) where O : IMaterialObject, O : Block = materialBlocks.put(item.mat, item.objType, item)

    @JvmStatic
    fun getBlockCell(block: Block): Table.Cell<Material, ObjectType<*, *>, Block>? = materialBlocks.cellSet().firstOrNull { it.value === block }

    @JvmStatic
    fun getBlockMaterial(block: Block): Material? = if (block is IMaterialObject) block.mat else getBlockCell(block)?.rowKey

    @JvmStatic
    fun getBlockObjType(block: Block): ObjectType<*, *>? = if (block is IMaterialObject) block.objType else getBlockCell(block)?.columnKey
}