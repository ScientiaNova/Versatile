package com.EmosewaPixel.pixellib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.ToolType;

import java.util.Collections;
import java.util.List;

//Mod Block is used for creating a regular Block with a specified registry name and harvest level
public class ModBlock extends Block {
    private int level;

    public ModBlock(Block.Properties properties, String name, int level) {
        super(properties);
        this.level = level;
        setRegistryName(name);
    }

    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return level;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Collections.singletonList(new ItemStack(this));
    }
}