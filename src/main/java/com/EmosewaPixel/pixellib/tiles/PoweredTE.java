package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.blocks.ActivatableMachineBlock;
import com.EmosewaPixel.pixellib.recipes.EnergyMachineRecipe;
import com.EmosewaPixel.pixellib.recipes.EnergyRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PoweredTE extends AbstractRecipeBasedTE<EnergyMachineRecipe> implements IEnergyStorage {
    protected int energy = 0;
    protected int maxPower;
    protected int maxPowerIn;

    public PoweredTE(TileEntityType type, EnergyRecipeList recipeList, int maxPower, int maxPowerIn) {
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
                world.setBlockState(pos, world.getBlockState(pos).with(ActivatableMachineBlock.ACTIVE, true));
                if (getProgress() > 0) {
                    world.setBlockState(pos, world.getBlockState(pos).with(ActivatableMachineBlock.ACTIVE, true));
                    if (internalExtractEnergy(recipe.getEnergyPerTick(), false)) {
                        subtractProgress(1);
                        if (getProgress() == 0)
                            work();
                    }
                } else
                    startWorking();
            } else {
                world.setBlockState(pos, world.getBlockState(pos).with(ActivatableMachineBlock.ACTIVE, false));
                setProgress(0);
            }
            markDirty();
        }
    }

    public EnergyMachineRecipe getRecipeByInput() {
        Stream<ItemStack> stacksStream = IntStream.range(0, getRecipeList().getMaxInputs()).mapToObj(i -> recipeInventory.getStackInSlot(i));

        if (stacksStream.anyMatch(ItemStack::isEmpty))
            return EnergyMachineRecipe.EMPTY;

        EnergyMachineRecipe chosenRecipe = getRecipeList().getRecipes().stream().filter(recipe -> recipe.isInputValid((ItemStack[]) stacksStream.toArray())).findFirst().get();

        if (chosenRecipe == null)
            return EnergyMachineRecipe.EMPTY;

        ItemStack recipeInputs[] = (ItemStack[]) IntStream.range(0, getRecipeList().getMaxInputs()).mapToObj(i ->
                new ItemStack(recipeInventory.getStackInSlot(i).getItem(), chosenRecipe.getCountOfInputItem(recipeInventory.getStackInSlot(i)))).toArray();
        return new EnergyMachineRecipe(recipeInputs, chosenRecipe.getAllOutputs(), chosenRecipe.getTime(), chosenRecipe.getEnergyPerTick());
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

    protected boolean internalExtractEnergy(int extract, boolean simulate) {
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
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY)
            return LazyOptional.of(() -> this).cast();
        return super.getCapability(cap, side);
    }
}