package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import com.emosewapixel.pixellib.machines.gui.slots.IImprovedSlot
import com.emosewapixel.pixellib.machines.gui.slots.SlotLogic
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.ClickType
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack

class BaseContainer(id: Int, val playerInv: PlayerInventory, val te: BaseTileEntity, type: ContainerType<*>) : Container(type, id) {
    val guiPage = te.guiLayout.current
    val clientProperties = te.properties.mapValues { it.value.copy() }

    init {
        guiPage.components.forEach { if (it is ISlotComponent) addSlot(it.setupSlot(playerInv) as Slot) }
    }

    override fun canInteractWith(playerIn: PlayerEntity) = te.canInteractWith(playerIn)

    override fun detectAndSendChanges() = guiPage.components.asSequence().filterIsInstance<IPropertyGUIComponent>().distinct().forEach { (it.property as? ITEBoundProperty)?.detectAndSendChanges(this) }

    override fun transferStackInSlot(playerIn: PlayerEntity, index: Int): ItemStack {
        val slot = getSlot(index)
        if (!slot.hasStack || slot !is IImprovedSlot) return ItemStack.EMPTY
        val stack = slot.stack
        val matching = if (slot.isPlayerInventory) inventorySlots.filter { it is IImprovedSlot && !it.isPlayerInventory && it.isItemValid(stack) }
        else inventorySlots.filter { it is IImprovedSlot && it.isPlayerInventory && it.isItemValid(stack) }.reversed()
        val merged = slot.merge(matching)
        if (!merged) return ItemStack.EMPTY
        if (stack.isEmpty)
            slot.putStack(ItemStack.EMPTY)
        else
            slot.onSlotChanged()
        return stack
    }

    override fun slotClick(slotId: Int, mouseButton: Int, clickTypeIn: ClickType, player: PlayerEntity): ItemStack {
        if (slotId >= 0 && slotId < inventorySlots.size) {
            val slot = getSlot(slotId)
            if (slot is IImprovedSlot)
                return when (val logic = slot.onClick()) {
                    is SlotLogic.Vanilla -> super.slotClick(slotId, mouseButton, clickTypeIn, player)
                    is SlotLogic.Custom -> logic.func(slot, mouseButton, clickTypeIn, player)
                }
        }
        return super.slotClick(slotId, mouseButton, clickTypeIn, player)
    }
}