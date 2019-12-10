package com.emosewapixel.pixellib.machines.properties.implementations.recipes

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.reopening.UpdateRecipePacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.network.NetworkDirection

open class TEStandardRecipeProperty(recipeList: RecipeList, override val id: String, override val te: BaseTileEntity) : RecipeProperty(recipeList), ITEBoundProperty {
    override fun createDefault() = TEStandardRecipeProperty(recipeList, id, te)

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
        if (causeUpdate && !FMLEnvironment.dist.isDedicatedServer)
            NetworkHandler.CHANNEL.sendToServer(UpdateRecipePacket(id, value?.name
                    ?: ""))
        value = new
    }

    override fun serializeNBT() = nbt {
        value?.name?.let { id to it }
    }

    override fun deserializeNBT(nbt: CompoundNBT?) {
        if (nbt?.contains(id) == true)
            value = recipeList.getRecipes()[nbt.getString(id)]
    }
}