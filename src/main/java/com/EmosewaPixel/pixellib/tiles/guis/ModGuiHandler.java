package com.EmosewaPixel.pixellib.tiles.guis;

import com.EmosewaPixel.pixellib.tiles.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class ModGuiHandler {
    public static GuiScreen guis(FMLPlayMessages.OpenContainer container) {
        EntityPlayer player = Minecraft.getInstance().player;
        TileEntity tile = player.world.getTileEntity(container.getAdditionalData().readBlockPos());
        switch (container.getId().toString()) {
            case "pixellib:alloyer":
                return new GUIAlloyer(player.inventory, (TileEntityAlloyer) tile);
            case "pixellib:blast_furnace":
                return new GUIBlastFurnace(player.inventory, (TileEntityBlastFurnace) tile);
            case "pixellib:coke_oven":
                return new GUICokeOven(player.inventory, (TileEntityCokeOven) tile);
            case "pixellib:crusher":
                return new GUICrusher(player.inventory, (TileEntityCrusher) tile);
            case "pixellib:infusion_table":
                return new GUIInfusionTable(player.inventory, (TileEntityInfusionTable) tile);
            case "pixellib:sawmill":
                return new GUISawmill(player.inventory, (TileEntitySawmill) tile);
            default:
                return null;
        }
    }
}
