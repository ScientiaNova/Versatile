package com.emosewapixel.pixellib.tiles

import com.emosewapixel.pixellib.blocks.ActivatableMachineBlock
import com.emosewapixel.pixellib.recipes.EnergyMachineRecipe
import com.emosewapixel.pixellib.recipes.EnergyRecipeList
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage

open class PoweredTE(type: TileEntityType<*>, recipeList: EnergyRecipeList, protected var maxPower: Int, protected var maxPowerIn: Int) : AbstractRecipeBasedTE<EnergyMachineRecipe>(type, recipeList), IEnergyStorage {
    protected var energy = 0

    override var currentRecipe: EnergyMachineRecipe = EnergyMachineRecipe.EMPTY

    public override val recipeByInput: EnergyMachineRecipe
        get() {
            val stacksStream = (0 until recipeList.maxInputs).map { recipeInventory.getStackInSlot(it) }

            if (stacksStream.any { it.isEmpty })
                return EnergyMachineRecipe.EMPTY

            val chosenRecipe = recipeList.recipes.first { it.isInputValid(stacksStream.toTypedArray()) }
                    ?: return EnergyMachineRecipe.EMPTY

            val recipeIndices = (0 until recipeList.maxInputs).map { chosenRecipe.getIndexOfInput(recipeInventory.getStackInSlot(it)) }

            return if (recipeIndices.contains(-1)) EnergyMachineRecipe.EMPTY else EnergyMachineRecipe(
                    (0 until recipeList.maxInputs).map { ItemStack(recipeInventory.getStackInSlot(it).item, chosenRecipe.getInputCount(recipeIndices[it])) }.toTypedArray(),
                    recipeIndices.map { chosenRecipe.getConsumeChance(it) }.toTypedArray(),
                    chosenRecipe.fluidInputs,
                    chosenRecipe.outputs,
                    chosenRecipe.outputChances,
                    chosenRecipe.fluidOutputs,
                    chosenRecipe.time,
                    chosenRecipe.energyPerTick)
        }

    override fun tick() {
        if (!world!!.isRemote) {
            val recipe = currentRecipe
            if (!recipe.isEmpty) {
                world!!.setBlockState(pos, world!!.getBlockState(pos).with(ActivatableMachineBlock.ACTIVE, true))
                if (progress > 0) {
                    world!!.setBlockState(pos, world!!.getBlockState(pos).with(ActivatableMachineBlock.ACTIVE, true))
                    if (internalExtractEnergy(recipe.energyPerTick, false)) {
                        progress--
                        if (progress == 0)
                            work()
                    }
                } else
                    startWorking()
            } else {
                world!!.setBlockState(pos, world!!.getBlockState(pos).with(ActivatableMachineBlock.ACTIVE, false))
                progress = 0
            }
            markDirty()
        }
    }

    override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
        val energyReceived = Math.min(maxEnergyStored - energy, Math.min(maxPowerIn, maxReceive))
        if (!simulate) {
            energy += energyReceived
            markDirty()
        }
        return energyReceived
    }

    override fun extractEnergy(maxExtract: Int, simulate: Boolean) = 0

    protected fun internalExtractEnergy(extract: Int, simulate: Boolean): Boolean {
        val energyExtract = energy.coerceAtMost(extract)
        if (!simulate) {
            if (energyExtract != extract)
                return false
            energy -= extract
            markDirty()
        }
        return energyExtract == extract
    }

    override fun getEnergyStored() = energy

    override fun getMaxEnergyStored() = maxPower

    override fun canExtract() = false

    override fun canReceive() = true

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap === CapabilityEnergy.ENERGY) LazyOptional.of { this }.cast() else super.getCapability(cap, side)
}