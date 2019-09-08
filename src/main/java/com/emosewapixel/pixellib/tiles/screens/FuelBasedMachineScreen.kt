package com.emosewapixel.pixellib.tiles.screens

import com.emosewapixel.pixellib.tiles.FuelBasedTE
import com.emosewapixel.pixellib.tiles.containers.FuelBasedMachineContainer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.text.ITextComponent

open class FuelBasedMachineScreen(container: FuelBasedMachineContainer, playerInventory: PlayerInventory, te: FuelBasedTE, backGround: String, textComponent: ITextComponent) : RecipeBasedMachineScreen<FuelBasedTE>(container, playerInventory, te, backGround, textComponent) {
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
        val burnTime: Int
        if (te.burnTime > 0) {
            burnTime = getBurnLeftScaled(13)
            blit(guiLeft + 56 - (te.recipeList.maxInputs - 1) * 9, guiTop + 36 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 1)
        }
    }

    private fun getBurnLeftScaled(scale: Int) = (te.burnTime.toFloat() / te.maxBurnTime * scale).toInt()
}