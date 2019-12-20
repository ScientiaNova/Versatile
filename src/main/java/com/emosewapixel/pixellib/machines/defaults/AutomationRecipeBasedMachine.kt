package com.emosewapixel.pixellib.machines.defaults

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.layout.DefaultSizeConstants
import com.emosewapixel.pixellib.machines.gui.layout.GUIBook
import com.emosewapixel.pixellib.machines.gui.layout.book
import com.emosewapixel.pixellib.machines.gui.layout.components.still.LabelComponent
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.AutomationProcessingProperty
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.TEAutomationRecipeProperty
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import net.minecraft.util.text.TranslationTextComponent
import kotlin.math.max

open class AutomationRecipeBasedMachine(recipeList: RecipeList, properties: Properties, name: String = recipeList.name.toString()) : AbstractMachineBlock(properties, name) {
    init {
        recipeList.blocksImplementing += this
    }

    override val teProperties: BaseTileEntity.() -> List<ITEBoundProperty> = {
        val recipeProperty = TEAutomationRecipeProperty(recipeList, null, "recipe", this)
        recipeList.recipeComponents.values.fold(mutableListOf<ITEBoundProperty>(recipeProperty)) { list, component ->
            component.addDefaultProperty(this, list)
            list
        } + AutomationProcessingProperty("processing", this, recipeProperty)
    }

    override val guiLayout: BaseTileEntity.() -> GUIBook = {
        val recipeListPage = recipeList.createComponentGroup(this)
        val te = this

        book {
            val pageWidth = max(DefaultSizeConstants.BACKGROUND_WIDTH, MARGIN * 2 + recipeListPage.trueWidth)
            val pageHeight = max(DefaultSizeConstants.BACKGROUND_HEIGHT, TOP_MARGIN + recipeListPage.trueHeight + 2 + INVENTORY_HEIGHT + MARGIN)

            page {
                image {
                    width = pageWidth
                    height = pageHeight
                }

                label(this@AutomationRecipeBasedMachine.nameTextComponent.formattedText, pageWidth / 2, 6) {
                    location = LabelComponent.LabelLocation.CENTER
                }

                (teProperties["recipe"] as? RecipeProperty)?.value?.let { currentRecipe ->
                    val currentRecipePage = recipeList.createRecipeBasedComponentGroup(te, currentRecipe)
                    val processX = (pageWidth - currentRecipePage.trueWidth) / 2
                    val processY = TOP_MARGIN + (pageHeight - TOP_MARGIN - currentRecipePage.trueHeight - 2 - INVENTORY_HEIGHT - MARGIN) / 2
                    add(currentRecipePage, processX - recipeListPage.leftMost, processY - recipeListPage.topMost)
                } ?: label(TranslationTextComponent("gui.info.find_recipe").string,
                        pageWidth / 2, TOP_MARGIN + (pageHeight - TOP_MARGIN - INVENTORY_HEIGHT - MARGIN) / 2) {
                    location = LabelComponent.LabelLocation.CENTER
                }

                val inventoryX = (pageWidth - INVENTORY_WIDTH) / 2
                val inventoryY = pageHeight - INVENTORY_HEIGHT - MARGIN
                addPlayerInventory(inventoryX, inventoryY)
            }
        }
    }

    companion object {
        const val INVENTORY_WIDTH = DefaultSizeConstants.INVENTORY_WIDTH
        const val INVENTORY_HEIGHT = DefaultSizeConstants.INVENTORY_HEIGHT

        const val MARGIN = DefaultSizeConstants.MARGIN
        const val TOP_MARGIN = DefaultSizeConstants.TOP_MARGIN
    }
}