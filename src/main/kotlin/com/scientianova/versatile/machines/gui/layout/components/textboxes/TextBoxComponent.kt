package com.scientianova.versatile.machines.gui.layout.components.textboxes

import com.scientianova.versatile.common.extensions.max
import com.scientianova.versatile.common.extensions.min
import com.scientianova.versatile.machines.gui.GUiUtils
import com.scientianova.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianova.versatile.machines.gui.textures.BaseTextures
import com.scientianova.versatile.machines.gui.textures.interactable.ButtonDrawMode
import com.scientianova.versatile.machines.properties.implementations.strings.VariableStringProperty
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.platform.GlStateManager.LogicOp
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.SharedConstants
import org.lwjgl.glfw.GLFW
import kotlin.math.max
import kotlin.math.min

open class TextBoxComponent(val property: VariableStringProperty) : ITexturedGUIComponent {
    override val texture = BaseTextures.TEXT_BOX
    override var width = 110
    override val height = 16
    override var x = 59
    override var y = 20

    protected open val isEnabled = true
    protected var cursorPos = 0
        set(value) {
            if (value < 0 || value > property.value.length) return
            if (Screen.hasShiftDown())
                selection = if (hasSelection) selection.first to value else {
                    hasSelection = true
                    field to value
                }
            else hasSelection = false
            field = value
        }

    protected var hasSelection = false
    protected var selection = 0 to 0

    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        texture.draw(xOffset + x, yOffset + y, width, height, drawMode = if (isEnabled) ButtonDrawMode.ON else ButtonDrawMode.OFF)

        val fontRenderer = Minecraft.getInstance().fontRenderer
        val lines = fontRenderer.wrapFormattedStringToWidth(property.value, width - 8).split('\n')
        var indicesIn = 0
        val displayedLine = lines.firstOrNull {
            if (indicesIn + it.length >= cursorPos) return@firstOrNull true
            indicesIn += it.length
            false
        } ?: lines.last()
        fontRenderer.drawStringWithShadow(displayedLine, xOffset + x + 4f, yOffset + y + 4f, 0xffffffff.toInt())

        if (!hasSelection) {
            val cursorDrawX = xOffset + x + 4f + fontRenderer.getStringWidth(displayedLine.substring(cursorPos - indicesIn))
            if (cursorPos == property.value.length)
                fontRenderer.drawStringWithShadow("_", cursorDrawX, yOffset + y + 4f, 0xffffffff.toInt())
            else GUiUtils.drawColoredRectangle(0xffffff, cursorPos, yOffset + y + 3, 1, height - 6)
        } else {
            GlStateManager.enableColorLogicOp()
            GlStateManager.logicOp(LogicOp.OR_REVERSE.opcode)

            val selectionStart = max(selection.min(), indicesIn) - indicesIn
            val selectedXStart = fontRenderer.getStringWidth(displayedLine.substring(selectionStart))
            val selectionWidth = fontRenderer.getStringWidth(displayedLine.substring(selectionStart, min(selection.max() - indicesIn, displayedLine.length)))
            GUiUtils.drawColoredRectangle(0xff0000ff.toInt(), selectedXStart, yOffset + y + 3, selectionWidth, height - 6)

            GlStateManager.disableColorLogicOp()
        }
    }

    protected fun writeText(text: String) {
        property.setValue(property.value.take(if (hasSelection) selection.min() else cursorPos) + text + property.value.drop(if (hasSelection) selection.max() else cursorPos))
        cursorPos += text.length + if (hasSelection) min(selection.first - selection.second, 0) else 0
        hasSelection = false
    }

    protected fun deleteText(amount: Int) {
        val start = amount > 0
        property.setValue(
                if (start) property.value.take(if (hasSelection) selection.min() else cursorPos) + property.value.drop(if (hasSelection) selection.max() else cursorPos + amount)
                else property.value.take(if (hasSelection) selection.min() else cursorPos + amount) + property.value.drop(if (hasSelection) selection.max() else cursorPos)
        )
        cursorPos += if (hasSelection) min(selection.first - selection.second, 0) else amount
        hasSelection = false
    }

    protected fun getNextWordFromCursor(forwards: Boolean): Int {
        for (index in if (forwards) cursorPos until property.value.length else (cursorPos - 1) downTo 0)
            if (!property.value[index].isWhitespace() && (index == 0 || property.value[index - 1].isWhitespace())) return index
        return if (forwards) property.value.length else 0
    }

    override fun charTyped(char: Char, modifier: Int, xOffset: Int, yOffset: Int) = isEnabled && SharedConstants.isAllowedCharacter(char).also {
        writeText(char.toString())
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int, xOffset: Int, yOffset: Int) = isEnabled && when {
        Screen.isSelectAll(keyCode) -> {
            cursorPos = property.value.length
            selection = 0 to property.value.length
            true
        }
        Screen.isCopy(keyCode) -> {
            Minecraft.getInstance().keyboardListener.clipboardString = property.value.substring(selection.first, selection.second)
            true
        }
        Screen.isPaste(keyCode) -> {
            writeText(Minecraft.getInstance().keyboardListener.clipboardString)
            true
        }
        Screen.isCut(keyCode) -> {
            Minecraft.getInstance().keyboardListener.clipboardString = property.value.substring(selection.first, selection.second)
            writeText("")
            true
        }
        else -> when (keyCode) {
            GLFW.GLFW_KEY_BACKSPACE -> {
                deleteText(-1)
                true
            }
            GLFW.GLFW_KEY_INSERT, GLFW.GLFW_KEY_UP, GLFW.GLFW_KEY_DOWN, GLFW.GLFW_KEY_PAGE_UP, GLFW.GLFW_KEY_PAGE_DOWN -> false
            GLFW.GLFW_KEY_DELETE -> {
                deleteText(1)
                true
            }
            GLFW.GLFW_KEY_RIGHT -> {
                cursorPos = if (Screen.hasControlDown()) getNextWordFromCursor(true) else cursorPos + 1
                true
            }
            GLFW.GLFW_KEY_LEFT -> {
                cursorPos = if (Screen.hasControlDown()) getNextWordFromCursor(false) else cursorPos - 1
                true
            }
            GLFW.GLFW_KEY_HOME -> {
                cursorPos = 0
                true
            }
            GLFW.GLFW_KEY_END -> {
                cursorPos = property.value.length
                true
            }
            else -> false
        }
    }
}