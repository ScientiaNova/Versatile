package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.event.RegistryEvent

//This class is used for generating Blocks for all the possible Material-Object Type combinations
object BlockRegistry {
    fun registry(e: RegistryEvent.Register<Block>) {
        Materials.getAll().forEach { mat ->
            ObjTypes.getAll()
                    .filter { type -> mat is DustMaterial && type is BlockType && type.isMaterialCompatible(mat) && !MaterialBlocks.contains(mat, type) && type !in mat.typeBlacklist }
                    .forEach { type -> register(MaterialBlock(mat as DustMaterial, type as BlockType), e) }
        }
    }

    fun itemRegistry(e: RegistryEvent.Register<Item>) = MaterialBlocks.getAll().filterIsInstance<IMaterialObject>().forEach { registerItemBlock(it as Block, e) }

    private fun register(block: Block, e: RegistryEvent.Register<Block>): Block {
        e.registry.register(block)
        return block
    }

    private fun registerItemBlock(block: Block, e: RegistryEvent.Register<Item>) =
            e.registry.register(object : BlockItem(block, Properties().group(PixelLib.main)) {
                override fun getDisplayName(stack: ItemStack) = block.nameTextComponent
            }.setRegistryName(block.registryName!!))
}