package com.EmosewaPixel.pixellib.materialSystem.lists;

import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.google.common.collect.HashBasedTable;
import net.minecraft.block.Block;

import java.util.Collection;

public class MaterialBlocks {
    public static HashBasedTable<Material, ObjectType, Block> materialBlocks = HashBasedTable.create();

    public static Block getBlock(Material material, ObjectType type) {
        return materialBlocks.get(material, type);
    }

    public static boolean contains(Material material, ObjectType type) {
        return getBlock(material, type) != null;
    }

    public static void addBlock(Material mat, ObjectType type, Block item) {
        materialBlocks.put(mat, type, item);
    }

    public static void addBlock(IMaterialItem item) {
        if (item instanceof Block)
            addBlock(item.getMaterial(), item.getObjType(), (Block) item);
    }

    public static Collection<Block> getAllBlocks() {
        return materialBlocks.values();
    }
}