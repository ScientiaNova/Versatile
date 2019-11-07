package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.layout.components.*
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture

class GUIPage {
    val components = mutableListOf<IGUIComponent>()

    fun boolButton(property: String, onTex: GUITexture, offTex: GUITexture, x: Int, y: Int, builder: BooleanButtonComponent.() -> Unit) {
        val component = BooleanButtonComponent(property, onTex, offTex, x, y)
        component.builder()
        components += component
    }

    fun colorButton(property: String, tex: GUITexture, colors: IntArray, x: Int, y: Int, builder: ColorButtonComponent.() -> Unit) {
        val component = ColorButtonComponent(property, tex, colors, x, y)
        component.builder()
        components += component
    }

    fun fluidBar(property: String, backTex: GUITexture, x: Int, y: Int, builder: FluidBarComponent.() -> Unit) {
        val component = FluidBarComponent(property, backTex, x, y)
        component.builder()
        components += component
    }

    fun fluidSlot(property: String, x: Int, y: Int, builder: FluidSlotComponent.() -> Unit) {
        val component = FluidSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun ghostItemSlot(property: String, x: Int, y: Int, builder: GhostItemSlotComponent.() -> Unit) {
        val component = GhostItemSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun image(builder: ImageComponent.() -> Unit) {
        val component = ImageComponent()
        component.builder()
        components += component
    }

    fun intButton(property: String, textures: List<GUITexture>, x: Int, y: Int, builder: IntegerButtonComponent.() -> Unit) {
        val component = IntegerButtonComponent(property, textures, x, y)
        component.builder()
        components += component
    }

    fun itemSlot(property: String, x: Int, y: Int, builder: ItemSlotComponent.() -> Unit) {
        val component = ItemSlotComponent(property, x, y)
        component.builder()
        components += component
    }

    fun label(text: String, x: Int, y: Int, builder: LabelComponent.() -> Unit) {
        val component = LabelComponent(text, x, y)
        component.builder()
        components += component
    }

    fun pageButton(page: Int, texture: GUITexture, x: Int, y: Int, builder: PageButtonComponent.() -> Unit) {
        val component = PageButtonComponent(page, texture, x, y)
        component.builder()
        components += component
    }

    fun playerSlot(index: Int, x: Int, y: Int, builder: PlayerSlotComponent.() -> Unit) {
        val component = PlayerSlotComponent(index, x, y)
        component.builder()
        components += component
    }

    fun progressBar(property: String, maxProperty: String, builder: ProgressBarComponent.() -> Unit) {
        val component = ProgressBarComponent(property, maxProperty)
        component.builder()
        components += component
    }
}