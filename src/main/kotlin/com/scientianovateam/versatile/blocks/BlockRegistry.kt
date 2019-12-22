package com.scientianovateam.versatile.blocks

import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.materialsystem.lists.ObjTypes
import net.minecraft.block.Block
import net.minecraftforge.event.RegistryEvent

//This class is used for generating Blocks for all the possible Material-Object Type combinations
object BlockRegistry {
    fun registerBlocks(e: RegistryEvent.Register<Block>) {
        MaterialBlocks.additionSuppliers.cellSet()
                .forEach { MaterialBlocks.addBlock(it.rowKey!!, it.columnKey!!, it.value!!()) }
        Materials.all.forEach { mat ->
            ObjTypes.all.filter { type -> type.isMaterialCompatible(mat) && !MaterialBlocks.contains(mat, type) && if (mat.invertedBlacklist) type in mat.typeBlacklist else type !in mat.typeBlacklist }
                    .forEach { type -> type.blockConstructor?.invoke(mat)?.let { e.registry.register(it) } }
        }
    }
}