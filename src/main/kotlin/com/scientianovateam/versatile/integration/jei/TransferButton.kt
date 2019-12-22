package com.scientianovateam.versatile.integration.jei

import com.scientianovateam.versatile.machines.gui.GUiUtils
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import mezz.jei.Internal
import net.minecraft.util.text.TranslationTextComponent

object TransferButton : IGUIComponent {
    override var x = 0
    override var y = 0
    override val width = 13
    override val height = 13

    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        val textures = Internal.getTextures()
        val texture = textures.getButtonForState(true, isSelected(mouseX - xOffset, mouseY - yOffset))
        texture.draw(xOffset, yOffset, width, height)
        JEITextures.TRANSFER_OVERLAY.draw(xOffset + 3, yOffset + 3, 7, 7)
    }

    override fun drawInForeground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        if (isSelected(mouseX - xOffset, mouseY - yOffset))
            GUiUtils.drawTooltip(listOf(TranslationTextComponent("extra_recipe_info.set_recipe").string), mouseX, mouseY)
    }
}