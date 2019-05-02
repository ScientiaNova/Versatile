package com.EmosewaPixel.pixellib.materialSystem.types;

import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import net.minecraft.block.Block;

import java.util.function.Predicate;

public class BlockType extends ObjectType {
    private Block.Properties properties;

    public BlockType(String name, Predicate<Material> requirement, Block.Properties properties) {
        super(name, requirement);
        this.properties=properties;
    }

    public Block.Properties getProperties() {
        return properties;
    }
}