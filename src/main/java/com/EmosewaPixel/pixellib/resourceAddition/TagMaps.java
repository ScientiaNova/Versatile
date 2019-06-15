package com.EmosewaPixel.pixellib.resourceAddition;

import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class TagMaps {
    protected static ListMultimap<String, Item> ITEM_TAGS = MultimapBuilder.treeKeys().arrayListValues().build();
    protected static ListMultimap<String, Block> BLOCK_TAGS = MultimapBuilder.treeKeys().arrayListValues().build();

    public static void addMatItemToTag(IMaterialItem item) {
        if (item instanceof Item) {
            addItemToTag(item.getObjType().getName() + "s/" + item.getMaterial().getName(), (Item) item);
            addItemToTag(item.getObjType().getName() + "s", (Item) item);
            if (item.getMaterial().hasSecondName())
                addItemToTag(item.getObjType().getName() + "s/" + item.getMaterial().getSecondName(), (Item) item);
        }
        if (item instanceof Block) {
            addBlockToTag(item.getObjType().getName() + "s/" + item.getMaterial().getName(), (Block) item);
            addBlockToTag(item.getObjType().getName() + "s", (Block) item);
            if (item.getMaterial().hasSecondName())
                addBlockToTag(item.getObjType().getName() + "s/" + item.getMaterial().getSecondName(), (Block) item);
        }
    }

    public static void addItemToTag(String tag, Item item) {
        ITEM_TAGS.put(tag, item);
    }

    public static void addBlockToTag(String tag, Block block) {
        BLOCK_TAGS.put(tag, block);
        ITEM_TAGS.put(tag, Item.getItemFromBlock(block));
    }
}
