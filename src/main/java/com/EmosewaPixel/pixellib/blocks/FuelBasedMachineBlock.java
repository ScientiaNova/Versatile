package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.tiles.containers.providers.FueledMachineContainerProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;
import java.util.function.Supplier;

//Fueled Machine Blocks are Blocks that have TEs which consume fuel and can give off light
public class FuelBasedMachineBlock extends RotatableMachineBlock {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public FuelBasedMachineBlock(String name, Supplier<TileEntity> te, ContainerType<?> containerType) {
        super(name, te, containerType);
        this.setDefaultState(stateContainer.getBaseState().with(LIT, false));
    }

    public FuelBasedMachineBlock(Block.Properties properties, String name, Supplier<TileEntity> te, ContainerType<?> containerType) {
        super(properties, name, te, containerType);
        this.setDefaultState(stateContainer.getBaseState().with(LIT, false));
    }

    @Override
    public int getLightValue(BlockState state) {
        return state.get(LIT) ? 13 : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.get(LIT)) {
            double x = (double) pos.getX() + 0.5D;
            double y = (double) pos.getY();
            double z = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction facing = state.get(FACING);
            Direction.Axis axis = facing.getAxis();
            double lvt_15_1_ = rand.nextDouble() * 0.6D - 0.3D;
            double lvt_17_1_ = axis == Direction.Axis.X ? (double) facing.getXOffset() * 0.52D : lvt_15_1_;
            double lvt_19_1_ = rand.nextDouble() * 6.0D / 16.0D;
            double lvt_21_1_ = axis == Direction.Axis.Z ? (double) facing.getZOffset() * 0.52D : lvt_15_1_;
            world.addParticle(ParticleTypes.SMOKE, x + lvt_17_1_, y + lvt_19_1_, z + lvt_21_1_, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, x + lvt_17_1_, y + lvt_19_1_, z + lvt_21_1_, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, LIT);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote)
            if (containerType != null)
                NetworkHooks.openGui((ServerPlayerEntity) player, new FueledMachineContainerProvider(pos, getRegistryName(), containerType), pos);
        return true;
    }
}