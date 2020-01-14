package com.scientianovateam.versatile.items.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getFloatOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.items.properties.ToolTierBasedProperties
import net.minecraft.block.BlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.item.ShovelItem
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

class ShovelItemV(val tierBasedProperties: ToolTierBasedProperties, val extraAttackDamage: Float = 2.5f, attackSpeed: Float = -3f) : ShovelItem(tierBasedProperties.tier, tierBasedProperties.tier.attackDamage + extraAttackDamage, attackSpeed, tierBasedProperties), ISerializableItem {
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
    private var localizationFunction: () -> ITextComponent = { TranslationTextComponent(translationKey) }
    override fun getDisplayName(stack: ItemStack) = localizationFunction()
    override fun setLocalization(function: () -> ITextComponent) {
        localizationFunction = function
    }

    override val serializer = Serializer

    object Serializer : IRegisterableJSONSerializer<ShovelItemV, JsonObject> {
        override val registryName = "shovel".toResLocV()

        override fun read(json: JsonObject) = ShovelItemV(
                ToolTierBasedProperties.Serializer.read(json),
                json.getFloatOrNull("extra_attack_damage") ?: 2.5f,
                json.getFloatOrNull("attack_speed") ?: -3f
        )

        override fun write(obj: ShovelItemV) = json {
            "type" to "shovel"
            ToolTierBasedProperties.Serializer.write(obj.tierBasedProperties).extract()
            "extra_attack_damage" to obj.extraAttackDamage
            "attack_speed" to obj.attackSpeed
        }
    }
}