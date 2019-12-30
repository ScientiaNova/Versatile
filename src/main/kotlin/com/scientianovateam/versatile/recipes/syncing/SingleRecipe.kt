package com.scientianovateam.versatile.recipes.syncing

import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.recipes.Recipe
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World

open class SingleRecipe(val recipe: Recipe) : IRecipe<PlayerInventory> {
    override fun canFit(width: Int, height: Int) = false

    override fun getCraftingResult(inv: PlayerInventory): ItemStack = ItemStack.EMPTY

    override fun getId() = "single".toResLocV()

    override fun getRecipeOutput(): ItemStack = ItemStack.EMPTY

    override fun matches(inv: PlayerInventory, worldIn: World) = false

    override fun getType() = VanillaRecipeRegistry.SINGLE_RECIPE_TYPE

    override fun getSerializer() = MaterialBasedRecipeSerializer
}