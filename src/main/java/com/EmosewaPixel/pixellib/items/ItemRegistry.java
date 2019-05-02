package com.EmosewaPixel.pixellib.items;

import com.EmosewaPixel.pixellib.items.tools.*;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.lists.TextureTypes;
import com.EmosewaPixel.pixellib.materialSystem.lists.ObjTypes;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.ItemType;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
    public static void registry(RegistryEvent.Register<Item> e) {
        for (Material mat : Materials.getAll()) {
            for (ObjectType type : ObjTypes.getAll())
                if (type instanceof ItemType)
                    if (MaterialItems.getItem(mat, type) instanceof MaterialItem)
                        register(new MaterialItem(mat, type), e);

            register(new MaterialAxe(mat), e);
            register(new MaterialHoe(mat), e);
            register(new MaterialPick(mat), e);
            register(new MaterialShovel(mat), e);
            register(new MaterialSword(mat), e);
        }

        for (ObjectType objT : ObjTypes.getAll())
            if (objT instanceof ItemType)
                for (TextureType textureT : TextureTypes.getAll())
                    register(new Item(new Item.Properties()).setRegistryName("pixellib:" + textureT.toString() + "_" + objT.getName()), e);
    }

    private static Item register(Item item, RegistryEvent.Register<Item> e) {
        e.getRegistry().register(item);
        return item;
    }
}
