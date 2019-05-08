package com.EmosewaPixel.pixellib.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SimpleMachineRecipe {
    private Object[] input;
    private Object[] output;
    private int time;
    public static final SimpleMachineRecipe EMPTY = new SimpleMachineRecipe(null, null, 0);

    public SimpleMachineRecipe(Object[] input, Object[] output, int time) {
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public Object getInput(int index) {
        return input[index];
    }

    public int getInputCount(int index) {
        Object obj = getInput(index);
        if (obj instanceof ItemStack)
            return ((ItemStack) obj).getCount();
        if (obj instanceof TagStack)
            return ((TagStack) obj).getCount();
        return 0;
    }

    public Object[] getAllInputs() {
        return input;
    }

    public ItemStack getOutput(int index) {
        if (output[index] instanceof ItemStack)
            return (ItemStack) output[index];
        else if (output[index] instanceof TagStack)
            return ((TagStack) output[index]).asItemStack();
        return null;
    }

    public ItemStack[] getAllOutputs() {
        ItemStack[] outputStacks = new ItemStack[output.length];
        for (int i = 0; i < output.length; i++)
            if (output[i] instanceof ItemStack)
                outputStacks[i] = (ItemStack) output[i];
            else if (output[i] instanceof TagStack)
                outputStacks[i] = ((TagStack) output[i]).asItemStack();
        return outputStacks;
    }

    public List<List<ItemStack>> getInputsAsList() {
        List<List<ItemStack>> list = new ArrayList<>();
        for (Object stack : input) {
            List<ItemStack> singleList = new ArrayList<>();
            if (stack instanceof ItemStack)
                singleList.add((ItemStack) stack);
            else
                for (Item stackitem : ((TagStack) stack).geTag().getAllElements())
                    singleList.add(new ItemStack(stackitem, ((TagStack) stack).getCount()));
            list.add(singleList);
        }

        return list;
    }

    public List<ItemStack> getOutputsAsList() {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack stack : getAllOutputs())
            list.add(stack);
        return list;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isInputValid(ItemStack[] stacks) {
        int matches = 0;

        if (stacks.length != input.length)
            return false;

        for (ItemStack stack : stacks) {
            if (stack.isEmpty())
                return false;
            for (Object stackRec : input) {
                if (stackRec instanceof ItemStack) {
                    if (((ItemStack) stackRec).isEmpty())
                        return false;
                    if (stack.getItem() == ((ItemStack) stackRec).getItem() && stack.getCount() >= ((ItemStack) stackRec).getCount())
                        matches++;
                }
                if (stackRec instanceof TagStack) {
                    if (((TagStack) stackRec).isEmpty())
                        return false;
                    if (((TagStack) stackRec).geTag().contains(stack.getItem()) && stack.getCount() >= ((TagStack) stackRec).getCount())
                        matches++;
                }
            }
        }

        return matches == stacks.length;
    }

    public int getCountOfInputItem(ItemStack stack) {
        for (Object input : input) {
            if (input instanceof ItemStack) {
                if (stack.getItem() == ((ItemStack) input).getItem())
                    return ((ItemStack) input).getCount();
            } else if (((TagStack) input).geTag().contains(stack.getItem()))
                return ((TagStack) input).getCount();
        }
        return 0;
    }

    public boolean itemBelongsInRecipe(ItemStack stack) {
        if (stack.isEmpty() || input == null)
            return false;

        for (Object stackRec : input) {
            if (stackRec instanceof ItemStack) {
                if (((ItemStack) stackRec).getItem() == stack.getItem())
                    return true;
            } else if (((TagStack) stackRec).geTag().contains(stack.getItem()))
                return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
