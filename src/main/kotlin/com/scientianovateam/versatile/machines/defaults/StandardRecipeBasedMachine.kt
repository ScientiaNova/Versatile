package com.scientianovateam.versatile.machines.defaults

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.layout.DefaultSizeConstants
import com.scientianovateam.versatile.machines.gui.layout.GUIBook
import com.scientianovateam.versatile.machines.gui.layout.book
import com.scientianovateam.versatile.machines.gui.layout.components.still.LabelComponent
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.StandardProcessingProperty
import com.scientianovateam.versatile.machines.properties.implementations.recipes.TEStandardRecipeProperty
import com.scientianovateam.versatile.recipes.lists.RecipeList
import kotlin.math.max

open class StandardRecipeBasedMachine(recipeList: RecipeList, properties: Properties, name: String = recipeList.name.toString()) : AbstractMachineBlock(properties, name) {
    init {
        recipeList.blocksImplementing += this
    }

    override val teProperties: BaseTileEntity.() -> List<ITEBoundProperty> = {
        val recipeProperty = TEStandardRecipeProperty(recipeList, null, "recipe", this)
        recipeList.recipeComponents.values.fold(mutableListOf<ITEBoundProperty>(recipeProperty)) { list, component ->
            component.addDefaultProperty(this, list)
            list
        } + StandardProcessingProperty("processing", this, recipeProperty)
    }

    override val guiLayout: BaseTileEntity.() -> GUIBook = {
        val recipePage = recipeList.createComponentGroup(this)

        val recipePageWidth = recipePage.trueWidth
        val recipePageHeight = recipePage.trueHeight

        book {
            val pageWidth = max(DefaultSizeConstants.BACKGROUND_WIDTH, MARGIN * 2 + recipePageWidth)
            val pageHeight = max(DefaultSizeConstants.BACKGROUND_HEIGHT, TOP_MARGIN + recipePageHeight + 2 + INVENTORY_HEIGHT + MARGIN)

            page {
                image {
                    width = pageWidth
                    height = pageHeight
                }

                label(this@StandardRecipeBasedMachine.nameTextComponent.formattedText, pageWidth / 2, 6) {
                    location = LabelComponent.LabelLocation.CENTER
                }

                val processX = (pageWidth - recipePageWidth) / 2
                val processY = TOP_MARGIN + (pageHeight - TOP_MARGIN - recipePageHeight - 2 - INVENTORY_HEIGHT - MARGIN) / 2
                add(recipePage, processX - recipePage.leftMost, processY - recipePage.topMost)

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