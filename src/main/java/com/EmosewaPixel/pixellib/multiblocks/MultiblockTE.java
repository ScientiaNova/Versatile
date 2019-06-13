package com.EmosewaPixel.pixellib.multiblocks;

import com.EmosewaPixel.pixellib.blocks.RotatableMachineBlock;
import com.EmosewaPixel.pixellib.recipes.AbstractRecipeList;
import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import com.EmosewaPixel.pixellib.tiles.AbstractRecipeBasedTE;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class MultiblockTE<T extends SimpleMachineRecipe> extends AbstractRecipeBasedTE<T> {
    protected List<IMultiblockPart> partList;

    public MultiblockTE(TileEntityType type, AbstractRecipeList<T, ?> recipeList) {
        super(type, recipeList);
    }

    abstract protected MultiblockPatternBuilder getPattern();

    protected boolean isValidStructure() {
        BlockState state = getBlockState();
        Map<BlockPos, Predicate<BlockState>> pattern = getRotatedPattern();
        final BlockPos posInPattern = pattern.entrySet().stream().filter(e -> e.getValue().test(state)).map(Map.Entry::getKey).findFirst().get();

        if (posInPattern == null)
            return false;

        partList = pattern.keySet().stream().filter(pos -> world.getTileEntity(pos) instanceof IMultiblockPart).map(pos -> (IMultiblockPart) world.getTileEntity(pos)).collect(Collectors.toList());

        return pattern.keySet().stream().allMatch(pos -> pattern.get(pos).test(world.getBlockState(getPos().subtract(posInPattern).add(pos))));
    }

    protected Predicate<Block> selfPredicate() {
        return b -> b == this.getBlockState().getBlock();
    }

    protected Map<BlockPos, Predicate<BlockState>> getRotatedPattern() {
        if (getBlockState().getValues().containsKey(RotatableMachineBlock.FACING)) {
            switch (getBlockState().get(RotatableMachineBlock.FACING)) {
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