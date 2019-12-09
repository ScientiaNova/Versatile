package com.emosewapixel.pixellib.machines

import com.emosewapixel.pixellib.machines.gui.layout.DefaultSizeConstants
import com.emosewapixel.pixellib.machines.gui.layout.GUIBook
import com.emosewapixel.pixellib.machines.gui.layout.book
import com.emosewapixel.pixellib.machines.gui.layout.components.still.LabelComponent
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.TERecipeProperty
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import kotlin.math.max

open class RecipeBasedMachine(recipeList: RecipeList, properties: Properties, name: String = recipeList.name.toString()) : AbstractMachineBlock(properties, name) {
    init {
        recipeList.blocksImplementing += this
    }

    override val teProperties: BaseTileEntity.() -> List<ITEBoundProperty> = {
        recipeList.recipeComponents.values.fold(mutableListOf<ITEBoundProperty>(TERecipeProperty(recipeList, "recipe", this))) { list, component ->
            component.addDefaultProperty(this, list)
            list
        }
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

                label(this@RecipeBasedMachine.nameTextComponent.formattedText, pageWidth / 2, 6) {
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