package com.scientianova.versatile.machines.properties.implementations

import com.scientianova.versatile.common.extensions.nbt
import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.gui.BaseContainer
import com.scientianova.versatile.machines.packets.NetworkHandler
import com.scientianova.versatile.machines.packets.reopening.ChangePagePacket
import com.scientianova.versatile.machines.packets.reopening.ReopenGUIPacket
import com.scientianova.versatile.machines.properties.ITEBoundProperty
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