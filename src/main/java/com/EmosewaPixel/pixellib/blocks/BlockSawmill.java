package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.tiles.TileEntitySawmill;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerSawmill;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockSawmill extends Block implements ITileEntityProvider {
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
    public static final BooleanProperty ON = BooleanProperty.create("on");

    public BlockSawmill() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
        setRegistryName("pixellib:sawmill");
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(ON, false));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntitySawmill();
    }

    @Override
    public ToolType getHarvestTool(IBlockState state) {
        return ToolType.AXE;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (te instanceof TileEntitySawmill)
            ((TileEntitySawmill) te).dropInventory();
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }


    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Deprecated
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
            NetworkHooks.openGui((EntityPlayerMP) player, new SawmillInterface(pos), pos);
        return true;
    }

    public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean p_196243_5_) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntitySawmill) {
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
        stateBuilder.add(new IProperty[]{FACING, ON});
    }

    public class SawmillInterface implements IInteractionObject {
        private BlockPos pos;

        public SawmillInterface(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
            return new ContainerSawmill(inventoryPlayer, (TileEntitySawmill) entityPlayer.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "pixellib:sawmill";
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation("block.skyresourcesclassic.sawmill", new Object[0]);
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
