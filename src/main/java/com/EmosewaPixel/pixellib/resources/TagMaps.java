package com.EmosewaPixel.pixellib.resources;

import com.EmosewaPixel.pixellib.materialsystem.materials.IMaterialItem;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

//This class contains functions used for adding Item and Block Tags to the fake data pack
public class TagMaps {
    protected static ListMultimap<String, Item> ITEM_TAGS = MultimapBuilder.treeKeys().arrayListValues().build();
    protected static ListMultimap<String, Block> BLOCK_TAGS = MultimapBuilder.treeKeys().arrayListValues().build();

    public static void addMatItemToTag(IMaterialItem item) {
        if (item instanceof Item) {
            addItemToTag(item.getObjType().getName() + "s/" + item.getMat().getName(), (Item) item);
            addItemToTag(item.getObjType().getName() + "s", (Item) item);
            if (item.getMat().hasSecondName())
                addItemToTag(item.getObjType().getName() + "s/" + item.getMat().getSecondName(), (Item) item);
        }
        if (item instanceof Block) {
            addBlockToTag(item.getObjType().getName() + "s/" + item.getMat().getName(), (Block) item);
            addBlockToTag(item.getObjType().getName() + "s", (Block) item);
            if (item.getMat().hasSecondName())
                addBlockToTag(item.getObjType().getName() + "s/" + item.getMat().getSecondName(), (Block) item);
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
