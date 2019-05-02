package com.EmosewaPixel.pixellib.tiles.guis;

import com.EmosewaPixel.pixellib.tiles.TileEntityBlastFurnace;
import net.minecraft.inventory.IInventory;

public class GUIBlastFurnace extends GUIMachineBase {
    public GUIBlastFurnace(IInventory playerInventory, TileEntityBlastFurnace te) {
        super(playerInventory, te, "textures/gui/container/furnace.png");
    }
}
