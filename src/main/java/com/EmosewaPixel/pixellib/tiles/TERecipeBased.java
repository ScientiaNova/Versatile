package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import com.EmosewaPixel.pixellib.recipes.SimpleRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public class TERecipeBased extends AbstractTERecipeBased<SimpleMachineRecipe> {
    public TERecipeBased(TileEntityType type, SimpleRecipeList recipeList) {
        super(type, recipeList);
        setCurrentRecipe(SimpleMachineRecipe.EMPTY);
    }

    public SimpleMachineRecipe getRecipeByInput() {
        ItemStack[] stacks = new ItemStack[input.getSlots()];
        for (int i = 0; i < input.getSlots(); i++) {
            if (input.getStackInSlot(i).isEmpty())
                return SimpleMachineRecipe.EMPTY;
            stacks[i] = input.getStackInSlot(i);
        }

        ItemStack recipeInputs[] = new ItemStack[input.getSlots()];
        SimpleMachineRecipe returnRecipe;
        for (SimpleMachineRecipe recipe : getRecipeList().getReipes())
            if (recipe.isInputValid(stacks)) {
                for (int i = 0; i < input.getSlots(); i++)
                    recipeInputs[i] = new ItemStack(input.getStackInSlot(i).getItem(), recipe.getCountOfInputItem(input.getStackInSlot(i)));
                returnRecipe = new SimpleMachineRecipe(recipeInputs, recipe.getAllOutputs(), recipe.getTime());
                return returnRecipe;
            }
        return SimpleMachineRecipe.EMPTY;
    }
}
