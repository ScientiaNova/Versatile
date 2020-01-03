package com.scientianovateam.versatile.items.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getPrimitiveOrNull
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.items.properties.ToolTierBasedProperties
import net.minecraft.block.BlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.AxeItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World

class AxeItemV(val tierBasedProperties: ToolTierBasedProperties, val extraAttackDamage: Float = 7f, attackSpeed: Float = -3.1f) : AxeItem(tierBasedProperties.tier, tierBasedProperties.tier.attackDamage + extraAttackDamage, attackSpeed, tierBasedProperties) {
    override fun hasContainerItem(stack: ItemStack?) = tierBasedProperties.containerItem != null

    override fun getContainerItem(itemStack: ItemStack?): ItemStack = tierBasedProperties.containerItem?.toStack()
            ?: ItemStack.EMPTY

    override fun addInformation(stack: ItemStack, world: World?, tooltips: MutableList<ITextComponent>, flat: ITooltipFlag) {
        tooltips.addAll(tierBasedProperties.tooltips)
    }

    override fun getDestroySpeed(stack: ItemStack, state: BlockState): Float {
        val regularSpeed = super.getDestroySpeed(stack, state)
        return if (regularSpeed == efficiency) tierBasedProperties.destroySpeed else regularSpeed
    }

    override fun getTranslationKey(): String = tierBasedProperties.translationKey ?: super.getTranslationKey()
    override fun hasEffect(stack: ItemStack) = tierBasedProperties.glows || super.hasEffect(stack)
    override fun isEnchantable(stack: ItemStack) = tierBasedProperties.isEnchantable
    override fun getItemEnchantability(stack: ItemStack?) = tierBasedProperties.enchantability

    override fun getEntityLifespan(itemStack: ItemStack?, world: World?) = tierBasedProperties.entityLifespan
    override fun isBookEnchantable(stack: ItemStack?, book: ItemStack?) = tierBasedProperties.isBookEnchantable
    override fun getBurnTime(itemStack: ItemStack?) = tierBasedProperties.burnTime

    object Serializer : IRegisterableJSONSerializer<AxeItemV, JsonObject> {
        override val registryName = "axe".toResLocV()

        override fun read(json: JsonObject) = AxeItemV(
                ToolTierBasedProperties.Serializer.read(json),
                json.getPrimitiveOrNull("extra_attack_damage")?.asFloat ?: 7f,
                json.getPrimitiveOrNull("attack_speed")?.asFloat ?: -3.1f
        )

        override fun write(obj: AxeItemV) = ToolTierBasedProperties.Serializer.write(obj.tierBasedProperties).apply {
            addProperty("extra_attack_damage", obj.extraAttackDamage)
            addProperty("attack_speed", obj.attackSpeed)
        }
    }
}