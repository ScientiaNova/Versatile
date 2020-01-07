package com.scientianovateam.versatile.items

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.forEach
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.items.serializable.*
import com.scientianovateam.versatile.materialsystem.lists.Forms
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.proxy.addModelJSONs
import com.scientianovateam.versatile.recipes.registerAll
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object ItemEventSubscriber {
    @SubscribeEvent
    fun onVersatileRegistry(e: VersatileRegistryEvent) {
        ITEM_SERIALIZERS.registerAll(
                RegularItem.Serializer,
                AxeItemV.Serializer,
                HoeItemV.Serializer,
                PickaxeItemV.Serializer,
                ShovelItemV.Serializer,
                SwordItemV.Serializer,
                ArmorItemV.Serializer
        )
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onLateItemRegistry(e: RegistryEvent.Register<Item>) {
        SERIALIZED_ITEMS.forEach { e.registry.register(it.value as Item) }
        MaterialItems.additionSuppliers.forEach { MaterialItems.addItem(it.rowKey!!, it.columnKey!!, it.value!!()) }
        Materials.all.forEach { mat ->
            Forms.all.filter { type -> type.isMaterialCompatible(mat) && !MaterialItems.contains(mat, type) && if (mat.invertedBlacklist) type in mat.typeBlacklist else type !in mat.typeBlacklist }
                    .forEach { type -> type.itemConstructor?.invoke(mat)?.let { e.registry.register(it) } }
        }
        addModelJSONs()
    }
}