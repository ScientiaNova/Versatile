package com.EmosewaPixel.pixellib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import java.util.function.Supplier;

public class BlockRotateableMachine extends BlockMachine {
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;

    public BlockRotateableMachine(String name, Supplier<TileEntity> te) {
        super(name, te);
        this.setDefaultState(stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    public BlockRotateableMachine(Properties properties, String name, Supplier<TileEntity> te) {
        super(properties, name, te);
        this.setDefaultState(stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    public IBlockState rotate(IBlockState state, Rotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public IBlockState mirror(IBlockState state, Mirror mirror) {
        return state.rotate(mirror.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }
}