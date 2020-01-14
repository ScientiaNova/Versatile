package com.scientianovateam.versatile.items.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.items.properties.ArmorItemProperties
import net.minecraft.block.BlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

class ArmorItemV(val armorProperties: ArmorItemProperties) : ArmorItem(armorProperties.tier, armorProperties.slotType, armorProperties), ISerializableItem {
    override fun getArmorTexture(stack: ItemStack?, entity: Entity?, slot: EquipmentSlotType?, type: String?) =
            "${registryName?.namespace}:textures/models/armor/${armorMaterial.name}_layer_${if (slot == EquipmentSlotType.LEGS) 2 else 1}" +
                    "${type?.let { "_$it" } ?: ""}.png"

    override fun getDestroySpeed(stack: ItemStack, state: BlockState) = armorProperties.destroySpeed
    override fun hasContainerItem(stack: ItemStack?) = armorProperties.containerItem != null
    override fun getContainerItem(itemStack: ItemStack?): ItemStack = armorProperties.containerItem?.toStack()
            ?: ItemStack.EMPTY

    override fun addInformation(stack: ItemStack, world: World?, tooltips: MutableList<ITextComponent>, flat: ITooltipFlag) {
        tooltips.addAll(armorProperties.tooltips)
    }

    override fun getTranslationKey(): String = armorProperties.translationKey ?: super.getTranslationKey()
    override fun hasEffect(stack: ItemStack) = armorProperties.glows || super.hasEffect(stack)
    override fun isEnchantable(p_77616_1_: ItemStack) = armorProperties.isEnchantable
    override fun getItemEnchantability(stack: ItemStack?) = armorProperties.enchantability
    override fun getEntityLifespan(itemStack: ItemStack?, world: World?) = armorProperties.entityLifespan
    override fun isBookEnchantable(stack: ItemStack?, book: ItemStack?) = armorProperties.isBookEnchantable
    override fun getBurnTime(itemStack: ItemStack?) = armorProperties.burnTime
    private var localizationFunction: () -> ITextComponent = { TranslationTextComponent(translationKey) }
    override fun getDisplayName(stack: ItemStack) = localizationFunction()
    override fun setLocalization(function: () -> ITextComponent) {
        localizationFunction = function
    }

    override val serializer = Serializer

    object Serializer : IRegisterableJSONSerializer<ArmorItemV, JsonObject> {
        override val registryName = "armor".toResLocV()

        override fun read(json: JsonObject) = ArmorItemV(ArmorItemProperties.Serializer.read(json))

        override fun write(obj: ArmorItemV) = json {
            "type" to "armor"
            ArmorItemProperties.Serializer.write(obj.armorProperties).extract()
        }
    }
}