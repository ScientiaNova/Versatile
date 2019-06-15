package com.EmosewaPixel.pixellib.tiles.containers;

import com.EmosewaPixel.pixellib.miscUtils.StreamUtils;
import com.EmosewaPixel.pixellib.tiles.FuelBasedTE;
import com.EmosewaPixel.pixellib.tiles.RecipeBasedTE;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.items.SlotItemHandler;

public class FuelBasedMachineContainer extends RecipeBasedMachineContainer<RecipeBasedTE> {
    public FuelBasedMachineContainer(PlayerInventory playerInventory, FuelBasedTE te, ContainerType<?> type, int id) {
        super(playerInventory, te, type, id);
    }

    @Override
    protected void addMachineSlots() {
        StreamUtils.repeat(te.getRecipeList().getMaxInputs(), i ->
                this.addSlot(new SlotItemHandler(itemHandler, i, te.getRecipeList().getMaxInputs() == 1 ? 56 : 38 + i * 18, 17))
        );
        this.addSlot(new SlotItemHandler(itemHandler, te.getRecipeList().getMaxInputs(), 56 - (te.getRecipeList().getMaxInputs() - 1) * 9, 53));

        StreamUtils.repeat(te.getRecipeList().getMaxOutputs(), i ->
                this.addSlot(new SlotItemHandler(itemHandler, te.getSlotCount() - i - 1, 116, te.getRecipeList().getMaxOutputs() == 1 ? 35 : 48 - i * 22))
        );
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        listeners.forEach(listener -> {
            listener.sendWindowProperty(this, 2, ((FuelBasedTE) te).getBurnTime());
            listener.sendWindowProperty(this, 3, ((FuelBasedTE) te).getMaxBurnTime());
        });
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id) {
            case 2:
                ((FuelBasedTE) te).setBurnTime(data);
                break;
            case 3:
                if (!te.getCurrentRecipe().isEmpty())
                    ((FuelBasedTE) te).setMaxBurnTime(data);
                break;
        }
    }
}