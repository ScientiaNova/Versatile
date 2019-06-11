package com.EmosewaPixel.pixellib.tiles.containers.providers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Supplier;

public abstract class AbstractMachineContainerProvider implements INamedContainerProvider {
    protected BlockPos pos;
    protected ContainerType<?> containerType;
    private ResourceLocation name;

    public AbstractMachineContainerProvider(BlockPos pos, ResourceLocation name, ContainerType<?> containerType) {
        this.pos = pos;
        this.name = name;
        this.containerType = containerType;
    }

    @Override
    public abstract Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity);

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block." + name.getNamespace() + "." + name.getPath(), new Object[0]);
    }
}