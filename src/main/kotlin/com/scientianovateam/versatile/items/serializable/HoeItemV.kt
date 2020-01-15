package com.scientianovateam.versatile.items.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.items.properties.ToolTierBasedProperties
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

class HoeItemV(val tierBasedProperties: ToolTierBasedProperties, val attackSpeed: Float = tierBasedProperties.tier.attackDamage + 1) : HoeItem(tierBasedProperties.tier, attackSpeed, tierBasedProperties), ISerializableItem {
    init {
        registryName = tierBasedProperties.name.toResLoc()
    }

    override fun hasContainerItem(stack: ItemStack?) = tierBasedProperties.containerItem != null

    override fun getContainerItem(itemStack: ItemStack?): ItemStack = tierBasedProperties.containerItem?.toStack()
            ?: ItemStack.EMPTY

    override fun addInformation(stack: ItemStack, world: World?, tooltips: MutableList<ITextComponent>, flat: ITooltipFlag) {
        tooltips.addAll(tierBasedProperties.tooltips)
    }

    override fun getTranslationKey(): String = tierBasedProperties.translationKey
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

    object Serializer : IRegisterableJSONSerializer<HoeItemV, JsonObject> {
        override val registryName = "hoe".toResLocV()

        override fun read(json: JsonObject): HoeItemV {
            val properties = ToolTierBasedProperties.Serializer.read(json)
            return HoeItemV(properties, json.getFloatOrNull("attack_speed") ?: properties.tier.attackDamage + 1)
        }

        override fun write(obj: HoeItemV) = json {
            "type" to "hoe"
            ToolTierBasedProperties.Serializer.write(obj.tierBasedProperties).extract()
            "attack_speed" to obj.attackSpeed
        }
    }
}