package com.EmosewaPixel.pixellib.materialsystem.lists;

import com.EmosewaPixel.pixellib.materialsystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialsystem.materials.Material;
import com.EmosewaPixel.pixellib.materialsystem.types.ObjectType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.block.Block;

import java.util.Collection;

//This class contains functions for interacting with the global list of material blocks
public final class MaterialBlocks {
    private static final HashBasedTable<Material, ObjectType, Block> materialBlocks = HashBasedTable.create();

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

    public static Table.Cell<Material, ObjectType, Block> getBlockCell(Block block) {
        return materialBlocks.cellSet().stream().filter(c -> c.getValue() == block).findFirst().get();
    }

    public static Material getBlockMaterial(Block block) {
        return getBlockCell(block).getRowKey();
    }

    public static ObjectType getBlockObjType(Block block) {
        return getBlockCell(block).getColumnKey();
    }

    public static Collection<Block> getAllBlocks() {
        return materialBlocks.values();
    }
}