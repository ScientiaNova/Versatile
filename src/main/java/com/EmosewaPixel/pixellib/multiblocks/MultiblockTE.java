package com.EmosewaPixel.pixellib.multiblocks;

import com.EmosewaPixel.pixellib.recipes.AbstractRecipeList;
import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import com.EmosewaPixel.pixellib.tiles.AbstractTERecipeBased;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.function.Predicate;

public abstract class MultiblockTE<T extends SimpleMachineRecipe> extends AbstractTERecipeBased<T> {
    public MultiblockTE(TileEntityType type, AbstractRecipeList<T, ?> recipeList) {
        super(type, recipeList);
    }

    abstract protected MultiblockPatternBuilder getPattern();

    protected boolean isValidStructure() {
        BlockPos posInPattern = null;
        List<List<List<Predicate<IBlockState>>>> pattern = getPattern().getPattern();
        for (int i = 0; i < pattern.size(); i++)
            for (int j = 0; j < pattern.get(i).size(); j++)
                for (int k = 0; k < pattern.get(i).get(j).size(); k++)
                    if (pattern.get(i).get(j).get(k).test(getBlockState())) {
                        posInPattern = new BlockPos(i, j, k);
                        break;
                    }

        if (posInPattern == null)
            return false;

        for (int i = 0; i < pattern.size(); i++)
            for (int j = 0; j < pattern.get(i).size(); j++)
                for (int k = 0; k < pattern.get(i).get(j).size(); k++)
                    if (!pattern.get(i).get(j).get(k).test(world.getBlockState(getPos().subtract(posInPattern).add(i, j, k))))
                        return false;

        return true;
    }

    protected Predicate<Block> selfPredicate() {
        return b -> b == this.getBlockState().getBlock();
    }
}