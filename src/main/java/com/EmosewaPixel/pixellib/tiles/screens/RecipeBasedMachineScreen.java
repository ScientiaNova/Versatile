package com.EmosewaPixel.pixellib.tiles.screens;

import com.EmosewaPixel.pixellib.tiles.AbstractRecipeBasedTE;
import com.EmosewaPixel.pixellib.tiles.containers.RecipeBasedMachineContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class RecipeBasedMachineScreen<T extends AbstractRecipeBasedTE> extends ContainerScreen<RecipeBasedMachineContainer<T>> {
    protected T te;
    private String backGround;
    private IInventory playerInventory;

    public RecipeBasedMachineScreen(PlayerInventory playerInventory, T te, String backGround) {
        super(new RecipeBasedMachineContainer<>(playerInventory, te), playerInventory);
        this.backGround = backGround;
        this.playerInventory = playerInventory;
        this.te = te;
    }

    public RecipeBasedMachineScreen(RecipeBasedMachineContainer container, IInventory playerInventory, T te, String backGround) {
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