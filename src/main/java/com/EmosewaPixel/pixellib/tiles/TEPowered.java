package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.blocks.BlockMachineActivateable;
import com.EmosewaPixel.pixellib.recipes.EnergyMachineRecipe;
import com.EmosewaPixel.pixellib.recipes.EnergyRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TEPowered extends AbstractTERecipeBased<EnergyMachineRecipe> implements IEnergyStorage {
    protected int energy = 0;
    protected int maxPower;
    protected int maxPowerIn;

    public TEPowered(TileEntityType type, EnergyRecipeList recipeList, int maxPower, int maxPowerIn) {
        super(type, recipeList);
        setCurrentRecipe(EnergyMachineRecipe.EMPTY);
        this.maxPower = maxPower;
        this.maxPowerIn = maxPowerIn;
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            EnergyMachineRecipe recipe = getCurrentRecipe();
            if (!recipe.isEmpty()) {
                world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineActivateable.ACTIVE, true));
                if (getProgress() > 0) {
                    world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineActivateable.ACTIVE, true));
                    if (internalExtractEnergy(recipe.getEnergyPerTick(), false)) {
                        subtractProgress(1);
                        if (getProgress() == 0)
                            work();
                    }
                } else
                    startWorking();
            } else {
                world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineActivateable.ACTIVE, false));
                setProgress(0);
            }
            markDirty();
        }
    }

    public EnergyMachineRecipe getRecipeByInput() {
        ItemStack[] stacks = new ItemStack[input.getSlots()];
        for (int i = 0; i < input.getSlots(); i++) {
            if (input.getStackInSlot(i).isEmpty())
                return EnergyMachineRecipe.EMPTY;
            stacks[i] = input.getStackInSlot(i);
        }

        ItemStack recipeInputs[] = new ItemStack[input.getSlots()];
        EnergyMachineRecipe returnRecipe;
        for (EnergyMachineRecipe recipe : getRecipeList().getReipes())
            if (recipe.isInputValid(stacks)) {
                for (int i = 0; i < input.getSlots(); i++)
                    recipeInputs[i] = new ItemStack(input.getStackInSlot(i).getItem(), recipe.getCountOfInputItem(input.getStackInSlot(i)));
                returnRecipe = new EnergyMachineRecipe(recipeInputs, recipe.getAllOutputs(), recipe.getTime(), recipe.getEnergyPerTick());
                return returnRecipe;
            }
        return EnergyMachineRecipe.EMPTY;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(getMaxEnergyStored() - energy, Math.min(maxPowerIn, maxReceive));
        if (!simulate) {
            energy += energyReceived;
            markDirty();
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    public boolean internalExtractEnergy(int extract, boolean simulate) {
        int energyExtract = Math.min(energy, extract);
        if (!simulate) {
            if (energyExtract != extract)
                return false;
            energy -= extract;
            markDirty();
        }
        return energyExtract == extract;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxPower;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityEnergy.ENERGY)
            return LazyOptional.of(() -> this).cast();
        return super.getCapability(cap, side);
    }
}