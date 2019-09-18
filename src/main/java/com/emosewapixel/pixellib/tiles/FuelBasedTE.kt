package com.emosewapixel.pixellib.tiles

import com.emosewapixel.pixellib.blocks.FuelBasedMachineBlock
import com.emosewapixel.pixellib.extensions.isHorizontal
import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.extensions.plusAssign
import com.emosewapixel.pixellib.recipes.SimpleMachineRecipe
import com.emosewapixel.pixellib.recipes.SimpleRecipeList
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.FurnaceTileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.wrapper.CombinedInvWrapper

open class FuelBasedTE(type: TileEntityType<*>, list: SimpleRecipeList) : RecipeBasedTE(type, list) {
    var burnTime = 0
    var maxBurnTime = 0

    var fuelInput: ItemStackHandler

    override var currentRecipe: SimpleMachineRecipe = SimpleMachineRecipe.EMPTY

    init {
        fuelInput = object : ItemStackHandler(1) {
            override fun isItemValid(slot: Int, stack: ItemStack) = getItemBurnTime(stack) > 0

            override fun onContentsChanged(slot: Int) {
                this@FuelBasedTE.markDirty()
            }
        }

        combinedHandler = CombinedInvWrapper(recipeInventory, fuelInput)
    }

    override fun tick() {
        if (!world!!.isRemote) {
            if (burnTime > 0) {
                burnTime--
                world!!.setBlockState(pos, world!!.getBlockState(pos).with(FuelBasedMachineBlock.LIT, true))
                super.tick()
            } else {
                if (!fuelInput.getStackInSlot(0).isEmpty)
                    consumeFuel()
                else
                    world!!.setBlockState(pos, world!!.getBlockState(pos).with(FuelBasedMachineBlock.LIT, false))
            }
            markDirty()
        }
    }

    protected fun getItemBurnTime(stack: ItemStack): Int {
        if (stack.isEmpty)
            return 0
        val rec = stack.burnTime
        return ForgeEventFactory.getItemBurnTime(stack, if (rec == -1) FurnaceTileEntity.getBurnTimes().getOrDefault(stack.item, 0) else rec)
    }

    protected fun consumeFuel() {
        if (!currentRecipe.isEmpty && canOutput(currentRecipe)) {
            maxBurnTime = getItemBurnTime(fuelInput.getStackInSlot(0))
            burnTime = maxBurnTime
            if (burnTime > 0) {
                if (fuelInput.getStackInSlot(0).hasContainerItem())
                    fuelInput.setStackInSlot(0, fuelInput.getStackInSlot(0).containerItem)
                else
                    fuelInput.extractItem(0, 1, false)
            }
        } else
            world!!.setBlockState(pos, world!!.getBlockState(pos).with(FuelBasedMachineBlock.LIT, false))
    }

    override fun read(compound: CompoundNBT) {
        super.read(compound)
        if ("FuelItems" in compound)
            fuelInput.deserializeNBT((compound["FuelItems"] as CompoundNBT?)!!)
        burnTime = compound.getInt("BurnTime")
        maxBurnTime = compound.getInt("MaxBurnTime")
    }

    override fun write(compound: CompoundNBT): CompoundNBT {
        super.write(compound) += nbt {
            "FuelItems" to fuelInput
            "BurnTime" to burnTime
            "MaxBurnTime" to maxBurnTime
        }
        return compound
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side.isHorizontal)
                LazyOptional.of { fuelInput }.cast()
            else super.getCapability(cap, side)
}