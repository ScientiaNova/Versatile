package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.materialsystem.main.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.block.Block
import net.minecraft.item.Items

object MaterialBlocks {
    private val materialBlocks = HashBasedTable.create<Material, ObjectType, Block>()
    val additionSuppliers: HashBasedTable<Material, ObjectType, () -> Block> = HashBasedTable.create<Material, ObjectType, () -> Block>()

    @JvmStatic
    val all: Collection<Block>
        get() = materialBlocks.values()

    @JvmStatic
    operator fun get(material: Material, type: ObjectType): Block? = materialBlocks.get(material, type)

    @JvmStatic
    operator fun get(material: Material): MutableMap<ObjectType, Block>? = materialBlocks.row(material)

    @JvmStatic
    operator fun get(type: ObjectType): MutableMap<Material, Block>? = materialBlocks.column(type)

    @JvmStatic
    fun contains(material: Material, type: ObjectType) = materialBlocks.contains(material, type)

    @JvmStatic
    operator fun contains(block: Block) = block in materialBlocks.values()

    @JvmStatic
    fun addBlock(mat: Material, type: ObjectType, block: Block) {
        materialBlocks.put(mat, type, block)
        if (block.asItem() != Items.AIR)
            MaterialItems.addItem(mat, type, block.asItem())
    }

    @JvmStatic
    fun addBlock(mat: Material, type: ObjectType, block: () -> Block) = additionSuppliers.put(mat, type, block)

    @JvmStatic
    fun <O> addBlock(block: O) where O : IMaterialObject, O : Block = materialBlocks.put(block.mat, block.objType, block)

    @JvmStatic
    fun getBlockCell(block: Block): Table.Cell<Material, ObjectType, Block>? = materialBlocks.cellSet().firstOrNull { it.value === block }

    @JvmStatic
    fun getBlockMaterial(block: Block): Material? = if (block is IMaterialObject) block.mat else block.tags.asSequence()
            .filter { '/' in it.path }.map { Materials[it.path.takeLastWhile { char -> char != '/' }] }.firstOrNull()

    @JvmStatic
    fun getBlockObjType(block: Block): ObjectType? = if (block is IMaterialObject) block.objType else getBlockCell(block)?.columnKey
}