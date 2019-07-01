package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import com.EmosewaPixel.pixellib.recipes.SimpleRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

import java.util.List;
import java.util.stream.Collectors;
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

        SimpleMachineRecipe chosenRecipe = getRecipeList().getRecipes().stream().filter(recipe -> recipe.isInputValid((ItemStack[]) stacksStream.toArray())).findFirst().get();

        if (chosenRecipe == null)
            return SimpleMachineRecipe.EMPTY;

        List<Integer> recipeIndexes = IntStream.range(0, getRecipeList().getMaxInputs())
                .map(i -> chosenRecipe.getIndexOfInput(recipeInventory.getStackInSlot(i))).boxed().collect(Collectors.toList());

        if (recipeIndexes.contains(-1))
            return SimpleMachineRecipe.EMPTY;

        return new SimpleMachineRecipe(
                IntStream.range(0, getRecipeList().getMaxInputs()).mapToObj(i -> new ItemStack(recipeInventory.getStackInSlot(i).getItem(), chosenRecipe.getCountOfInput(recipeIndexes.get(i)))).toArray(),
                (Float[]) recipeIndexes.stream().map(chosenRecipe::getConsumeChance).toArray(),
                chosenRecipe.getAllOutputs(),
                chosenRecipe.getOutputChances(),
                chosenRecipe.getTime());
    }
}