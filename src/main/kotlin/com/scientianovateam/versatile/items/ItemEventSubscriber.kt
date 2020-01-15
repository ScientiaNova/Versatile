package com.scientianovateam.versatile.items

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.forEach
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.items.serializable.*
import com.scientianovateam.versatile.materialsystem.lists.FORMS
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.proxy.addModelJSONs
import com.scientianovateam.versatile.recipes.registerAll
import net.minecraft.item.Item
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
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
                BlockItemV.Serializer,
                HoeItemV.Serializer,
                PickaxeItemV.Serializer,
                ShovelItemV.Serializer,
                SwordItemV.Serializer,
                ArmorItemV.Serializer
        )
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onLateItemRegistry(e: RegistryEvent.Register<Item>) {
        SERIALIZED_ITEMS.forEach { e.registry.register(it as Item) }
        MaterialItems.additionSuppliers.forEach { MaterialItems.addItem(it.rowKey!!, it.columnKey!!, it.value!!()) }
        FORMS.forEach { form ->
            form.properties.keys.forEach material@{ mat ->
                if (MaterialItems.contains(mat, form)) return@material
                val item = deserializeItem(form.itemJSON(mat))
                item.registryName = form.registryName(mat)
                item.setLocalization {
                    if (LanguageMap.getInstance().exists((item as Item).translationKey)) TranslationTextComponent(item.translationKey)
                    else form.localize(mat)
                }
                e.registry.register(item as Item)
            }
        }
        addModelJSONs()
    }
}