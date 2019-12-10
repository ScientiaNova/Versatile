package com.emosewapixel.pixellib.machines.packets.reopening

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateRecipePacket(val property: String, val name: String) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeString(property)
        buffer.writeString(name)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = UpdateRecipePacket(buffer.readString(), buffer.readString())

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun processPacket(packet: UpdateRecipePacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val serverSideContainer = context.get().sender?.openContainer as? BaseContainer
                val container = DistExecutor.runForDist(
                        {
                            Supplier {
                                serverSideContainer ?: Minecraft.getInstance().player.openContainer as? BaseContainer
                            }
                        },
                        { Supplier { serverSideContainer } }
                )
                (container?.te?.teProperties?.get(packet.property) as? RecipeProperty)?.let {
                    it.setValue(it.recipeList.getRecipes()[packet.name], false)
                }
                (container?.clientProperties?.get(packet.property) as? RecipeProperty)?.let {
                    it.setValue(it.recipeList.getRecipes()[packet.name], false)
                }
                container?.te?.guiLayout?.let { layout -> layout.current = layout[0]() }
                context.get().sender?.let { player ->
                    if (container != null)
                        NetworkHandler.CHANNEL.sendTo(ReopenGUIPacket(container.te.pos, container.type), player.connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
                }
            }
            context.get().packetHandled = true
        }
    }
}