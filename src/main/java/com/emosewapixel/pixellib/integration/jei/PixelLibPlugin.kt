package com.emosewapixel.pixellib.integration.jei

import com.emosewapixel.pixellib.extensions.toStack
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.RecipeLists
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.util.ResourceLocation

@JeiPlugin
class PixelLibPlugin : IModPlugin {
    private val jeiRecipeLists get() = RecipeLists.all.filter(RecipeList::genJEIPage)

    override fun getPluginUid() = ResourceLocation("pixellib", "generated")

    override fun registerCategories(registration: IRecipeCategoryRegistration) {
        val guiHelper = registration.jeiHelpers.guiHelper
        val categories = jeiRecipeLists.map { MachineBaseCategory(guiHelper, it) }
        registration.addRecipeCategories(*categories.toTypedArray())
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        jeiRecipeLists.forEach { list ->
            list.blocksImplementing.forEach { registration.addRecipeCatalyst(it.toStack(), list.name) }
        }
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        jeiRecipeLists.forEach { registration.addRecipes(it.getRecipes().values, it.name) }
    }
}