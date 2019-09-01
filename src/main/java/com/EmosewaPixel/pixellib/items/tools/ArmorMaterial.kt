package com.EmosewaPixel.pixellib.items.tools

import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.SoundEvent

import java.util.function.Supplier

//This is an implementation of vanilla's IArmorMaterial so you can simply make objects of it
class ArmorMaterial(private val durability: Int, private val damageReduction: Int, private val enchantability: Int, private val soundEvent: SoundEvent, private val repairMaterial: Supplier<Ingredient>, private val name: String, private val toughness: Float) : IArmorMaterial {
    override fun getDurability(slotIn: EquipmentSlotType) =durability

    override fun getDamageReductionAmount(slotIn: EquipmentSlotType) =damageReduction

    override fun getEnchantability() = enchantability

    override fun getSoundEvent() = soundEvent

    override fun getRepairMaterial() = repairMaterial.get()


    override fun getName() = name

    override fun getToughness() = toughness
}
