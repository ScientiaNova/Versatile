package com.emosewapixel.pixellib.materialsystem.materials

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.items.tools.ArmorMaterial
import com.emosewapixel.pixellib.items.tools.ItemTier
import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import net.minecraft.item.Item
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.SoundEvent
import org.openzen.zencode.java.ZenCodeType
import java.util.function.Supplier

//Gem Materials are materials for which, above all, gems are generated
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.DustMaterial")
open class GemMaterial @ZenCodeType.Constructor constructor(name: String, textureType: String, color: Int, tier: Int) : DustMaterial(name, textureType, color, tier) {
    override val defaultItem: Item?
        get() = MaterialItems[this, MaterialRegistry.GEM]

    @JvmName("invokeGem")
    operator fun invoke(builder: GemMaterial.() -> Unit) = builder(this)

    @ZenCodeType.Method
    fun itemTier(harvestLevelIn: Int, maxUsesIn: Int, efficiencyIn: Float, attackDamageIn: Float, enchantabilityIn: Int) =
            ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, Supplier { Ingredient.fromTag(getTag(MaterialRegistry.GEM)) })

    @ZenCodeType.Method
    fun armorMaterial(durability: Int, damageReduction: Int, enchantability: Int, soundEvent: SoundEvent, toughness: Float) = ArmorMaterial(durability, damageReduction, enchantability, soundEvent, Supplier { Ingredient.fromTag(getTag(MaterialRegistry.GEM)) }, name, toughness)
}