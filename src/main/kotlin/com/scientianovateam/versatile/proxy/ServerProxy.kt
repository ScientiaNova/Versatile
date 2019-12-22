package com.scientianovateam.versatile.proxy

import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent

object ServerProxy : IModProxy {
    override fun init() {}

    override fun enque(e: InterModEnqueueEvent) {}

    override fun process(e: InterModProcessEvent) {}
}