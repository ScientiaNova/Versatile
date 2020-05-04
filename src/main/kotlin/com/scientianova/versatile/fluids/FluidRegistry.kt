package com.scientianova.versatile.fluids

import com.scientianova.versatile.materialsystem.lists.Forms
import com.scientianova.versatile.materialsystem.lists.MaterialFluids
import com.scientianova.versatile.materialsystem.lists.Materials
import net.minecraft.fluid.Fluid
import net.minecraftforge.event.RegistryEvent

fun registerFluids(e: RegistryEvent.Register<Fluid>) {
    MaterialFluids.additionSuppliers.cellSet()
            .forEach { MaterialFluids.addFluidPair(it.rowKey!!, it.columnKey!!, it.value!!()) }
    Materials.all.forEach { mat ->
        Forms.all.forEach inner@{ form ->
            form[mat]?.let {
                val pair = it.fluidPair ?: return@inner
                e.registry.register(pair.still)
                e.registry.register(pair.flowing)
            }
        }
    }
}