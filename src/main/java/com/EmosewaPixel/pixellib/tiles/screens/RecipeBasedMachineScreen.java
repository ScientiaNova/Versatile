package com.EmosewaPixel.pixellib.tiles.screens;

import com.EmosewaPixel.pixellib.tiles.AbstractRecipeBasedTE;
import com.EmosewaPixel.pixellib.tiles.containers.RecipeBasedMachineContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class RecipeBasedMachineScreen<T extends AbstractRecipeBasedTE> extends ContainerScreen<RecipeBasedMachineContainer<T>> {
    protected T te;
    private String backGround;
    private PlayerInventory playerInventory;

    public RecipeBasedMachineScreen(RecipeBasedMachineContainer container, PlayerInventory playerInventory, T te, String backGround, ITextComponent textComponent) {
        super(container, playerInventory, textComponent);
        this.backGround = backGround;
        this.playerInventory = playerInventory;
        this.te = te;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        minecraft.getTextureManager().bindTexture(new ResourceLocation(backGround));
        blit(guiLeft, guiTop, 0, 0, xSize, ySize);
        int progress;
        if (te.getProgress() > 0 && !te.getCurrentRecipe().isEmpty()) {
            progress = getProgressLeftScaled(24);
            blit(guiLeft + 79, guiTop + 34, 176, 14, progress + 1, 16);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = te.getBlockState().getBlock().getNameTextComponent().getFormattedText();
        font.drawString(name, xSize / 2 - font.getStringWidth(name) / 2, 6.0F, 4210752);
        font.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, ySize - 96 + 2, 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    private int getProgressLeftScaled(int scale) {
        return (int) (scale - (float) te.getProgress() / te.getCurrentRecipe().getTime() * scale);
    }
}