package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.PixelLib
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel
import org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION

object NetworkHandler {
    val CHANNEL: SimpleChannel = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation(PixelLib.ModId, "main"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(::PROTOCOL_VERSION)
            .simpleChannel()

    private var packetId = 0
        get() {
            return field++
        }

    init {
        CHANNEL.messageBuilder(OpenGUIPacket::class.java, packetId)
                .encoder(OpenGUIPacket::encode)
                .decoder(OpenGUIPacket.Companion::decode)
                .consumer(OpenGUIPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(ReopenGUIPacket::class.java, packetId)
                .encoder(ReopenGUIPacket::encode)
                .decoder(ReopenGUIPacket.Companion::decode)
                .consumer(ReopenGUIPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(ChangePagePacket::class.java, packetId)
                .encoder(ChangePagePacket::encode)
                .decoder(ChangePagePacket.Companion::decode)
                .consumer(ChangePagePacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(UpdateBooleanPacket::class.java, packetId)
                .encoder(UpdateBooleanPacket::encode)
                .decoder(UpdateBooleanPacket.Companion::decode)
                .consumer(UpdateBooleanPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(UpdateIntPacket::class.java, packetId)
                .encoder(UpdateIntPacket::encode)
                .decoder(UpdateIntPacket.Companion::decode)
                .consumer(UpdateIntPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(UpdateSlotPacket::class.java, packetId)
                .encoder(UpdateSlotPacket::encode)
                .decoder(UpdateSlotPacket.Companion::decode)
                .consumer(UpdateSlotPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(UpdateTankPacket::class.java, packetId)
                .encoder(UpdateTankPacket::encode)
                .decoder(UpdateTankPacket.Companion::decode)
                .consumer(UpdateTankPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(UpdateHeldStackPacket::class.java, packetId)
                .encoder(UpdateHeldStackPacket::encode)
                .decoder(UpdateHeldStackPacket.Companion::decode)
                .consumer(UpdateHeldStackPacket.Companion::processPacket)
                .add()
    }
}