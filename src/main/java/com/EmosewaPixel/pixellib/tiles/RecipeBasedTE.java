package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import com.EmosewaPixel.pixellib.recipes.SimpleRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public class RecipeBasedTE extends AbstractRecipeBasedTE<SimpleMachineRecipe> {
    public RecipeBasedTE(TileEntityType type, SimpleRecipeList recipeList) {
        super(type, recipeList);
        setCurrentRecipe(SimpleMachineRecipe.EMPTY);
    }

    public SimpleMachineRecipe getRecipeByInput() {
        ItemStack[] stacks = new ItemStack[getRecipeList().getMaxInputs()];
        for (int i = 0; i < getRecipeList().getMaxInputs(); i++) {
            if (recipeInventory.getStackInSlot(i).isEmpty())
                return SimpleMachineRecipe.EMPTY;
            stacks[i] = recipeInventory.getStackInSlot(i);
        }

        ItemStack recipeInputs[] = new ItemStack[getRecipeList().getMaxInputs()];
        SimpleMachineRecipe returnRecipe;
        for (SimpleMachineRecipe recipe : getRecipeList().getReipes())
            if (recipe.isInputValid(stacks)) {
                for (int i = 0; i < getRecipeList().getMaxInputs(); i++)
                    recipeInputs[i] = new ItemStack(recipeInventory.getStackInSlot(i).getItem(), recipe.getCountOfInputItem(recipeInventory.getStackInSlot(i)));
                returnRecipe = new SimpleMachineRecipe(recipeInputs, recipe.getAllOutputs(), recipe.getTime());
                return returnRecipe;
            }
        return SimpleMachineRecipe.EMPTY;
    }
}