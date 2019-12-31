package com.scientianovateam.versatile.recipes.lists

import com.google.common.collect.Multimap
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.BaseContainer
import com.scientianovateam.versatile.machines.gui.layout.GUIComponentGroup
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.IRecipeComponent
import net.minecraft.block.Block
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent

interface IRecipeLIst {
    val name: ResourceLocation
    val blocksImplementing: MutableList<Block>
    val inputMap: Multimap<String, Recipe>
    val recipes: Map<ResourceLocation, Recipe>
    val localizedName get() = TranslationTextComponent("recipe_list.$name")
    val recipeComponents: Map<String, IRecipeComponent<*>>
    val recipeTransferFunction: ((Recipe, BaseContainer) -> Unit)? get() = null
    val genJEIPage get() = true
    fun clear()
    fun addRecipe(recipe: Recipe, vanillaRecipeMap: MutableMap<IRecipeType<*>, MutableMap<ResourceLocation, IRecipe<*>>>?)
    fun findRecipe(machine: BaseTileEntity): Recipe?
    fun createComponentGroup(machine: BaseTileEntity? = null): GUIComponentGroup
    fun createRecipeBasedComponentGroup(machine: BaseTileEntity?, recipe: Recipe): GUIComponentGroup
}