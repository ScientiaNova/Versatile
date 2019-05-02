package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.tiles.TileEntityInfusionTable;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerInfusionTable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockInfusionTable extends Block implements ITileEntityProvider {
    public BlockInfusionTable() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
        setRegistryName("pixellib:infusion_table");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityInfusionTable();
    }

    @Override
    public ToolType getHarvestTool(IBlockState state) {
        return ToolType.AXE;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (te instanceof TileEntityInfusionTable)
            ((TileEntityInfusionTable) te).dropInventory();
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

    @Deprecated
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
            NetworkHooks.openGui((EntityPlayerMP) player, new InfusionTableInterface(pos), pos);
        return true;
    }

    public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean p_196243_5_) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityInfusionTable) {
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

    public class InfusionTableInterface implements IInteractionObject {
        private BlockPos pos;

        private InfusionTableInterface(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
            return new ContainerInfusionTable(inventoryPlayer, (TileEntityInfusionTable) entityPlayer.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "pixellib:infusion_table";
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation("block.skyresourcesclassic.infusion_table", new Object[0]);
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
