package com.EmosewaPixel.pixellib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.ToolType;

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
}