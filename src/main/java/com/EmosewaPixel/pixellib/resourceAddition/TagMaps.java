package com.EmosewaPixel.pixellib.resourceAddition;

import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class TagMaps {
    protected static ListMultimap<String, Item> ITEM_TAGS = MultimapBuilder.treeKeys().arrayListValues().build();
    protected static ListMultimap<String, Block> BLOCK_TAGS = MultimapBuilder.treeKeys().arrayListValues().build();

    public static void addItemToTag(IMaterialItem item) {
        if (item instanceof Item) {
            ITEM_TAGS.put(item.getObjType().getName() + "s/" + item.getMaterial().getName(), (Item) item);
            ITEM_TAGS.put(item.getObjType().getName() + "s", (Item) item);
        }
        if (item instanceof Block) {
            ITEM_TAGS.put(item.getObjType().getName() + "s/" + item.getMaterial().getName(), Item.getItemFromBlock((Block) item));
            ITEM_TAGS.put(item.getObjType().getName() + "s", Item.getItemFromBlock((Block) item));
            BLOCK_TAGS.put(item.getObjType().getName() + "s/" + item.getMaterial().getName(), (Block) item);
            BLOCK_TAGS.put(item.getObjType().getName() + "s", (Block) item);
        }
    }
}
