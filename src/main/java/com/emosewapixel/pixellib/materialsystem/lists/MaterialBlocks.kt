package com.emosewapixel.pixellib.materialsystem.lists

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.main.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import com.emosewapixel.pixellib.materialsystem.main.ct.BlockSupplier
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.block.Block
import net.minecraft.item.Items
import org.openzen.zencode.java.ZenCodeGlobals
import org.openzen.zencode.java.ZenCodeType

//This class contains functions for interacting with the global list of material blocks
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.lists.MaterialBlocks")
object MaterialBlocks {
    private val materialBlocks = HashBasedTable.create<Material, ObjectType, Block>()
    val additionSuppliers = HashBasedTable.create<Material, ObjectType, BlockSupplier>()

    @ZenCodeGlobals.Global("materialBlocks")
    val instance = this

    @JvmStatic
    val all: MutableCollection<Block>
        @ZenCodeType.Getter get() = materialBlocks.values()

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(material: Material, type: ObjectType): Block? = materialBlocks.get(material, type)

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(material: Material): MutableMap<ObjectType, Block>? = materialBlocks.row(material)

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(type: ObjectType): MutableMap<Material, Block>? = materialBlocks.column(type)

    @JvmStatic
    @ZenCodeType.Method
    fun contains(material: Material, type: ObjectType) = materialBlocks.contains(material, type)

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    operator fun contains(block: Block) = block in materialBlocks.values()

    @JvmStatic
    fun addBlock(mat: Material, type: ObjectType, block: Block) {
        materialBlocks.put(mat, type, block)
        if (block.asItem() != Items.AIR)
            MaterialItems.addItem(mat, type, block.asItem())
    }

    @JvmStatic
    @ZenCodeType.Method
    fun addBlock(mat: Material, type: ObjectType, block: BlockSupplier) = additionSuppliers.put(mat, type, block)

    @JvmStatic
    fun <O> addBlock(block: O) where O : IMaterialObject, O : Block = materialBlocks.put(block.mat, block.objType, block)

    @JvmStatic
    fun getBlockCell(block: Block): Table.Cell<Material, ObjectType, Block>? = materialBlocks.cellSet().firstOrNull { it.value === block }

    @JvmStatic
    @ZenCodeType.Method
    fun getBlockMaterial(block: Block): Material? = if (block is IMaterialObject) block.mat else getBlockCell(block)?.rowKey

    @JvmStatic
    @ZenCodeType.Method
    fun getBlockObjType(block: Block): ObjectType? = if (block is IMaterialObject) block.objType else getBlockCell(block)?.columnKey
}