package com.EmosewaPixel.pixellib.tiles.guis;

import com.EmosewaPixel.pixellib.tiles.TileEntityRecipeBased;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerMachineRecipeBased;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUIRecipeBasedMachine extends GuiContainer {
    private TileEntityRecipeBased te;
    private String backGround;
    private IInventory playerInventory;

    public GUIRecipeBasedMachine(IInventory playerInventory, TileEntityRecipeBased te, String backGround) {
        super(new ContainerMachineRecipeBased(playerInventory, te));
        this.backGround = backGround;
        this.playerInventory = playerInventory;
        this.te = te;
    }

    public GUIRecipeBasedMachine(ContainerMachineRecipeBased container, IInventory playerInventory, TileEntityRecipeBased te, String backGround) {
        super(container);
        this.backGround = backGround;
        this.playerInventory = playerInventory;
        this.te = te;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(new ResourceLocation(backGround));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int progress;
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
}
