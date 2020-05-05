package com.scientianova.versatile.items

import net.minecraft.item.Food
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.Rarity
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.common.ToolType

class ExtendedItemProperties(
        maxStackSize: Int = 64,
        maxDamage: Int = 0,
        containerItem: Item? = null,
        group: ItemGroup? = null,
        rarity: Rarity = Rarity.COMMON,
        food: Food? = null,
        canRepair: Boolean = true,
        toolProperties: Map<ToolType, Int> = emptyMap(),
        val localizedNameFun: Item.() -> ITextComponent? = { TranslationTextComponent(translationKey) },
        val burnTime: Int = 0,
        val destroySpeed: Float = 0f,
        val enchantable: Boolean = maxStackSize == 1 && maxDamage > 1,
        val enchantability: Int = 0,
        val hasEffect: Boolean = false
) : Item.Properties() {
    init {
        maxStackSize(maxStackSize)
        maxDamage(maxDamage)
        containerItem?.let { containerItem(it) }
        group?.let { group(it) }
        rarity(rarity)
        food?.let { food(food) }
        if (!canRepair) setNoRepair()
        toolProperties.forEach { (type, level) -> addToolType(type, level) }
    }
}