package com.EmosewaPixel.pixellib.items;

import com.EmosewaPixel.pixellib.materialSystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.lists.ObjTypes;
import com.EmosewaPixel.pixellib.materialSystem.lists.TextureTypes;
import com.EmosewaPixel.pixellib.materialSystem.types.ItemType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
    public static void registry(RegistryEvent.Register<Item> e) {
        Materials.getAll().forEach(mat ->
                ObjTypes.getAll().stream()
                        .filter(type -> (type instanceof ItemType && type.isMaterialCompatible(mat) && !MaterialItems.contains(mat, type) && !mat.hasBlacklisted(type)))
                        .forEach(type -> register(new MaterialItem(mat, type), e))
        );

        ObjTypes.getAll().stream().filter(objT -> objT instanceof ItemType).forEach(objT -> {
            if (objT.hasTag(MaterialRegistry.SINGLE_TEXTURE_TYPE))
                register(new Item(new Item.Properties()).setRegistryName("pixellib:" + objT.getName()), e);
            else
                TextureTypes.getAll().forEach(textureT -> register(new Item(new Item.Properties()).setRegistryName("pixellib:" + textureT.toString() + "_" + objT.getName()), e));
        });
    }

    private static Item register(Item item, RegistryEvent.Register<Item> e) {
        e.getRegistry().register(item);
        return item;
    }
}