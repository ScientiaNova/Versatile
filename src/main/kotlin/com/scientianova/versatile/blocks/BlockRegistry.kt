package com.scientianova.versatile.blocks

import com.scientianova.versatile.materialsystem.lists.Forms
import com.scientianova.versatile.materialsystem.lists.MaterialBlocks
import com.scientianova.versatile.materialsystem.lists.Materials
import net.minecraft.block.Block
import net.minecraftforge.event.RegistryEvent

//This class is used for generating Blocks for all the possible Material-Object Type combinations
fun registerBlocks(e: RegistryEvent.Register<Block>) {
    MaterialBlocks.additionSuppliers.cellSet()
            .forEach { MaterialBlocks.addBlock(it.rowKey!!, it.columnKey!!, it.value!!()) }
    Materials.all.forEach { mat ->
        Forms.all.forEach { form -> form[mat]?.block?.let { e.registry.register(it) } }
    }
}