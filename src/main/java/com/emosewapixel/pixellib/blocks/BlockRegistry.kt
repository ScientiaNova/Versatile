package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

//This class is used for generating Blocks for all the possible Material-Object Type combinations
object BlockRegistry {
    private val items = mutableListOf<Item>()

    fun registerBlocks(e: RegistryEvent.Register<Block>) {
        Materials.all.forEach { mat ->
            ObjTypes.all.filter { type -> type is BlockType && type.isMaterialCompatible(mat) && !MaterialBlocks.contains(mat, type) && if (mat.invertedBlacklist) type in mat.typeBlacklist else type !in mat.typeBlacklist }
                    .forEach { type ->
                        e.registry.register((type as BlockType).objectConstructor(mat, type))
                        items += type.itemConstructor(mat, type)
                    }
        }
    }

    fun registerItems(e: RegistryEvent.Register<Item>) = items.forEach { e.registry.register(it) }
}