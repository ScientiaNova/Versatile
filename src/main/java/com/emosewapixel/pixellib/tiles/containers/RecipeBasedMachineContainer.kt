package com.emosewapixel.pixellib.tiles.containers

import com.emosewapixel.pixellib.tiles.AbstractRecipeBasedTE
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

open class RecipeBasedMachineContainer<T : AbstractRecipeBasedTE<*>>(playerInventory: PlayerInventory, protected var te: T, type: ContainerType<*>, id: Int) : Container(type, id) {
    protected lateinit var itemHandler: IItemHandler

    override fun canInteractWith(playerIn: PlayerEntity): Boolean {
        return te.canInteractWith(playerIn)
    }

    init {
        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent { handler -> itemHandler = handler }

        addMachineSlots()

        addPlayerSlots(playerInventory)
    }

    protected open fun addMachineSlots() {
        repeat(te.recipeList.maxInputs) { this.addSlot(SlotItemHandler(itemHandler, it, if (te.recipeList.maxInputs == 1) 56 else 38 + it * 18, 35)) }

        repeat(te.recipeList.maxOutputs) { this.addSlot(SlotItemHandler(itemHandler, te.slotCount - it - 1, 116, if (te.recipeList.maxOutputs == 1) 35 else 48 - it * 22)) }
    }

    private fun addPlayerSlots(playerInventory: IInventory) {
        repeat(3) { i -> repeat(9) { j -> this.addSlot(Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)) } }
        repeat(9) { k -> this.addSlot(Slot(playerInventory, k, 8 + k * 18, 142)) }
    }

    override fun transferStackInSlot(playerIn: PlayerEntity?, index: Int): ItemStack {
        var itemstack = ItemStack.EMPTY
        val slot = this.inventorySlots[index]

        if (slot != null && slot.hasStack) {
            val stack1 = slot.stack
            itemstack = stack1.copy()

            if (index < te.slotCount) {
                if (!this.mergeItemStack(stack1, te.slotCount, this.inventorySlots.size, true))
                    return ItemStack.EMPTY
            } else if (!this.mergeItemStack(stack1, 0, te.slotCount, false))
                return ItemStack.EMPTY

            if (stack1.isEmpty)
                slot.putStack(ItemStack.EMPTY)
            else
                slot.onSlotChanged()
        }

        return itemstack
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
        listeners.forEach { listener ->
            listener.sendWindowProperty(this, 0, te.progress)
            listener.sendWindowProperty(this, 1, te.currentRecipe.time)
        }
    }

    override fun updateProgressBar(id: Int, data: Int) {
        when (id) {
            0 -> te.progress = data
            1 -> te.currentRecipe.time = data
        }
    }
}