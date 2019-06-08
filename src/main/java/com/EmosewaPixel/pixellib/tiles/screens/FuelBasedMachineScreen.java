package com.EmosewaPixel.pixellib.tiles.screens;

import com.EmosewaPixel.pixellib.tiles.FuelBasedTE;
import com.EmosewaPixel.pixellib.tiles.containers.FuelBasedMachineContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;

public class FuelBasedMachineScreen extends RecipeBasedMachineScreen<FuelBasedTE> {
    public FuelBasedMachineScreen(IInventory playerInventory, FuelBasedTE te, String backGround) {
        super(new FuelBasedMachineContainer(playerInventory, te), playerInventory, te, backGround);
    }

    public FuelBasedMachineScreen(FuelBasedMachineContainer container, PlayerInventory playerInventory, FuelBasedTE te, String backGround) {
        super(container, playerInventory, te, backGround);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int burnTime;
        if (te.getBurnTime() > 0) {
            burnTime = getBurnLeftScaled(13);
            drawTexturedModalRect(guiLeft + 56 - (te.getRecipeList().getMaxInputs() - 1) * 9, guiTop + 36 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 1);
        }
    }

    private int getBurnLeftScaled(int scale) {
        return (int) ((float) te.getBurnTime() / te.getMaxBurnTime() * scale);
    }
}