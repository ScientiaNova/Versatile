package com.EmosewaPixel.pixellib.materialSystem.lists;

import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.item.Item;

import java.util.Collection;

//This class contains functions for interacting with the global list of material items
public class MaterialItems {
    private static HashBasedTable<Material, ObjectType, Item> materialItems = HashBasedTable.create();

    public static Item getItem(Material material, ObjectType type) {
        return materialItems.get(material, type);
    }

    public static boolean contains(Material material, ObjectType type) {
        return getItem(material, type) != null;
    }

    public static void addItem(Material mat, ObjectType type, Item item) {
        materialItems.put(mat, type, item);
    }

    public static void addItem(IMaterialItem item) {
        if (item instanceof Item)
            addItem(item.getMaterial(), item.getObjType(), (Item) item);
    }

    public static Table.Cell<Material, ObjectType, Item> getItemCell(Item item) {
        return materialItems.cellSet().stream().filter(c -> c.getValue() == item).findFirst().get();
    }

    public static Material getItemMaterial(Item item) {
        return getItemCell(item).getRowKey();
    }

    public static ObjectType getItemObjType(Item item) {
        return getItemCell(item).getColumnKey();
    }

    public static Collection<Item> getAllItems() {
        return materialItems.values();
    }
}