package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.capabilities.fluids.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.gui.layout.components.bars.FluidBarComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.bars.ProgressBarComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.buttons.BooleanButtonComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.buttons.ColorButtonComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.buttons.IntegerButtonComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.buttons.PageButtonComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.FluidSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.GhostItemSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.ItemSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.slots.PlayerSlotComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.still.ImageComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.still.InventoryLabelComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.still.LabelComponent
import com.emosewapixel.pixellib.machines.gui.textures.updating.AnimatedGUITexture
import com.emosewapixel.pixellib.machines.gui.textures.interactable.ButtonTextureGroup
import com.emosewapixel.pixellib.machines.gui.textures.still.GUITexture
import com.emosewapixel.pixellib.machines.properties.ILimitedIntegerProperty
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.emosewapixel.pixellib.machines.properties.IVariableProperty
import com.emosewapixel.pixellib.machines.properties.implementations.TEPageProperty
import net.minecraftforge.items.IItemHandlerModifiable

open class GUIPage(var extraWidth: Int = 0, var extraHeight: Int = 0, builder: GUIPage.() -> Unit = { }) {
    val components = mutableListOf<IGUIComponent>()

    init {
        this.builder()
    }

    val leftMost get() = components.map(IGUIComponent::x).min() ?: 0

    val topMost get() = components.map(IGUIComponent::y).min() ?: 0

    val rightMost get() = components.map { it.x + it.width }.max() ?: 0

    val bottomMost get() = components.map { it.y + it.height }.max() ?: 0

    val trueWidth get() = if (components.isEmpty()) 0 else components.map { it.x + it.width }.max()!! - components.map(IGUIComponent::x).min()!!

    val trueHeight get() = if (components.isEmpty()) 0 else components.map { it.y + it.height }.max()!! - components.map(IGUIComponent::y).min()!!

    val width get() = rightMost + extraWidth

    val height get() = bottomMost + extraHeight

    @JvmOverloads
    fun add(component: IGUIComponent, xOffset: Int = 0, yOffset: Int = 0) {
        component.offset(xOffset, yOffset)
        components += component
    }

    fun addPlayerInventory(xStart: Int, yStart: Int) {
        inventoryLabel(xStart, yStart)

        val mainY = yStart + 11
        for (index in 9 until 36) {
            val x = xStart + index % 9 * 18
            val y = mainY + index / 9 * 18 - 18
            playerSlot(index, x, y)
        }

        val hotbarY = mainY + 58
        for (index in 0 until 9) {
            val x = xStart + index * 18
            playerSlot(index, x, hotbarY)
        }
    }

    fun boolButton(property: IVariableProperty<Boolean>, textures: ButtonTextureGroup, x: Int, y: Int, builder: BooleanButtonComponent.() -> Unit = { }) {
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

    fun fluidSlot(property: IValueProperty<IFluidHandlerModifiable>, tankIndex: Int, builder: FluidSlotComponent.() -> Unit = { }) {
        val component = FluidSlotComponent(property, tankIndex)
        component.builder()
        components += component
    }

    fun ghostItemSlot(property: IValueProperty<IItemHandlerModifiable>, slotIndex: Int, builder: GhostItemSlotComponent.() -> Unit = { }) {
        val component = GhostItemSlotComponent(property, slotIndex)
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

    fun itemSlot(property: IValueProperty<IItemHandlerModifiable>, index: Int, builder: ItemSlotComponent.() -> Unit = { }) {
        val component = ItemSlotComponent(property, index)
        component.builder()
        components += component
    }

    fun label(text: String, x: Int, y: Int, builder: LabelComponent.() -> Unit = { }) {
        val component = LabelComponent(text, x, y)
        component.builder()
        components += component
    }

    fun inventoryLabel(x: Int, y: Int, builder: InventoryLabelComponent.() -> Unit = { }) {
        val component = InventoryLabelComponent(x, y)
        component.builder()
        components += component
    }

    fun pageButton(property: TEPageProperty, page: Int, texture: GUITexture, x: Int, y: Int, builder: PageButtonComponent.() -> Unit = { }) {
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

    fun componentGroup(builder: GUIComponentGroup.() -> Unit = { }) {
        val component = GUIComponentGroup()
        component.builder()
        components += component
    }
}