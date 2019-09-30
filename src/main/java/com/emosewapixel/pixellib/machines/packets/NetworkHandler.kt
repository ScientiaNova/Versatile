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

    init {
        CHANNEL.messageBuilder(OpenGUIPacket::class.java, 0)
                .encoder(OpenGUIPacket::encode)
                .decoder(OpenGUIPacket.Companion::decode)
                .consumer(OpenGUIPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(ReopenGUIPacket::class.java, 1)
                .encoder(ReopenGUIPacket::encode)
                .decoder(ReopenGUIPacket.Companion::decode)
                .consumer(ReopenGUIPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(ChangePagePacket::class.java, 2)
                .encoder(ChangePagePacket::encode)
                .decoder(ChangePagePacket.Companion::decode)
                .consumer(ChangePagePacket.Companion::processPacket)
                .add()
    }
}