package com.scientianova.versatile.fluids

import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.ObjectType
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.fluid.Fluid
import net.minecraft.item.BucketItem
import net.minecraftforge.fluids.ForgeFlowingFluid

class MaterialFluidHolder(override val mat: Material, override val objType: ObjectType) : IMaterialObject, IFluidPairHolder {
    private val attributes: ForgeFlowingFluid.Properties = ForgeFlowingFluid.Properties(::still, ::flowing, objType.fluidAttributes(mat))
            .block { MaterialBlocks[mat, objType] as? FlowingFluidBlock }
            .bucket { MaterialItems[mat, objType] as? BucketItem }

    override val still = Source(attributes)
    override val flowing = Flowing(attributes)

    init {
        MaterialFluids.addFluidPair(this)
    }

    inner class Source(properties: Properties) : ForgeFlowingFluid.Source(properties), IMaterialObject {
        override val mat = this@MaterialFluidHolder.mat
        override val objType = this@MaterialFluidHolder.objType

        init {
            registryName = objType.registryName(mat)
        }

        override fun isEquivalentTo(fluid: Fluid) = mat.getFluidTags(objType).any { fluid in it }
    }

    inner class Flowing(properties: Properties) : ForgeFlowingFluid.Flowing(properties), IMaterialObject {
        override val mat = this@MaterialFluidHolder.mat
        override val objType = this@MaterialFluidHolder.objType

        init {
            val reg = objType.registryName(mat)
            setRegistryName("${reg.namespace}:flowing_${reg.path}")
        }

        override fun isEquivalentTo(fluid: Fluid) = mat.getFluidTags(objType).any { fluid in it }
    }
}