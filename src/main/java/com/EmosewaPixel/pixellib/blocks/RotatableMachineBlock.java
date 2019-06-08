package com.EmosewaPixel.pixellib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import java.util.function.Supplier;

public class RotatableMachineBlock extends MachineBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public RotatableMachineBlock(String name, Supplier<TileEntity> te) {
        super(name, te);
        this.setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    public RotatableMachineBlock(Properties properties, String name, Supplier<TileEntity> te) {
        super(properties, name, te);
        this.setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }
}