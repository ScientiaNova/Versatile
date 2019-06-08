package com.EmosewaPixel.pixellib.tiles.containers.providers;

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
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FuelBasedMachineContainer(playerInventory, (FuelBasedTE) playerEntity.world.getTileEntity(pos));
    }
}