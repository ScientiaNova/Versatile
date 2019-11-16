package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.gui.layout.components.*
import com.emosewapixel.pixellib.machines.gui.textures.AnimatedGUITexture
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.gui.textures.GUITexturePair
import com.emosewapixel.pixellib.machines.properties.ILimitedIntegerProperty
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.emosewapixel.pixellib.machines.properties.IVariableProperty
import com.emosewapixel.pixellib.machines.properties.implementations.UpdatePageProperty
import net.minecraftforge.items.IItemHandlerModifiable
import kotlin.math.max

class GUIPage(val minWidth: Int = 0, val minHeight: Int = 0, builder: GUIPage.() -> Unit) {
    val components = mutableListOf<IGUIComponent>()

    init {
        builder(this)
    }

    val leftMost get() = components.map(IGUIComponent::x).min() ?: 0

    val topMost get() = components.map(IGUIComponent::y).min() ?: 0

    val rightMost get() = components.map { it.x + it.width }.max() ?: 0

    val bottomMost get() = components.map { it.y + it.height }.max() ?: 0

    val trueWidth get() = if (components.isEmpty()) 0 else components.map { it.x + it.width }.max()!! - components.map(IGUIComponent::x).min()!!

    val trueHeight get() = if (components.isEmpty()) 0 else components.map { it.y + it.height }.max()!! - components.map(IGUIComponent::y).min()!!

    val width get() = max(minWidth, rightMost)

    val height get() = max(minHeight, bottomMost)

    fun boolButton(property: IVariableProperty<Boolean>, textures: GUITexturePair, x: Int, y: Int, builder: BooleanButtonComponent.() -> Unit = { }) {
        val component = BooleanButtonComponent(property, textures, x, y)
        component.builder()
        components += component
    }

    fun colorButton(property: ILimitedIntegerProperty, tex: GUITexture, colors: IntArray, x: Int, y: Int, builder: ColorButtonComponent.() -> Unit = { }) {
        val component = ColorButtonComponent(property, tex, colors, x, y)
        component.builder()
        components += component
    }

    fun fluidBar(property: IValueProperty<IFluidHandlerModifiable>, backTex: GUITexture, x: Int, y: Int, builder: FluidBarComponent.() -> Unit = { }) {
        val component = FluidBarComponent(property, backTex, x, y)
        component.builder()
        components += component
    }

    fun fluidSlot(property: IValueProperty<IFluidHandlerModifiable>, x: Int, y: Int, builder: FluidSlotComponent.() -> Unit = { }) {
        val component = FluidSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun ghostItemSlot(property: IValueProperty<IItemHandlerModifiable>, x: Int, y: Int, builder: GhostItemSlotComponent.() -> Unit = { }) {
        val component = GhostItemSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun image(builder: ImageComponent.() -> Unit = { }) {
        val component = ImageComponent()
        component.builder()
        components += component
    }

    fun intButton(property: ILimitedIntegerProperty, textures: AnimatedGUITexture, x: Int, y: Int, builder: IntegerButtonComponent.() -> Unit = { }) {
        val component = IntegerButtonComponent(property, textures, x, y)
        component.builder()
        components += component
    }

    fun itemSlot(property: IValueProperty<IItemHandlerModifiable>, x: Int, y: Int, builder: ItemSlotComponent.() -> Unit = { }) {
        val component = ItemSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun label(text: String, x: Int, y: Int, builder: LabelComponent.() -> Unit = { }) {
        val component = LabelComponent(text, x, y)
        component.builder()
        components += component
    }

    fun pageButton(property: UpdatePageProperty, page: Int, texture: GUITexture, x: Int, y: Int, builder: PageButtonComponent.() -> Unit = { }) {
        val component = PageButtonComponent(property, page, texture, x, y)
        component.builder()
        components += component
    }

    fun playerSlot(index: Int, x: Int, y: Int, builder: PlayerSlotComponent.() -> Unit = { }) {
        val component = PlayerSlotComponent(index, x, y)
        component.builder()
        components += component
    }

    fun progressBar(property: IValueProperty<Double>, builder: ProgressBarComponent.() -> Unit = { }) {
        val component = ProgressBarComponent(property)
        component.builder()
        components += component
    }

    fun offset(x: Int, y: Int) = components.forEach {
        it.x += x
        it.y += y
    }

    fun GUIPage.merge(xOffset: Int, yOffset: Int) {
        this.offset(xOffset, yOffset)
        this@GUIPage.components += this.components
    }
}