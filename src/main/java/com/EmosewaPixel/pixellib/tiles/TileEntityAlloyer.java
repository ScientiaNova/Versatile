package com.EmosewaPixel.pixellib.tiles;

import net.minecraft.util.ITickable;

public class TileEntityAlloyer extends TileEntityFurnaceBase implements ITickable {
    public TileEntityAlloyer() {
        super(ExpertTypes.ALLOYER, 2, 1, RecipeTypes.alloyerRecipes);
    }
}