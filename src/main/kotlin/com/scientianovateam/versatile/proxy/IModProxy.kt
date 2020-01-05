package com.scientianovateam.versatile.proxy

import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent

//This is an interface used for making Mod Proxies
interface IModProxy {
    fun init() {}

    fun enqueue(e: InterModEnqueueEvent) {}

    fun process(e: InterModProcessEvent) {}
}