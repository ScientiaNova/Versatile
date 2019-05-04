package com.EmosewaPixel.pixellib.tiles.containers;

import com.EmosewaPixel.pixellib.tiles.TileEntityRecipeBased;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineRecipeBased extends Container {
    private TileEntityRecipeBased te;
    private IItemHandler itemHandler;

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }

    public ContainerMachineRecipeBased(IInventory playerInventory, TileEntityRecipeBased te) {
        this.te = te;

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> itemHandler = handler);

        for (int i = 0; i < te.getInputCount(); i++)
            this.addSlot(new SlotItemHandler(itemHandler, i, te.getInputCount() == 1 ? 56 : 38 + i * 18, 17));

        this.addSlot(new SlotItemHandler(itemHandler, te.getInputCount(), 56 - (te.getInputCount() - 1) * 9, 53));

        for (int i = 0; i < te.getOutputCount(); i++)
            this.addSlot(new SlotItemHandler(itemHandler, te.slotCount - i - 1, 116, te.getOutputCount() == 1 ? 35 : 48 - i * 22));

        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < te.slotCount) {
                if (!this.mergeItemStack(itemstack1, te.slotCount, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, te.slotCount, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            listener.sendWindowProperty(this, 0, te.getProgress());
            listener.sendWindowProperty(this, 1, te.getCurrentRecipe().getTime());
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        switch (id) {
            case 0:
                te.setProgress(data);
                break;
            case 1:
                te.getCurrentRecipe().setTime(data);
                break;
        }
    }
}
