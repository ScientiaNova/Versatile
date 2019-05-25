package com.EmosewaPixel.pixellib.multiblocks;

import com.EmosewaPixel.pixellib.blocks.BlockRotateableMachine;
import com.EmosewaPixel.pixellib.recipes.AbstractRecipeList;
import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import com.EmosewaPixel.pixellib.tiles.AbstractTERecipeBased;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class MultiblockTE<T extends SimpleMachineRecipe> extends AbstractTERecipeBased<T> {
    protected ArrayList<IMultiblockPart> partList = new ArrayList<>();

    public MultiblockTE(TileEntityType type, AbstractRecipeList<T, ?> recipeList) {
        super(type, recipeList);
    }

    abstract protected MultiblockPatternBuilder getPattern();

    protected boolean isValidStructure() {
        BlockPos posInPattern = null;
        Map<BlockPos, Predicate<IBlockState>> pattern = getRotatedPattern();
        for (Map.Entry<BlockPos, Predicate<IBlockState>> predicate : pattern.entrySet())
            if (predicate.getValue().test(getBlockState())) {
                posInPattern = predicate.getKey();
                break;
            }

        if (posInPattern == null)
            return false;

        for (BlockPos pos : pattern.keySet()) {
            if (!pattern.get(pos).test(world.getBlockState(getPos().subtract(posInPattern).add(pos))))
                return false;
            if (world.getTileEntity(pos) instanceof IMultiblockPart)
                partList.add((IMultiblockPart) world.getTileEntity(pos));
        }

        return true;
    }

    protected Predicate<Block> selfPredicate() {
        return b -> b == this.getBlockState().getBlock();
    }

    protected Map<BlockPos, Predicate<IBlockState>> getRotatedPattern() {
        if (getBlockState().getValues().containsKey(BlockRotateableMachine.FACING)) {
            switch (getBlockState().get(BlockRotateableMachine.FACING)) {
                case SOUTH:
                    return getPattern().getPatternMap().entrySet().stream().map(e -> new Pair<>(new BlockPos(e.getKey().getX() * -1, e.getKey().getY(), e.getKey().getZ() * -1), e.getValue())).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
                case EAST:
                    return getPattern().getPatternMap().entrySet().stream().map(e -> new Pair<>(new BlockPos(e.getKey().getZ() * -1, e.getKey().getY(), e.getKey().getX()), e.getValue())).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
                case WEST:
                    return getPattern().getPatternMap().entrySet().stream().map(e -> new Pair<>(new BlockPos(e.getKey().getZ(), e.getKey().getY(), e.getKey().getX() * -1), e.getValue())).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
            }
        }
        return getPattern().getPatternMap();
    }
}