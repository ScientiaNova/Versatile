package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.reopening.ChangePagePacket
import com.emosewapixel.pixellib.machines.packets.reopening.ReopenGUIPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.network.NetworkDirection

open class TEPageProperty(override val te: BaseTileEntity) : ITEBoundProperty {
    override val id = ""

    override fun detectAndSendChanges(container: BaseContainer) {
        if (container.guiPage != container.te.guiLayout.currentPage)
            NetworkHandler.CHANNEL.sendTo(ReopenGUIPacket(te.pos, container.type), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
    }

    var page
        get() = te.guiLayout.currentPageId
        set(value) {
            if (te.world?.isRemote == true)
                NetworkHandler.CHANNEL.sendToServer(ChangePagePacket(te.pos, value))
            te.guiLayout.setCurrentPage(value)
        }

    override fun clone() = this

    override fun deserializeNBT(nbt: CompoundNBT?) {}

    override fun serializeNBT() = nbt { }
}