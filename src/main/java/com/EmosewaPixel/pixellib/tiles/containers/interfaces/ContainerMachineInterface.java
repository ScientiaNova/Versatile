package com.EmosewaPixel.pixellib.tiles.containers.interfaces;

import com.EmosewaPixel.pixellib.tiles.TERecipeBased;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerMachineRecipeBased;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ContainerMachineInterface extends AbstractContainerMachineInterface {
    public ContainerMachineInterface(BlockPos pos, ResourceLocation name) {
        super(pos, name);
    }

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
        return new ContainerMachineRecipeBased(inventoryPlayer, (TERecipeBased) entityPlayer.world.getTileEntity(pos));
    }
}
