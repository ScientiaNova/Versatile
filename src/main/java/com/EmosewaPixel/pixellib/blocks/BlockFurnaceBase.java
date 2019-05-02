package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.tiles.TileEntityFurnaceBase;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerMachineBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockFurnaceBase extends Block implements ITileEntityProvider {
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockRedstoneTorch.LIT;

    private String name;

    public BlockFurnaceBase(String name) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
        setRegistryName("pixellib:" + name);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(LIT, false));
        this.name = name;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return null;
    }

    @Override
    public ToolType getHarvestTool(IBlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (te instanceof TileEntityFurnaceBase)
            ((TileEntityFurnaceBase) te).dropInventory();
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }


    public int getLightValue(IBlockState state) {
        return state.get(LIT) ? 13 : 0;
    }

    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Deprecated
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
            NetworkHooks.openGui((EntityPlayerMP) player, new MachineBaseInterface(pos), pos);
        return true;
    }

    public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean p_196243_5_) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityFurnaceBase) {
                world.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, world, pos, newState, p_196243_5_);
        }
    }

    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        return Container.calcRedstone(world.getTileEntity(pos));
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

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public IBlockState rotate(IBlockState state, Rotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public IBlockState mirror(IBlockState state, Mirror mirror) {
        return state.rotate(mirror.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> stateBuilder) {
        stateBuilder.add(new IProperty[]{FACING, LIT});
    }

    public class MachineBaseInterface implements IInteractionObject {
        private BlockPos pos;

        public MachineBaseInterface(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
            return new ContainerMachineBase(inventoryPlayer, (TileEntityFurnaceBase) entityPlayer.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "pixellib:" + name;
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation("block.skyresourcesclassic." + name, new Object[0]);
        }

        @Override
        public boolean hasCustomName() {
            return false;
        }

        @Nullable
        @Override
        public ITextComponent getCustomName() {
            return null;
        }
    }
}
