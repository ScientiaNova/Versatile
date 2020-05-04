package com.scientianova.versatile.fluids

import com.scientianova.versatile.materialsystem.lists.MaterialBlocks
import com.scientianova.versatile.materialsystem.lists.MaterialFluids
import com.scientianova.versatile.materialsystem.lists.MaterialItems
import com.scientianova.versatile.materialsystem.main.IMaterialObject
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.Form
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.fluid.Fluid
import net.minecraft.item.BucketItem
import net.minecraftforge.fluids.ForgeFlowingFluid

class MaterialFluidHolder(override val mat: Material, override val form: Form) : IMaterialObject, IFluidPairHolder {
    private val attributes: ForgeFlowingFluid.Properties = ForgeFlowingFluid.Properties(::still, ::flowing, form.fluidAttributes(mat))
            .block { MaterialBlocks[mat, form] as? FlowingFluidBlock }
            .bucket { MaterialItems[mat, form] as? BucketItem }

    override val still = Source(attributes)
    override val flowing = Flowing(attributes)

    init {
        MaterialFluids.addFluidPair(this)
    }

    inner class Source(properties: Properties) : ForgeFlowingFluid.Source(properties), IMaterialObject {
        override val mat = this@MaterialFluidHolder.mat
        override val form = this@MaterialFluidHolder.form

        init {
            registryName = form.registryName(mat)
        }

        override fun isEquivalentTo(fluid: Fluid) = mat.getFluidTags(form).any { fluid in it }
    }

    inner class Flowing(properties: Properties) : ForgeFlowingFluid.Flowing(properties), IMaterialObject {
        override val mat = this@MaterialFluidHolder.mat
        override val form = this@MaterialFluidHolder.form

        init {
            val reg = form.registryName(mat)
            setRegistryName("${reg.namespace}:flowing_${reg.path}")
        }

        override fun isEquivalentTo(fluid: Fluid) = mat.getFluidTags(form).any { fluid in it }
    }
}