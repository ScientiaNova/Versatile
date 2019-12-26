package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID)
object VELISPEventSubscriber {
    @SubscribeEvent
    fun register(e: VersatileRegistryEvent) {

    }
}