package com.emosewapixel.pixellib.materialsystem.materials

import com.emosewapixel.pixellib.items.tools.ArmorMaterial
import com.emosewapixel.pixellib.items.tools.ItemTier
import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import net.minecraft.item.Item
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.SoundEvent
import java.util.function.Supplier

//Ingot Materials are the solid materials that, above all else, have ingots
open class IngotMaterial(name: String, textureType: String, color: Int, tier: Int) : DustMaterial(name, textureType, color, tier) {
    override val defaultItem: Item?
        get() = MaterialItems.getItem(this, MaterialRegistry.INGOT)

    init {
        compoundType = CompoundType.ALLOY
    }


    @JvmName("invokeIngot")
    operator fun invoke(builder: IngotMaterial.() -> Unit) = builder(this)

    fun itemTier(harvestLevelIn: Int, maxUsesIn: Int, efficiencyIn: Float, attackDamageIn: Float, enchantabilityIn: Int) =
            ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, Supplier { Ingredient.fromTag(getTag(MaterialRegistry.INGOT)) })

    fun armorMaterial(durability: Int, damageReduction: Int, enchantability: Int, soundEvent: SoundEvent, toughness: Float) = ArmorMaterial(durability, damageReduction, enchantability, soundEvent, Supplier { Ingredient.fromTag(getTag(MaterialRegistry.INGOT)) }, name, toughness)
}