package com.EmosewaPixel.pixellib.tiles.containers.providers;

import com.EmosewaPixel.pixellib.tiles.RecipeBasedTE;
import com.EmosewaPixel.pixellib.tiles.containers.RecipeBasedMachineContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class MachineContainerProvider extends AbstractMachineContainerProvider {
    public MachineContainerProvider(BlockPos pos, ResourceLocation name, ContainerType<?> containerType) {
        super(pos, name, containerType);
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new RecipeBasedMachineContainer(playerInventory, (RecipeBasedTE) playerEntity.world.getTileEntity(pos), containerType, id);
    }
}