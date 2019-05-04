package com.EmosewaPixel.pixellib.tiles.containers.interfaces;

import com.EmosewaPixel.pixellib.tiles.TileEntityFuelBased;
import com.EmosewaPixel.pixellib.tiles.containers.ContainerMachineFuelBased;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ContainerMachineFueledInterface extends ContainerMachineInterface {
    public ContainerMachineFueledInterface(BlockPos pos, ResourceLocation name) {
        super(pos, name);
    }

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
        return new ContainerMachineFuelBased(inventoryPlayer, (TileEntityFuelBased) entityPlayer.world.getTileEntity(pos));
    }
}