package com.emosewapixel.pixellib.tiles.containers

import com.emosewapixel.pixellib.tiles.FuelBasedTE
import com.emosewapixel.pixellib.tiles.RecipeBasedTE
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.ContainerType
import net.minecraftforge.items.SlotItemHandler

open class FuelBasedMachineContainer(playerInventory: PlayerInventory, te: FuelBasedTE, type: ContainerType<*>, id: Int) : RecipeBasedMachineContainer<RecipeBasedTE>(playerInventory, te, type, id) {
    override fun addMachineSlots() {
        repeat(te.recipeList.maxInputs) { this.addSlot(SlotItemHandler(itemHandler, it, if (te.recipeList.maxInputs == 1) 56 else 38 + it * 18, 17)) }
        this.addSlot(SlotItemHandler(itemHandler, te.recipeList.maxInputs, 56 - (te.recipeList.maxInputs - 1) * 9, 53))

        repeat(te.recipeList.maxOutputs) { this.addSlot(SlotItemHandler(itemHandler, te.slotCount - it - 1, 116, if (te.recipeList.maxOutputs == 1) 35 else 48 - it * 22)) }
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
        listeners.forEach { listener ->
            listener.sendWindowProperty(this, 2, (te as FuelBasedTE).burnTime)
            listener.sendWindowProperty(this, 3, (te as FuelBasedTE).maxBurnTime)
        }
    }

    override fun updateProgressBar(id: Int, data: Int) {
        super.updateProgressBar(id, data)
        when (id) {
            2 -> (te as FuelBasedTE).burnTime = data
            3 -> if (!te.currentRecipe.isEmpty)
                (te as FuelBasedTE).maxBurnTime = data
        }
    }
}