package com.EmosewaPixel.pixellib.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.List;

public class ImprovedItemStackHandler extends ItemStackHandler {
    protected List<Integer> noInputSlots;
    protected List<Integer> noOutputSlots;

    public ImprovedItemStackHandler(int slots, Integer[] noInput, Integer[] noOutput) {
        super(slots);
        this.noInputSlots = Arrays.asList(noInput);
        this.noOutputSlots = Arrays.asList(noOutput);
    }

    public ImprovedItemStackHandler(int slots) {
        this(slots, null, null);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!isItemValid(slot, stack)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return !noInputSlots.contains(slot);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (noOutputSlots.contains(slot))
            return ItemStack.EMPTY;
        return super.extractItem(slot, amount, simulate);
    }
}
