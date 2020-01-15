package com.scientianovateam.versatile.fluids

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.materialsystem.lists.FORMS
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import net.minecraft.fluid.Fluid
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object FluidEventBusSubscriber {
    @SubscribeEvent
    fun registerFluids(e: RegistryEvent.Register<Fluid>) {
        MaterialFluids.additionSuppliers.cellSet().forEach {
            MaterialFluids.addFluidPair(it.rowKey!!, it.columnKey!!, it.value!!())
        }
        FORMS.forEach { form ->
            form.properties.keys.forEach material@{ mat ->
                if (MaterialFluids.contains(mat, form)) return@material
                //TODO fluids
            }
        }
    }
}