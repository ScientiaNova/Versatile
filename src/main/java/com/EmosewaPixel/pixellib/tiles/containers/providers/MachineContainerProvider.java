package com.EmosewaPixel.pixellib.tiles.containers.interfaces;

import com.EmosewaPixel.pixellib.tiles.RecipeBasedTE;
import com.EmosewaPixel.pixellib.tiles.containers.RecipeBasedMachineContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class MachineContainerProvider extends AbstractMachineContainerProvider {
    public MachineContainerProvider(BlockPos pos, ResourceLocation name) {
        super(pos, name);
    }

    @Override
    public Container createMenu(int id, PlayerInventory inventoryPlayer, PlayerEntity entityPlayer) {
        return new RecipeBasedMachineContainer(inventoryPlayer, (RecipeBasedTE) entityPlayer.world.getTileEntity(pos));
    }
}