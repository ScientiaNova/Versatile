package com.EmosewaPixel.pixellib.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;

public class TagStack {
    private Tag<Item> tag;
    private int count;

    public TagStack(Tag<Item> tag, int count) {
        this.tag = tag;
        this.count = count;
    }

    public TagStack(Tag<Item> tag) {
        this(tag, 1);
    }

    public Tag<Item> geTag() {
        return tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public TagStack copy() {
        return new TagStack(this.tag, this.count);
    }

    public ItemStack asItemStack() {
        return new ItemStack(tag.getAllElements().stream().findFirst().orElse(Items.AIR), count);
    }
}