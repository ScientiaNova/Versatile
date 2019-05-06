package com.EmosewaPixel.pixellib.tiles.containers;

import com.EmosewaPixel.pixellib.tiles.TEFuelBased;
import com.EmosewaPixel.pixellib.tiles.TERecipeBased;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;

public class ContainerMachineFuelBased extends ContainerMachineRecipeBased<TERecipeBased> {
    private TEFuelBased te;

    public ContainerMachineFuelBased(IInventory playerInventory, TEFuelBased te) {
        super(playerInventory, te);
        this.te = te;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            listener.sendWindowProperty(this, 2, te.getBurnTime());
            listener.sendWindowProperty(this, 3, te.getMaxBurnTime());
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id) {
            case 2:
                te.setBurnTime(data);
                break;
            case 3:
                if (!te.getCurrentRecipe().isEmpty())
                    te.setMaxBurnTime(data);
                break;
        }
    }
}
