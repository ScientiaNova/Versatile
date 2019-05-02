package com.EmosewaPixel.pixellib.tiles.guis;

import com.EmosewaPixel.pixellib.tiles.TileEntityInfusionTable;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerInfusionTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUIInfusionTable extends GuiContainer {
    private TileEntityInfusionTable te;
    private IInventory playerInventory;

    public GUIInfusionTable(IInventory playerInventory, TileEntityInfusionTable te) {
        super(new ContainerInfusionTable(playerInventory, te));
        this.te = te;
        this.playerInventory = playerInventory;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(new ResourceLocation("pixellib:textures/gui/container/infusion_table.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        if (te.getProgress() > 0 && !te.getCurrentRecipe().isEmpty()) {
            int progress = getProgressLeftScaled(24);
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
}
