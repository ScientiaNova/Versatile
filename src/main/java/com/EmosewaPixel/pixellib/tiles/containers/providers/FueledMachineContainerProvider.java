package com.EmosewaPixel.pixellib.tiles.containers.interfaces;

import com.EmosewaPixel.pixellib.tiles.FuelBasedTE;
import com.EmosewaPixel.pixellib.tiles.containers.FuelBasedMachineContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class FueledMachineContainerProvider extends MachineContainerProvider {
    public FueledMachineContainerProvider(BlockPos pos, ResourceLocation name) {
        super(pos, name);
    }

    @Override
    public Container createMenu(int id, PlayerInventory inventoryPlayer, PlayerEntity entityPlayer) {
        return new FuelBasedMachineContainer(inventoryPlayer, (FuelBasedTE) entityPlayer.world.getTileEntity(pos));
    }
}