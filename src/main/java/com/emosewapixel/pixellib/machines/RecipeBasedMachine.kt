package com.emosewapixel.pixellib.machines

import com.emosewapixel.pixellib.machines.gui.layout.DefaultSizeConstants
import com.emosewapixel.pixellib.machines.gui.layout.GUIBook
import com.emosewapixel.pixellib.machines.gui.layout.book
import com.emosewapixel.pixellib.machines.gui.layout.components.LabelComponent
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.FluidInventoryProperty
import com.emosewapixel.pixellib.machines.properties.implementations.ItemInventoryProperty
import com.emosewapixel.pixellib.machines.properties.implementations.TEFluidInventoryProperty
import com.emosewapixel.pixellib.machines.properties.implementations.TEItemInventoryProperty
import com.emosewapixel.pixellib.machines.recipes.AbstractRecipeList
import kotlin.math.max

open class RecipeBasedMachine(recipeList: AbstractRecipeList<*, *>, properties: Properties, name: String = recipeList.name.toString()) : AbstractMachineBlock(properties, name) {
    init {
        recipeList.blocksImplementing += this
    }

    override val teProperties: BaseTileEntity.() -> List<ITEBoundProperty> = {
        listOf(
                TEItemInventoryProperty(this, "items", recipeList.maxInputs, recipeList.maxOutputs),
                TEFluidInventoryProperty(this, "fluids", recipeList.maxFluidInputs, recipeList.maxFluidOutputs)
        )
    }

    override val guiLayout: BaseTileEntity.() -> GUIBook = {
        val recipePage = recipeList.createPage(
                teProperties["items"] as ItemInventoryProperty,
                teProperties["fluids"] as FluidInventoryProperty
        )

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