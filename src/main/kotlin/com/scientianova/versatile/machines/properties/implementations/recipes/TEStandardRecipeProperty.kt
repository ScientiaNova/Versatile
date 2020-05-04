package com.scientianova.versatile.machines.properties.implementations.recipes

import com.scientianovateam.versatile.common.extensions.nbt
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.BaseContainer
import com.scientianovateam.versatile.machines.packets.NetworkHandler
import com.scientianovateam.versatile.machines.packets.reopening.UpdateRecipePacket
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.recipes.Recipe
import com.scientianovateam.versatile.machines.recipes.RecipeList
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.network.NetworkDirection

open class TEStandardRecipeProperty(recipeList: RecipeList, value: Recipe?, override val id: String, override val te: BaseTileEntity) : RecipeProperty(recipeList, value), ITEBoundProperty {
    override fun clone() = TEStandardRecipeProperty(recipeList, value, id, te)

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as? TEStandardRecipeProperty)?.value != value) {
            value?.let {
                NetworkHandler.CHANNEL.sendTo(
                        UpdateRecipePacket(id, it.name),
                        (container.playerInv.player as ServerPlayerEntity).connection.networkManager,
                        NetworkDirection.PLAY_TO_CLIENT
                )
            }
            (container.clientProperties[id] as? TEStandardRecipeProperty)?.value = value
        }
    }

    override fun setValue(new: Recipe?, causeUpdate: Boolean) {
        value = new
        if (causeUpdate && te.world?.isRemote == true)
            NetworkHandler.CHANNEL.sendToServer(UpdateRecipePacket(id, value?.name ?: ""))
        te.markDirty()
    }

    override fun serializeNBT() = nbt {
        value?.name?.let { id to it }
    }

    override fun deserializeNBT(nbt: CompoundNBT?) {
        if (nbt?.contains(id) == true)
            value = recipeList.getRecipes()[nbt.getString(id)]
    }
}