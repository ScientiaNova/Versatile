package com.scientianovateam.versatile.items.tools

import net.minecraft.item.IItemTier
import net.minecraft.item.crafting.Ingredient

import java.util.function.Supplier

//This is an implementation of vanilla's IItemTier so you can simply make objects of it
class ItemTier(private val harvestLevel: Int, private val maxUses: Int, private val efficiency: Float, private val attackDamage: Float, private val enchantability: Int, private val repairMaterial: Supplier<Ingredient>) : IItemTier {
    override fun getMaxUses() = this.maxUses

    override fun getEfficiency() = this.efficiency

    override fun getAttackDamage() = this.attackDamage

    override fun getHarvestLevel() = this.harvestLevel

    override fun getEnchantability() = this.enchantability

    override fun getRepairMaterial() = this.repairMaterial.get()
}