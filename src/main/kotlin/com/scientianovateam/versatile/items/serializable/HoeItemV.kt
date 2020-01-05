package com.scientianovateam.versatile.items.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getFloatOrNull
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.items.properties.ToolTierBasedProperties
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World

class HoeItemV(val tierBasedProperties: ToolTierBasedProperties, val attackSpeed: Float = tierBasedProperties.tier.attackDamage + 1) : HoeItem(tierBasedProperties.tier, attackSpeed, tierBasedProperties) {
    override fun hasContainerItem(stack: ItemStack?) = tierBasedProperties.containerItem != null

    override fun getContainerItem(itemStack: ItemStack?): ItemStack = tierBasedProperties.containerItem?.toStack()
            ?: ItemStack.EMPTY

    override fun addInformation(stack: ItemStack, world: World?, tooltips: MutableList<ITextComponent>, flat: ITooltipFlag) {
        tooltips.addAll(tierBasedProperties.tooltips)
    }

    override fun getTranslationKey(): String = tierBasedProperties.translationKey ?: super.getTranslationKey()
    override fun hasEffect(stack: ItemStack) = tierBasedProperties.glows || super.hasEffect(stack)
    override fun isEnchantable(stack: ItemStack) = tierBasedProperties.isEnchantable
    override fun getItemEnchantability(stack: ItemStack?) = tierBasedProperties.enchantability

    override fun getEntityLifespan(itemStack: ItemStack?, world: World?) = tierBasedProperties.entityLifespan
    override fun isBookEnchantable(stack: ItemStack?, book: ItemStack?) = tierBasedProperties.isBookEnchantable
    override fun getBurnTime(itemStack: ItemStack?) = tierBasedProperties.burnTime

    object Serializer : IRegisterableJSONSerializer<HoeItemV, JsonObject> {
        override val registryName = "hoe".toResLocV()

        override fun read(json: JsonObject): HoeItemV {
            val properties = ToolTierBasedProperties.Serializer.read(json)
            return HoeItemV(properties, json.getFloatOrNull("attack_speed") ?: properties.tier.attackDamage + 1)
        }

        override fun write(obj: HoeItemV) = ToolTierBasedProperties.Serializer.write(obj.tierBasedProperties).apply {
            addProperty("attack_speed", obj.attackSpeed)
        }
    }
}