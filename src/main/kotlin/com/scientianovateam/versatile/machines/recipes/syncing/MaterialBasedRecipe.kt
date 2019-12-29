package com.scientianovateam.versatile.machines.recipes.syncing

import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.machines.recipes.Recipe
import com.scientianovateam.versatile.machines.recipes.RecipeMaterialTemplate
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.velisp.evaluated.MaterialValue
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World

open class MaterialBasedRecipe(private val materialPredicate: IUnresolved?, private val template: RecipeMaterialTemplate?, generated: List<Recipe>) : IRecipe<PlayerInventory> {
    var generated = generated
        private set

    fun eval() {
        if (template == null) return
        generated = Materials.all.let {
            if (materialPredicate == null) it else it.filter { mat -> materialPredicate.resolve(mapOf("mat" to MaterialValue(mat))).evaluate().value == true }
        }.map { template.create(it) }
    }

    override fun canFit(width: Int, height: Int) = false

    override fun getCraftingResult(inv: PlayerInventory): ItemStack = ItemStack.EMPTY

    override fun getId() = "material_based".toResLocV()

    override fun getRecipeOutput(): ItemStack = ItemStack.EMPTY

    override fun matches(inv: PlayerInventory, worldIn: World) = false

    override fun getType() = VanillaRecipeRegistry.MATERIAL_BASED_RECIPE_TYPE

    override fun getSerializer() = MaterialBasedRecipeSerializer
}