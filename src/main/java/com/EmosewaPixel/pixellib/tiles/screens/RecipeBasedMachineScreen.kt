package com.EmosewaPixel.pixellib.tiles.screens

import com.EmosewaPixel.pixellib.tiles.AbstractRecipeBasedTE
import com.EmosewaPixel.pixellib.tiles.containers.RecipeBasedMachineContainer
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

open class RecipeBasedMachineScreen<T : AbstractRecipeBasedTE<*>>(container: RecipeBasedMachineContainer<*>, playerInventory: PlayerInventory, protected var te: T, private val backGround: String, textComponent: ITextComponent) : ContainerScreen<RecipeBasedMachineContainer<*>>(container, playerInventory, textComponent) {
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        minecraft!!.getTextureManager().bindTexture(ResourceLocation(backGround))
        blit(guiLeft, guiTop, 0, 0, xSize, ySize)
        val progress: Int
        if (te.progress > 0 && !te.currentRecipe.isEmpty) {
            progress = getProgressLeftScaled(24)
            blit(guiLeft + 79, guiTop + 34, 176, 14, progress + 1, 16)
        }
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val name = te.blockState.block.nameTextComponent.formattedText
        font.drawString(name, (xSize / 2 - font.getStringWidth(name) / 2).toFloat(), 6.0f, 4210752)
        font.drawString(playerInventory.displayName.formattedText, 8.0f, (ySize - 96 + 2).toFloat(), 4210752)
    }

    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground()
        super.render(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    private fun getProgressLeftScaled(scale: Int) = (scale - te.progress.toFloat() / te.currentRecipe.time * scale).toInt()
}