package com.EmosewaPixel.pixellib.items;

import com.EmosewaPixel.pixellib.materialSystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.lists.ObjTypes;
import com.EmosewaPixel.pixellib.materialSystem.lists.TextureTypes;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.ItemType;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
    public static void registry(RegistryEvent.Register<Item> e) {
        for (Material mat : Materials.getAll())
            for (ObjectType type : ObjTypes.getAll())
                if (type instanceof ItemType && type.isMaterialCompatible(mat) && !MaterialItems.contains(mat, type) && !mat.hasBlacklisted(type))
                    register(new MaterialItem(mat, type), e);

        for (ObjectType objT : ObjTypes.getAll())
            if (objT instanceof ItemType)
                if (objT.hasTag(MaterialRegistry.SINGLE_TEXTURE_TYPE))
                    register(new Item(new Item.Properties()).setRegistryName("pixellib:" + objT.getName()), e);
                else
                    for (TextureType textureT : TextureTypes.getAll())
                        register(new Item(new Item.Properties()).setRegistryName("pixellib:" + textureT.toString() + "_" + objT.getName()), e);
    }

    private static Item register(Item item, RegistryEvent.Register<Item> e) {
        e.getRegistry().register(item);
        return item;
    }
}