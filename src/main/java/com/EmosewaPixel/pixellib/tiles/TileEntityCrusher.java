package com.EmosewaPixel.pixellib.tiles;

import net.minecraft.util.ITickable;

public class TileEntityCrusher extends TileEntityFurnaceBase implements ITickable {
    public TileEntityCrusher() {
        super(ExpertTypes.CRUSHER, 1, 2, RecipeTypes.crusherRecipes);
    }
}