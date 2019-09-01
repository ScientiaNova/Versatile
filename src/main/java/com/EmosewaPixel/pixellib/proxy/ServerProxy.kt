package com.EmosewaPixel.pixellib.proxy

import net.alexwells.kottle.KotlinEventBusSubscriber
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent

@KotlinEventBusSubscriber(Dist.DEDICATED_SERVER)
object ServerProxy : IModProxy {
    override fun init() {

    }

    override fun enque(e: InterModEnqueueEvent) {

    }

    override fun process(e: InterModProcessEvent) {

    }
}