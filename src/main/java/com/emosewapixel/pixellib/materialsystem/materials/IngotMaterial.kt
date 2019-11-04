package com.emosewapixel.pixellib.materialsystem.materials

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.items.tools.ArmorMaterial
import com.emosewapixel.pixellib.items.tools.ItemTier
import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.SoundEvent
import org.openzen.zencode.java.ZenCodeType
import java.util.function.Supplier

//Ingot Materials are the solid materials that, above all else, have ingots
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.IngotMaterial")
open class IngotMaterial @ZenCodeType.Constructor constructor(name: String, textureType: String, color: Int, tier: Int) : DustMaterial(name, textureType, color, tier) {
    override val defaultItem get() = MaterialItems[this, BaseMaterials.INGOT]

    init {
        compoundType = CompoundType.ALLOY
    }

    @JvmName("invokeIngot")
    operator fun invoke(builder: IngotMaterial.() -> Unit) = builder(this)

    @ZenCodeType.Method
    fun itemTier(harvestLevelIn: Int, maxUsesIn: Int, efficiencyIn: Float, attackDamageIn: Float, enchantabilityIn: Int) =
            ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, Supplier { Ingredient.fromTag(getTag(BaseMaterials.INGOT)) })

    @ZenCodeType.Method
    fun armorMaterial(durability: Int, damageReduction: Int, enchantability: Int, soundEvent: SoundEvent, toughness: Float) = ArmorMaterial(durability, damageReduction, enchantability, soundEvent, Supplier { Ingredient.fromTag(getTag(BaseMaterials.INGOT)) }, name, toughness)
}