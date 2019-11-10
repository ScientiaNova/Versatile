package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.layout.components.*
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.properties.*
import com.emosewapixel.pixellib.machines.properties.implementations.UpdatePageProperty

class GUIPage(builder: (GUIPage) -> Unit) {
    val components = mutableListOf<IGUIComponent>()

    init {
        builder(this)
    }

    val width get() = if (components.isEmpty()) 0 else components.map { it.x + it.width }.max()!! - components.map(IGUIComponent::x).min()!!

    val height get() = if (components.isEmpty()) 0 else components.map { it.y + it.height }.max()!! - components.map(IGUIComponent::y).min()!!

    fun boolButton(property: IBooleanProperty, onTex: GUITexture, offTex: GUITexture, x: Int, y: Int, builder: BooleanButtonComponent.() -> Unit) {
        val component = BooleanButtonComponent(property, onTex, offTex, x, y)
        component.builder()
        components += component
    }

    fun colorButton(property: IIntegerProperty, tex: GUITexture, colors: IntArray, x: Int, y: Int, builder: ColorButtonComponent.() -> Unit) {
        val component = ColorButtonComponent(property, tex, colors, x, y)
        component.builder()
        components += component
    }

    fun fluidBar(property: IFluidHandlerProperty, backTex: GUITexture, x: Int, y: Int, builder: FluidBarComponent.() -> Unit) {
        val component = FluidBarComponent(property, backTex, x, y)
        component.builder()
        components += component
    }

    fun fluidSlot(property: IFluidHandlerProperty, x: Int, y: Int, builder: FluidSlotComponent.() -> Unit) {
        val component = FluidSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun ghostItemSlot(property: IItemHandlerProperty, x: Int, y: Int, builder: GhostItemSlotComponent.() -> Unit) {
        val component = GhostItemSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun image(builder: ImageComponent.() -> Unit) {
        val component = ImageComponent()
        component.builder()
        components += component
    }

    fun intButton(property: IIntegerProperty, textures: List<GUITexture>, x: Int, y: Int, builder: IntegerButtonComponent.() -> Unit) {
        val component = IntegerButtonComponent(property, textures, x, y)
        component.builder()
        components += component
    }

    fun itemSlot(property: IItemHandlerProperty, x: Int, y: Int, builder: ItemSlotComponent.() -> Unit) {
        val component = ItemSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun label(text: String, x: Int, y: Int, builder: LabelComponent.() -> Unit) {
        val component = LabelComponent(text, x, y)
        component.builder()
        components += component
    }

    fun pageButton(property: UpdatePageProperty, page: Int, texture: GUITexture, x: Int, y: Int, builder: PageButtonComponent.() -> Unit) {
        val component = PageButtonComponent(property, page, texture, x, y)
        component.builder()
        components += component
    }

    fun playerSlot(index: Int, x: Int, y: Int, builder: PlayerSlotComponent.() -> Unit) {
        val component = PlayerSlotComponent(index, x, y)
        component.builder()
        components += component
    }

    fun progressBar(property: IDoubleProperty, builder: ProgressBarComponent.() -> Unit) {
        val component = ProgressBarComponent(property)
        component.builder()
        components += component
    }
}