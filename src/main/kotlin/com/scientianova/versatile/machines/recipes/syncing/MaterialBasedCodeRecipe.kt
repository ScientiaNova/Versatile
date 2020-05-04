package com.scientianova.versatile.machines.recipes.syncing

import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.machines.recipes.Recipe
import com.google.gson.JsonObject
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

open class MaterialBasedCodeRecipe(val name: ResourceLocation, val materialPredicate: String, val generationCode: JsonObject) : IRecipe<PlayerInventory> {
    private val generated = mutableListOf<Recipe>()

    fun eval() {

    }

    override fun canFit(width: Int, height: Int) = false

    override fun getCraftingResult(inv: PlayerInventory): ItemStack = ItemStack.EMPTY

    override fun getId() = "versatile:material_based".toResLoc()

    override fun getRecipeOutput(): ItemStack = ItemStack.EMPTY

    override fun matches(inv: PlayerInventory, worldIn: World) = false

    override fun getType() = VanillaRecipeRegistry.MATERIAL_BASED_RECIPE_TYPE

    override fun getSerializer() = MaterialBasedCodeRecipeSerializer
}