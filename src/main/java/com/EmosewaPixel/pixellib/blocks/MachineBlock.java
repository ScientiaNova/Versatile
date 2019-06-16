package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.tiles.AbstractRecipeBasedTE;
import com.EmosewaPixel.pixellib.tiles.containers.providers.MachineContainerProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

//Machine Blocks are Blocks that have Tes
public class MachineBlock extends Block implements ITileEntityProvider {
    private Supplier<TileEntity> te;
    protected ContainerType<?> containerType;

    public MachineBlock(String name, Supplier<TileEntity> te, @Nullable ContainerType<?> containerType) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
        setRegistryName(name);
        this.te = te;
        this.containerType = containerType;
    }

    public MachineBlock(Block.Properties properties, String name, Supplier<TileEntity> te, @Nullable ContainerType<?> containerType) {
        super(properties);
        setRegistryName(name);
        this.te = te;
        this.containerType = containerType;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader world) {
        return te.get();
    }

    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (te instanceof AbstractRecipeBasedTE)
            ((AbstractRecipeBasedTE) te).dropInventory();
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean p_196243_5_) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof AbstractRecipeBasedTE) {
                world.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, world, pos, newState, p_196243_5_);
        }
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote)
            if (containerType != null)
                NetworkHooks.openGui((ServerPlayerEntity) player, new MachineContainerProvider(pos, getRegistryName(), containerType), pos);
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
        return Container.calcRedstone(world.getTileEntity(pos));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
