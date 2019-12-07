package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraftforge.items.IItemHandlerModifiable

open class ItemInventoryProperty(override val value: ImprovedItemStackHandler) : IValueProperty<IItemHandlerModifiable> {
    override fun createDefault() = ItemInventoryProperty(ImprovedItemStackHandler(value.slots, value.noOutputSlots, value.noInputSlots))
}