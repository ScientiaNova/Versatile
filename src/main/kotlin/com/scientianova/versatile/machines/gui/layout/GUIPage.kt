package com.scientianova.versatile.machines.gui.layout

import com.scientianovateam.versatile.machines.capabilities.fluids.IFluidHandlerModifiable
import com.scientianovateam.versatile.machines.gui.layout.components.bars.FluidBarComponent
import com.scientianovateam.versatile.machines.gui.layout.components.bars.ProgressBarComponent
import com.scientianovateam.versatile.machines.gui.layout.components.buttons.BooleanButtonComponent
import com.scientianovateam.versatile.machines.gui.layout.components.buttons.ColorButtonComponent
import com.scientianovateam.versatile.machines.gui.layout.components.buttons.IntegerButtonComponent
import com.scientianovateam.versatile.machines.gui.layout.components.buttons.PageButtonComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.FluidSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.GhostItemSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.ItemSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.slots.PlayerSlotComponent
import com.scientianovateam.versatile.machines.gui.layout.components.still.ImageComponent
import com.scientianovateam.versatile.machines.gui.layout.components.still.InventoryLabelComponent
import com.scientianovateam.versatile.machines.gui.layout.components.still.LabelComponent
import com.scientianovateam.versatile.machines.gui.layout.components.still.WrappingTextComponent
import com.scientianovateam.versatile.machines.gui.layout.components.textboxes.FocusableTextBoxComponent
import com.scientianovateam.versatile.machines.gui.layout.components.textboxes.TextBoxComponent
import com.scientianovateam.versatile.machines.gui.textures.interactable.ButtonTextureGroup
import com.scientianovateam.versatile.machines.gui.textures.still.GUITexture
import com.scientianovateam.versatile.machines.gui.textures.updating.AnimatedGUITexture
import com.scientianovateam.versatile.machines.properties.ILimitedIntegerProperty
import com.scientianovateam.versatile.machines.properties.IValueProperty
import com.scientianovateam.versatile.machines.properties.IVariableProperty
import com.scientianovateam.versatile.machines.properties.implementations.TEPageProperty
import com.scientianovateam.versatile.machines.properties.implementations.strings.VariableStringProperty
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

    fun wrappingText(text: String, x: Int, y: Int, width: Int, builder: WrappingTextComponent.() -> Unit = { }) {
        val component = WrappingTextComponent(text, x, y, width)
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

    fun textBox(property: VariableStringProperty, builder: TextBoxComponent.() -> Unit = { }) {
        val component = TextBoxComponent(property)
        component.builder()
        components += component
    }

    fun focusableTextBox(property: VariableStringProperty, builder: FocusableTextBoxComponent.() -> Unit = { }) {
        val component = FocusableTextBoxComponent(property)
        component.builder()
        components += component
    }

    fun componentGroup(builder: GUIComponentGroup.() -> Unit = { }) {
        val component = GUIComponentGroup()
        component.builder()
        components += component
    }
}