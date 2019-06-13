package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import com.EmosewaPixel.pixellib.recipes.SimpleRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RecipeBasedTE extends AbstractRecipeBasedTE<SimpleMachineRecipe> {
    public RecipeBasedTE(TileEntityType type, SimpleRecipeList recipeList) {
        super(type, recipeList);
        setCurrentRecipe(SimpleMachineRecipe.EMPTY);
    }

    public SimpleMachineRecipe getRecipeByInput() {
        Stream<ItemStack> stacksStream = IntStream.range(0, getRecipeList().getMaxInputs()).mapToObj(i -> recipeInventory.getStackInSlot(i));

        if (stacksStream.anyMatch(ItemStack::isEmpty))
            return SimpleMachineRecipe.EMPTY;

        SimpleMachineRecipe chosenRecipe = getRecipeList().getReipes().stream().filter(recipe -> recipe.isInputValid((ItemStack[]) stacksStream.toArray())).findFirst().get();

        if (chosenRecipe == null)
            return SimpleMachineRecipe.EMPTY;

        ItemStack recipeInputs[] = (ItemStack[]) IntStream.range(0, getRecipeList().getMaxInputs())
                .mapToObj(i -> new ItemStack(recipeInventory.getStackInSlot(i).getItem(), chosenRecipe.getCountOfInputItem(recipeInventory.getStackInSlot(i)))).toArray();
        return new SimpleMachineRecipe(recipeInputs, chosenRecipe.getAllOutputs(), chosenRecipe.getTime());
    }
}