package com.scientianovateam.versatile

import com.scientianovateam.versatile.data.generation.materials
import com.scientianovateam.versatile.data.generation.providers
import com.scientianovateam.versatile.materialsystem.builders.color
import com.scientianovateam.versatile.materialsystem.builders.composition
import com.scientianovateam.versatile.materialsystem.builders.ingotMaterial
import com.scientianovateam.versatile.materialsystem.builders.tier
import com.scientianovateam.versatile.materialsystem.lists.MATERIALS
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

@Mod.EventBusSubscriber(modid = VersatileTest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object TestDataRegistry {
    @SubscribeEvent
    fun gatherData(e: GatherDataEvent) = e.generator.providers {
        materials {
            ingotMaterial("angmallen") {
                tier = 2
                color = 0xe0d78a
                composition = listOf(MATERIALS["iron"]!!.toStack(), MATERIALS["gold"]!!.toStack())
            }
        }
    }
}