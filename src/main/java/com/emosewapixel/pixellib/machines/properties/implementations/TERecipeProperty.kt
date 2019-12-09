package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.ReopenGUIPacket
import com.emosewapixel.pixellib.machines.packets.UpdateRecipePacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.network.NetworkDirection

open class TERecipeProperty(recipeList: RecipeList, override val id: String, override val te: BaseTileEntity) : RecipeProperty(recipeList), ITEBoundProperty {
    override fun createDefault() = TERecipeProperty(recipeList, id, te)

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as? TERecipeProperty)?.value != value) {
            NetworkHandler.CHANNEL.sendTo(
                    UpdateRecipePacket(id, value?.name ?: ""),
                    (container.playerInv.player as ServerPlayerEntity).connection.networkManager,
                    NetworkDirection.PLAY_TO_CLIENT
            )
            NetworkHandler.CHANNEL.sendTo(ReopenGUIPacket(te.pos, container.type), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            (container.clientProperties[id] as? TERecipeProperty)?.value = value
        }
    }

    override fun setValue(new: Recipe?, causeUpdate: Boolean) {
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