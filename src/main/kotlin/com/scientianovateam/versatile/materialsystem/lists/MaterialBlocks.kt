package com.scientianovateam.versatile.materialsystem.lists

import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.block.Block
import net.minecraft.item.Items

object MaterialBlocks {
    private val materialBlocks = HashBasedTable.create<Material, Form, Block>()
    val additionSuppliers: HashBasedTable<Material, Form, () -> Block> = HashBasedTable.create<Material, Form, () -> Block>()

    @JvmStatic
    val all: Collection<Block>
        get() = materialBlocks.values()

    @JvmStatic
    operator fun get(material: Material, type: Form): Block? = materialBlocks.get(material, type)

    @JvmStatic
    operator fun get(material: Material): MutableMap<Form, Block>? = materialBlocks.row(material)

    @JvmStatic
    operator fun get(type: Form): MutableMap<Material, Block>? = materialBlocks.column(type)

    @JvmStatic
    fun contains(material: Material, type: Form) = materialBlocks.contains(material, type)

    @JvmStatic
    operator fun contains(block: Block) = block in materialBlocks.values()

    @JvmStatic
    fun addBlock(mat: Material, type: Form, block: Block) {
        materialBlocks.put(mat, type, block)
        if (block.asItem() != Items.AIR)
            MaterialItems.addItem(mat, type, block.asItem())
    }

    @JvmStatic
    fun addBlock(mat: Material, type: Form, block: () -> Block) = additionSuppliers.put(mat, type, block)

    @JvmStatic
    fun <O> addBlock(block: O) where O : IMaterialObject, O : Block = materialBlocks.put(block.mat, block.objType, block)

    @JvmStatic
    fun getBlockCell(block: Block): Table.Cell<Material, Form, Block>? = materialBlocks.cellSet().firstOrNull { it.value === block }

    @JvmStatic
    fun getBlockMaterial(block: Block): Material? = if (block is IMaterialObject) block.mat else block.tags.asSequence()
            .filter { '/' in it.path }.map { Materials[it.path.takeLastWhile { char -> char != '/' }] }.firstOrNull()

    @JvmStatic
    fun getBlockForm(block: Block): Form? = if (block is IMaterialObject) block.objType else getBlockCell(block)?.columnKey
}