package com.EmosewaPixel.pixellib.items;

import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.lists.ObjTypes;
import com.EmosewaPixel.pixellib.materialSystem.types.ItemType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

//This class is used for generating Items for all the possible Material-Object Type combinations
public class ItemRegistry {
    public static void registry(RegistryEvent.Register<Item> e) {
        Materials.getAll().forEach(mat ->
                ObjTypes.getAll().stream()
                        .filter(type -> (type instanceof ItemType && type.isMaterialCompatible(mat) && !MaterialItems.contains(mat, type) && !mat.hasBlacklisted(type)))
                        .forEach(type -> register(new MaterialItem(mat, type), e))
        );
    }

    private static Item register(Item item, RegistryEvent.Register<Item> e) {
        e.getRegistry().register(item);
        return item;
    }
}