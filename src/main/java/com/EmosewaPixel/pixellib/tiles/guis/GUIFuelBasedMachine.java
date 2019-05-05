package com.EmosewaPixel.pixellib.tiles.guis;

import com.EmosewaPixel.pixellib.tiles.TEFuelBased;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerMachineFuelBased;
import net.minecraft.inventory.IInventory;

public class GUIFuelBasedMachine extends GUIRecipeBasedMachine {
    private TEFuelBased te;

    public GUIFuelBasedMachine(IInventory playerInventory, TEFuelBased te, String backGround) {
        super(new ContainerMachineFuelBased(playerInventory, te), playerInventory, te, backGround);
        this.te = te;
    }

    public GUIFuelBasedMachine(ContainerMachineFuelBased container, IInventory playerInventory, TEFuelBased te, String backGround) {
        super(container, playerInventory, te, backGround);
        this.te = te;
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