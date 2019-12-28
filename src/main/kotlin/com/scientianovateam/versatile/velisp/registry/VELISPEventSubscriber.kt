package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.velisp.*
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID)
object VELISPEventSubscriber {
    @SubscribeEvent
    fun register(e: VersatileRegistryEvent) {
        VELISP_TYPES["versatile:any".toResLoc()] = { AnyType }
        VELISP_TYPES["versatile:number".toResLoc()] = { NumberType }
        VELISP_TYPES["versatile:bool".toResLoc()] = { BoolType }
        VELISP_TYPES["versatile:string".toResLoc()] = { StringType }
        VELISP_TYPES["versatile:list".toResLoc()] = {
            val (name, info) = VELISPType.separateNameAndInfo(it)
            ListType(VELISP_TYPES[name.toResLocV()]?.invoke(info)
                    ?: error("Didn't properly specify type for list type"))
        }
        VELISP_TYPES["versatile:optional".toResLoc()] = {
            val (name, info) = VELISPType.separateNameAndInfo(it)
            OptionalType(VELISP_TYPES[name.toResLocV()]?.invoke(info)
                    ?: error("Didn't properly specify type for optional type"))
        }
        VELISP_TYPES["versatile:function".toResLoc()] = {
            FunctionType(it.toIntOrNull() ?: error("Didn't specify amount of inputs for function type"))
        }
    }
}