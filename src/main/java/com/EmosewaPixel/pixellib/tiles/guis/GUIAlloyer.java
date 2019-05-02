package com.EmosewaPixel.pixellib.tiles.guis;

import com.EmosewaPixel.pixellib.tiles.TileEntityAlloyer;
import net.minecraft.inventory.IInventory;

public class GUIAlloyer extends GUIMachineBase {
    public GUIAlloyer(IInventory playerInventory, TileEntityAlloyer te) {
        super(playerInventory, te, "pixellib:textures/gui/container/alloyer.png");
    }
}
