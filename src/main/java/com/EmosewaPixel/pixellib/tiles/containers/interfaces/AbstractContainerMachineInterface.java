package com.EmosewaPixel.pixellib.tiles.containers.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;

import javax.annotation.Nullable;

public abstract class AbstractContainerMachineInterface implements IInteractionObject {
    protected BlockPos pos;
    private ResourceLocation name;

    public AbstractContainerMachineInterface(BlockPos pos, ResourceLocation name) {
        this.pos = pos;
        this.name = name;
    }

    @Override
    public abstract Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer);

    @Override
    public String getGuiID() {
        return name.toString();
    }

    @Override
    public ITextComponent getName() {
        return new TextComponentTranslation("block." + name.getNamespace() + "." + name.getPath(), new Object[0]);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return null;
    }
}