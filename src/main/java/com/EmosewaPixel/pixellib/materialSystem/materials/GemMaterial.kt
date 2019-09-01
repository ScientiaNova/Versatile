package com.EmosewaPixel.pixellib.materialsystem.materials

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial
import com.EmosewaPixel.pixellib.items.tools.ItemTier
import com.EmosewaPixel.pixellib.materialsystem.MaterialRegistry
import com.EmosewaPixel.pixellib.materialsystem.lists.MaterialItems
import com.EmosewaPixel.pixellib.materialsystem.types.TextureType
import net.minecraft.item.Item
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.SoundEvent
import java.util.function.Supplier

//Gem Materials are materials for which, above all, gems are generated
open class GemMaterial(name: String, textureType: TextureType?, color: Int, tier: Int) : DustMaterial(name, textureType, color, tier) {
    override val defaultItem: Item?
        get() = MaterialItems.getItem(this, MaterialRegistry.GEM)

    @JvmName("invokeGem")
    operator fun invoke(builder: GemMaterial.() -> Unit) = builder(this)

    fun itemTier(harvestLevelIn: Int, maxUsesIn: Int, efficiencyIn: Float, attackDamageIn: Float, enchantabilityIn: Int) =
            ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, Supplier { Ingredient.fromTag(getTag(MaterialRegistry.GEM)) })

    fun armorMaterial(durability: Int, damageReduction: Int, enchantability: Int, soundEvent: SoundEvent, toughness: Float) = ArmorMaterial(durability, damageReduction, enchantability, soundEvent, Supplier { Ingredient.fromTag(getTag(MaterialRegistry.GEM)) }, name, toughness)
}