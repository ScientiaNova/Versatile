package com.scientianovateam.versatile.items.tiers

import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.IRecipeStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.toIngredient
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.ItemStack
import net.minecraft.util.SoundEvent

class ArmorTier(val registryName: String, val durability: IntArray, val damageReduction: IntArray, private val enchantability: Int, soundSupplier: () -> SoundEvent, private val toughness: Float, repairRecipeStackSupplier: () -> IRecipeStack<ItemStack>) : IArmorMaterial {
    private val sound by lazy(soundSupplier)
    val repairRecipeStack by lazy(repairRecipeStackSupplier)

    override fun getName() = registryName

    override fun getDurability(slotIn: EquipmentSlotType) = durability[slotIn.index]

    override fun getDamageReductionAmount(slotIn: EquipmentSlotType) = damageReduction[slotIn.index]

    override fun getEnchantability() = enchantability

    override fun getSoundEvent() = sound

    override fun getToughness() = toughness

    override fun getRepairMaterial() = repairRecipeStack.toIngredient()
}