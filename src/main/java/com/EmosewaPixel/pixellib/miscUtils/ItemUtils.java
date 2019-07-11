package com.EmosewaPixel.pixellib.miscutils;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//This class contains useful functions for working with items
public class ItemUtils {
    public static void spawnItemInWorld(World world, BlockPos pos, ItemStack stack) {
        world.addEntity(new ItemEntity(world, pos.getX() + 0.5F,
                pos.getY() + 0.5F, pos.getZ() + 0.5F, stack));
    }
}
