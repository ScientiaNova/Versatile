package com.scientianova.versatile.machines.properties.implementations.recipes

import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.gui.BaseContainer
import com.scientianova.versatile.machines.packets.NetworkHandler
import com.scientianova.versatile.machines.packets.reopening.ReopenGUIPacket
import com.scientianova.versatile.machines.packets.reopening.UpdateRecipePacket
import com.scientianova.versatile.machines.properties.ITEBoundProperty
import com.scientianova.versatile.machines.recipes.Recipe
import com.scientianova.versatile.machines.recipes.RecipeList
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.fml.network.NetworkDirection

open class TEAutomationRecipeProperty(recipeList: RecipeList, value: Recipe?,  override val id: String, override val te: BaseTileEntity) : TEStandardRecipeProperty(recipeList, value, id, te), ITEBoundProperty {
    override fun clone() = TEAutomationRecipeProperty(recipeList, value, id, te)

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as? TEAutomationRecipeProperty)?.value != value) {
            NetworkHandler.CHANNEL.sendTo(
                    UpdateRecipePacket(id, value?.name
                            ?: ""),
                    (container.playerInv.player as ServerPlayerEntity).connection.networkManager,
                    NetworkDirection.PLAY_TO_CLIENT
            )
            NetworkHandler.CHANNEL.sendTo(ReopenGUIPacket(te.pos, container.type), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            (container.clientProperties[id] as? TEAutomationRecipeProperty)?.value = value
        }
    }

    override fun setValue(new: Recipe?, causeUpdate: Boolean) {
        super.setValue(new, causeUpdate)
        te.guiLayout.setCurrentPage(te.guiLayout.currentPageId)
        te.clear()
    }
}