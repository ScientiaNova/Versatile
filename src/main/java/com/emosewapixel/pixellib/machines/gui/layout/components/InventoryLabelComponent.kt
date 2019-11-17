package com.emosewapixel.pixellib.machines.gui.layout.components

import net.minecraft.util.text.TranslationTextComponent

class InventoryLabelComponent(override var x: Int, override var y: Int) : LabelComponent(TranslationTextComponent("container.inventory").formattedText, x, y)