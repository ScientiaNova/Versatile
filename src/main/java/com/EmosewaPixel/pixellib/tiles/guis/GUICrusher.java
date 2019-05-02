package com.EmosewaPixel.pixellib.tiles.guis;

import com.EmosewaPixel.pixellib.tiles.TileEntityCrusher;
import net.minecraft.inventory.IInventory;

public class GUICrusher extends GUIMachineBase {
    public GUICrusher(IInventory playerInventory, TileEntityCrusher te) {
        super(playerInventory, te, "pixellib:textures/gui/container/crusher.png");
    }
}
