package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.fluid.FlowingFluid
import net.minecraft.fluid.Fluid

//This class contains functions for interacting with the global list of material blocks
object MaterialFluids {
    private val materialFluids = HashBasedTable.create<Material, ObjectType<*, *>, IFluidPairHolder>()

    @JvmStatic
    val all: Collection<FlowingFluid>
        get() = allPairs.map { it.still }

    @JvmStatic
    val allPairs: Collection<IFluidPairHolder>
        get() = materialFluids.values()

    @JvmStatic
    fun getPair(material: Material, type: ObjectType<*, *>): IFluidPairHolder? = materialFluids.get(material, type)

    @JvmStatic
    fun getPair(material: Material): MutableMap<ObjectType<*, *>, IFluidPairHolder>? = materialFluids.row(material)

    @JvmStatic
    fun getPair(type: ObjectType<*, *>): MutableMap<Material, IFluidPairHolder>? = materialFluids.column(type)

    @JvmStatic
    operator fun get(material: Material, type: ObjectType<*, *>): FlowingFluid? = getPair(material, type)?.still

    @JvmStatic
    operator fun get(material: Material): Map<ObjectType<*, *>, FlowingFluid>? = getPair(material)?.mapValues { (_, v) -> v.still }

    @JvmStatic
    operator fun get(type: ObjectType<*, *>): Map<Material, FlowingFluid>? = getPair(type)?.mapValues { (_, v) -> v.still }

    @JvmStatic
    fun contains(material: Material, type: ObjectType<*, *>) = get(material, type) != null

    @JvmStatic
    operator fun contains(fluid: Fluid) = fluid in (all + allPairs.map { it.flowing })

    @JvmStatic
    fun addFluidPair(mat: Material, type: ObjectType<*, *>, fluidPair: IFluidPairHolder) {
        materialFluids.put(mat, type, fluidPair)
        MaterialItems.addItem(mat, type, fluidPair.still.filledBucket)
        MaterialBlocks.addBlock(mat, type, fluidPair.still.defaultState.blockState.block)
    }

    @JvmStatic
    fun <O> addFluidPair(fluidPair: O) where O : IMaterialObject, O : IFluidPairHolder = materialFluids.put(fluidPair.mat, fluidPair.objType, fluidPair)

    @JvmStatic
    fun getFluidCell(fluid: FlowingFluid): Table.Cell<Material, ObjectType<*, *>, IFluidPairHolder>? = materialFluids.cellSet().firstOrNull { it.value?.still === fluid }

    @JvmStatic
    fun getFluidMaterial(fluid: FlowingFluid): Material? = if (fluid is IMaterialObject) fluid.mat else getFluidCell(fluid)?.rowKey

    @JvmStatic
    fun getFluidObjType(fluid: FlowingFluid): ObjectType<*, *>? = if (fluid is IMaterialObject) fluid.objType else getFluidCell(fluid)?.columnKey
}