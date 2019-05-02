package com.EmosewaPixel.pixellib.tiles.guis;

import com.EmosewaPixel.pixellib.tiles.TileEntityFurnaceBase;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerMachineBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUIMachineBase extends GuiContainer {
    protected TileEntityFurnaceBase te;
    private String backGround;
    private IInventory playerInventory;

    public GUIMachineBase(IInventory playerInventory, TileEntityFurnaceBase te, String backGround) {
        super(new ContainerMachineBase(playerInventory, te));
        this.te = te;
        this.backGround = backGround;
        this.playerInventory = playerInventory;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(new ResourceLocation(backGround));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int progress;
        if (te.getBurnTime() > 0) {
            progress = getBurnLeftScaled(13);
            drawTexturedModalRect(guiLeft + 56 - (te.getInputCount() - 1) * 9, guiTop + 36 + 12 - progress, 176, 12 - progress, 14, progress + 1);
        }
        if (te.getProgress() > 0 && !te.getCurrentRecipe().isEmpty()) {
            progress = getProgressLeftScaled(24);
            drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, progress + 1, 16);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = te.getBlockState().getBlock().getNameTextComponent().getFormattedText();
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6.0F, 4210752);
        fontRenderer.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, ySize - 96 + 2, 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

    }

    private int getProgressLeftScaled(int scale) {
        return (int) (scale - (float) te.getProgress() / te.getCurrentRecipe().getTime() * scale);
    }

    private int getBurnLeftScaled(int scale) {
        return (int) ((float) te.getBurnTime() / te.getMaxBurnTime() * scale);
    }
}
