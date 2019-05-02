package com.EmosewaPixel.pixellib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.ToolType;

public class ModBlock extends Block {
    private int level;

    public ModBlock(Block.Properties properties, String name, int level) {
        super(properties);
        this.level = level;
        setRegistryName("pixellib:" + name);
    }

    @Override
    public ToolType getHarvestTool(IBlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return level;
    }
}