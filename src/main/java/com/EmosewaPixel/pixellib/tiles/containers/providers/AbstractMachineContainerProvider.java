package com.EmosewaPixel.pixellib.tiles.containers.providers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class AbstractMachineContainerProvider implements INamedContainerProvider {
    protected BlockPos pos;
    private ResourceLocation name;

    public AbstractMachineContainerProvider(BlockPos pos, ResourceLocation name) {
        this.pos = pos;
        this.name = name;
    }

    @Override
    public abstract Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity);

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block." + name.getNamespace() + "." + name.getPath(), new Object[0]);
    }
}