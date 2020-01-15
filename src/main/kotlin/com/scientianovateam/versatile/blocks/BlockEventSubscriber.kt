package com.scientianovateam.versatile.blocks

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.blocks.serializable.RegularBlock
import com.scientianovateam.versatile.blocks.serializable.deserializeBlock
import com.scientianovateam.versatile.common.extensions.forEach
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.materialsystem.lists.FORMS
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.recipes.registerAll
import net.minecraft.block.Block
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
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
        SERIALIZED_BLOCKS.forEach { e.registry.register(it as Block) }
        MaterialBlocks.additionSuppliers.forEach { MaterialBlocks.addBlock(it.rowKey!!, it.columnKey!!, it.value!!()) }
        FORMS.forEach { form ->
            form.properties.keys.forEach material@{ mat ->
                if (MaterialBlocks.contains(mat, form)) return@material
                val block = deserializeBlock(form.blockJSON(mat))
                block.registryName = form.registryName(mat)
                block.setLocalization {
                    if (LanguageMap.getInstance().exists((block as Block).translationKey)) TranslationTextComponent(block.translationKey)
                    else form.localize(mat)
                }
                e.registry.register(block as Block)
            }
        }
    }
}