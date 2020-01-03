package com.scientianovateam.versatile.items.tiers

import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.IRecipeStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.toIngredient
import net.minecraft.item.IItemTier
import net.minecraft.item.ItemStack

class ToolTier(val registryName: String, private val maxUses: Int, private val efficiency: Float, private val attackDamage: Float, private val harvestLevel: Int, private val enchantability: Int, repairRecipeStackSupplier: () -> IRecipeStack<ItemStack>) : IItemTier {
    val repairRecipeStack by lazy(repairRecipeStackSupplier)

    override fun getMaxUses() = maxUses

    override fun getEfficiency() = efficiency

    override fun getAttackDamage() = attackDamage

    override fun getHarvestLevel() = harvestLevel

    override fun getEnchantability() = enchantability

    override fun getRepairMaterial() = repairRecipeStack.toIngredient()
}