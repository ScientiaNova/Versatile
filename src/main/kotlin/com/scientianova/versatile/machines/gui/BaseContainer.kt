package com.scientianova.versatile.machines.gui

import com.scientianovateam.versatile.machines.BaseMachineRegistry
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.slots.IImprovedSlot
import com.scientianovateam.versatile.machines.gui.slots.SlotLogic
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.ClickType
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer

open class BaseContainer(id: Int, val playerInv: PlayerInventory, val te: BaseTileEntity, type: ContainerType<*> = BaseMachineRegistry.BASE_CONTAINER) : Container(type, id) {
    constructor(id: Int, playerInv: PlayerInventory, extraData: PacketBuffer) : this(id, playerInv, playerInv.player.world.getTileEntity(extraData.readBlockPos()) as BaseTileEntity)

    val guiPage = te.guiLayout.currentPage

    val clientProperties = te.teProperties.mapValues { it.value.clone() }

    init {
        guiPage.components.fold(mutableListOf<IImprovedSlot>()) { acc, current ->
            acc += current.addSlots(playerInv)
            acc
        }.forEach { addSlot(it as Slot) }
    }

    override fun canInteractWith(playerIn: PlayerEntity) = te.canInteractWith(playerIn)

    override fun detectAndSendChanges() {
        if (te.world?.isRemote != false) return
        te.teProperties.values.forEach { it.detectAndSendChanges(this) }
    }

    override fun transferStackInSlot(playerIn: PlayerEntity, index: Int): ItemStack {
        val slot = getSlot(index)
        if (!slot.hasStack || slot !is IImprovedSlot) return ItemStack.EMPTY
        val stack = slot.stack
        val matching = if (slot.isPlayerInventory) inventorySlots.filter {
            it is IImprovedSlot && !it.isPlayerInventory && it.isItemValid(stack)
        } else inventorySlots.filter { it is IImprovedSlot && it.isPlayerInventory && it.isItemValid(stack) }.reversed()
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