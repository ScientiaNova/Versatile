package com.EmosewaPixel.pixellib.tiles

import com.EmosewaPixel.pixellib.blocks.FuelBasedMachineBlock
import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe
import com.EmosewaPixel.pixellib.recipes.SimpleRecipeList
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

class FuelBasedTE(type: TileEntityType<*>, list: SimpleRecipeList) : RecipeBasedTE(type, list) {
    var burnTime = 0
    var maxBurnTime = 0

    var fuel_input: ItemStackHandler

    override var currentRecipe: SimpleMachineRecipe = SimpleMachineRecipe.EMPTY

    init {
        fuel_input = object : ItemStackHandler(1) {
            override fun isItemValid(slot: Int, stack: ItemStack) = getItemBurnTime(stack) > 0

            override fun onContentsChanged(slot: Int) {
                this@FuelBasedTE.markDirty()
            }
        }

        combinedHandler = CombinedInvWrapper(recipeInventory, fuel_input)
    }

    override fun tick() {
        if (!world!!.isRemote) {
            if (burnTime > 0) {
                burnTime--
                world!!.setBlockState(pos, world!!.getBlockState(pos).with(FuelBasedMachineBlock.LIT, true))
                super.tick()
            } else {
                if (!fuel_input.getStackInSlot(0).isEmpty)
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
            maxBurnTime = getItemBurnTime(fuel_input.getStackInSlot(0))
            burnTime = maxBurnTime
            if (burnTime > 0) {
                if (fuel_input.getStackInSlot(0).hasContainerItem())
                    fuel_input.setStackInSlot(0, fuel_input.getStackInSlot(0).containerItem)
                else
                    fuel_input.extractItem(0, 1, false)
            }
        } else
            world!!.setBlockState(pos, world!!.getBlockState(pos).with(FuelBasedMachineBlock.LIT, false))
    }

    override fun read(compound: CompoundNBT) {
        super.read(compound)
        if (compound.contains("FuelItems"))
            fuel_input.deserializeNBT((compound.get("FuelItems") as CompoundNBT?)!!)
        burnTime = compound.getInt("BurnTime")
        maxBurnTime = compound.getInt("MaxBurnTime")
    }

    override fun write(compound: CompoundNBT): CompoundNBT {
        super.write(compound)
        compound.put("FuelItems", fuel_input.serializeNBT())
        compound.putInt("BurnTime", burnTime)
        compound.putInt("MaxBurnTime", maxBurnTime)
        return compound
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (cap === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (side == Direction.EAST || side == Direction.WEST || side == Direction.NORTH || side == Direction.SOUTH)
                return LazyOptional.of { fuel_input }.cast()
        return super.getCapability(cap, side)
    }
}