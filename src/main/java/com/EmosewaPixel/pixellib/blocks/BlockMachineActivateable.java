package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.tiles.TEPowered;
import com.EmosewaPixel.pixellib.tiles.containers.interfaces.ContainerMachineInterface;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockMachineActivateable extends BlockRotateableMachine {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public BlockMachineActivateable(ResourceLocation name) {
        super(name);
        this.setDefaultState(stateContainer.getBaseState().with(ACTIVE, false));
    }

    public BlockMachineActivateable(Properties properties, ResourceLocation name) {
        super(properties, name);
        this.setDefaultState(stateContainer.getBaseState().with(ACTIVE, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> stateBuilder) {
        stateBuilder.add(FACING, ACTIVE);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
            if (worldIn.getTileEntity(pos) instanceof TEPowered)
                NetworkHooks.openGui((EntityPlayerMP) player, new ContainerMachineInterface(pos, getRegistryName()), pos);
        return true;
    }
}
