package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.tiles.PoweredTE;
import com.EmosewaPixel.pixellib.tiles.containers.providers.MachineContainerProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

//Activatable Machine Blocks are Blocks that can TEs which can have an active state
public class ActivatableMachineBlock extends RotatableMachineBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public ActivatableMachineBlock(String name, Supplier<TileEntity> te, @Nullable ContainerType<?> containerType) {
        super(name, te, containerType);
        this.setDefaultState(stateContainer.getBaseState().with(ACTIVE, false));
    }

    public ActivatableMachineBlock(Properties properties, String name, Supplier<TileEntity> te, @Nullable ContainerType<?> containerType) {
        super(properties, name, te, containerType);
        this.setDefaultState(stateContainer.getBaseState().with(ACTIVE, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, ACTIVE);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote)
            if (worldIn.getTileEntity(pos) instanceof PoweredTE)
                NetworkHooks.openGui((ServerPlayerEntity) player, new MachineContainerProvider(pos, getRegistryName(), containerType), pos);
        return true;
    }
}