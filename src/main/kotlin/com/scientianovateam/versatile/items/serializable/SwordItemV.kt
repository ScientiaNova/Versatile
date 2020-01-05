package com.scientianovateam.versatile.items.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getFloatOrNull
import com.scientianovateam.versatile.common.extensions.getIntOrNull
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.items.properties.ToolTierBasedProperties
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.item.SwordItem
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World

class SwordItemV(val tierBasedProperties: ToolTierBasedProperties, val extraAttackDamage: Int = 3, val attackSpeed: Float = 2.4f) : SwordItem(tierBasedProperties.tier, extraAttackDamage, attackSpeed, tierBasedProperties) {
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

    object Serializer : IRegisterableJSONSerializer<SwordItemV, JsonObject> {
        override val registryName = "sword".toResLocV()

        override fun read(json: JsonObject) = SwordItemV(
                ToolTierBasedProperties.Serializer.read(json),
                json.getIntOrNull("extra_attack_damage") ?: 3,
                json.getFloatOrNull("attack_speed") ?: -2.4f
        )

        override fun write(obj: SwordItemV) = ToolTierBasedProperties.Serializer.write(obj.tierBasedProperties).apply {
            addProperty("extra_attack_damage", obj.extraAttackDamage)
            addProperty("attack_speed", obj.attackSpeed)
        }
    }
}