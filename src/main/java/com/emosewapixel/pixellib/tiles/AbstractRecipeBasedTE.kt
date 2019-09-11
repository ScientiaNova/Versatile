package com.emosewapixel.pixellib.tiles

import com.emosewapixel.pixellib.capabilities.ImprovedItemStackHandler
import com.emosewapixel.pixellib.extensions.plusAssign
import com.emosewapixel.pixellib.miscutils.getArrayFromRange
import com.emosewapixel.pixellib.miscutils.spawnItemInWorld
import com.emosewapixel.pixellib.recipes.AbstractRecipeList
import com.emosewapixel.pixellib.recipes.SimpleMachineRecipe
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.wrapper.CombinedInvWrapper
import java.util.*

abstract class AbstractRecipeBasedTE<T : SimpleMachineRecipe>(type: TileEntityType<*>, val recipeList: AbstractRecipeList<T, *>) : ProgressiveTE(type) {
    abstract var currentRecipe: T

    val slotCount: Int
        get() = combinedHandler.slots

    protected abstract val recipeByInput: T

    protected var recipeInventory = object : ImprovedItemStackHandler(recipeList.maxRecipeSlots, getArrayFromRange(0, recipeList.maxInputs), getArrayFromRange(recipeList.maxInputs, recipeList.maxRecipeSlots)) {
        override fun isItemValid(slot: Int, stack: ItemStack) = recipeList.recipes.any { it.itemBelongsInRecipe(stack) }

        override fun onContentsChanged(slot: Int) {
            currentRecipe = recipeByInput
            markDirty()
        }
    }

    protected var combinedHandler = CombinedInvWrapper(recipeInventory)

    override fun tick() {
        if (!world!!.isRemote)
            if (progress > 0) {
                if (!currentRecipe.isEmpty) {
                    progress--
                    if (progress == 0)
                        work()
                } else
                    progress = 0
            } else
                startWorking()
        markDirty()
    }

    protected fun startWorking() {
        if (!currentRecipe.isEmpty)
            if (canOutput(currentRecipe))
                progress = currentRecipe.time - 1
    }

    protected fun work() {
        val lastRecipe = currentRecipe
        output(lastRecipe)
        val rand = Random()
        repeat(recipeList.maxInputs) { i ->
            if (lastRecipe.getOutputChance(i) >= rand.nextFloat())
                recipeInventory.extractItem(i, lastRecipe.getInputCount(i), false)
        }
    }

    override fun read(compound: CompoundNBT) {
        super.read(compound)
        if (compound.contains("RecipeInventory"))
            recipeInventory.deserializeNBT((compound.get("RecipeInventory") as CompoundNBT?)!!)
        currentRecipe = recipeByInput
    }

    override fun write(compound: CompoundNBT): CompoundNBT {
        super.write(compound) += "RecipeInventory" to recipeInventory
        return compound
    }

    fun canInteractWith(playerIn: PlayerEntity) = playerIn.getDistanceSq(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5) <= 64.0

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = if (cap === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) LazyOptional.of { recipeInventory }.cast() else LazyOptional.empty()

    protected fun canOutput(recipe: SimpleMachineRecipe) = (recipe.outputStacks.indices).all { recipeInventory.insertItem(recipeList.maxInputs + it, recipe.getOutput(it)!!.copy(), true).isEmpty }

    protected fun output(recipe: SimpleMachineRecipe) {
        val rand = Random()
        recipe.outputStacks.indices.forEach { i ->
            if (recipe.getOutputChance(i) >= rand.nextFloat())
                recipeInventory.insertItem(recipeList.maxInputs + i, recipe.getOutput(i)!!.copy(), true)
        }
    }

    fun dropInventory() = repeat(combinedHandler.slots) { i -> spawnItemInWorld(world!!, pos, combinedHandler.getStackInSlot(i)) }
}