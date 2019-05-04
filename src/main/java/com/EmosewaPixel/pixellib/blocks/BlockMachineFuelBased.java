package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.tiles.TileEntityFuelBased;
import com.EmosewaPixel.pixellib.tiles.TileEntityProgressive;
import com.EmosewaPixel.pixellib.tiles.containers.interfaces.ContainerMachineFueledInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;
import java.util.function.Supplier;

public class BlockMachineFuelBased extends BlockRotateableMachine {
    public static final BooleanProperty LIT = BlockRedstoneTorch.LIT;

    public BlockMachineFuelBased(ResourceLocation name, Supplier<TileEntityProgressive> te) {
        super(name, te);
        this.setDefaultState(stateContainer.getBaseState().with(LIT, false));
    }

    public BlockMachineFuelBased(Block.Properties properties, ResourceLocation name, Supplier<TileEntityProgressive> te) {
        super(properties, name, te);
        this.setDefaultState(stateContainer.getBaseState().with(LIT, false));
    }

    @Override
    public int getLightValue(IBlockState state) {
        return state.get(LIT) ? 13 : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (state.get(LIT)) {
            double x = (double) pos.getX() + 0.5D;
            double y = (double) pos.getY();
            double z = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            EnumFacing facing = state.get(FACING);
            EnumFacing.Axis axis = facing.getAxis();
            double lvt_15_1_ = rand.nextDouble() * 0.6D - 0.3D;
            double lvt_17_1_ = axis == EnumFacing.Axis.X ? (double) facing.getXOffset() * 0.52D : lvt_15_1_;
            double lvt_19_1_ = rand.nextDouble() * 6.0D / 16.0D;
            double lvt_21_1_ = axis == EnumFacing.Axis.Z ? (double) facing.getZOffset() * 0.52D : lvt_15_1_;
            world.spawnParticle(Particles.SMOKE, x + lvt_17_1_, y + lvt_19_1_, z + lvt_21_1_, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(Particles.FLAME, x + lvt_17_1_, y + lvt_19_1_, z + lvt_21_1_, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> stateBuilder) {
        stateBuilder.add(FACING, LIT);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
            if (worldIn.getTileEntity(pos) instanceof TileEntityFuelBased)
                NetworkHooks.openGui((EntityPlayerMP) player, new ContainerMachineFueledInterface(pos, getRegistryName()), pos);
        return true;
    }
}
