package com.EmosewaPixel.pixellib.items;

import com.EmosewaPixel.pixellib.PixelLib;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//Self Container Items are items that don't get used up in crafting recipes
public class SelfContainerItem extends Item {
    public SelfContainerItem(String name) {
        super(new Item.Properties().group(PixelLib.main));
        setRegistryName(name);
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