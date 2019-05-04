package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.recipes.MachineRecipe;

public interface ITileEntityRecipeBased {
    MachineRecipe getCurrentRecipe();

    void setCurrentRecipe(MachineRecipe recipe);

    void startWorking();

    void work();
}