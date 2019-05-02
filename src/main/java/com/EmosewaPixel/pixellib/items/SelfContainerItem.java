package com.EmosewaPixel.pixellib.items;

import com.EmosewaPixel.pixellib.PixelLib;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SelfContainerItem extends Item {
    public SelfContainerItem(String name) {
        super(new Item.Properties().group(PixelLib.main));
        setRegistryName("pixellib:" + name);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(this);
    }
}