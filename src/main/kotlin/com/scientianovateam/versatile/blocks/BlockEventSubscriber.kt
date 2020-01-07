package com.scientianovateam.versatile.blocks

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.blocks.serializable.RegularBlock
import com.scientianovateam.versatile.common.extensions.forEach
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.materialsystem.lists.Forms
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.recipes.registerAll
import net.minecraft.block.Block
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object BlockEventSubscriber {
    @SubscribeEvent
    fun onVersatileRegistry(e: VersatileRegistryEvent) {
        BLOCK_SERIALIZERS.registerAll(RegularBlock.Serializer)
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onLateBlockRegistry(e: RegistryEvent.Register<Block>) {
        SERIALIZED_BLOCKS.forEach { e.registry.register(it.value as Block) }
        MaterialBlocks.additionSuppliers.forEach { MaterialBlocks.addBlock(it.rowKey!!, it.columnKey!!, it.value!!()) }
        Materials.all.forEach { mat ->
            Forms.all.filter { type -> type.isMaterialCompatible(mat) && !MaterialBlocks.contains(mat, type) && if (mat.invertedBlacklist) type in mat.typeBlacklist else type !in mat.typeBlacklist }
                    .forEach { type -> type.blockConstructor?.invoke(mat)?.let { e.registry.register(it) } }
        }
    }
}