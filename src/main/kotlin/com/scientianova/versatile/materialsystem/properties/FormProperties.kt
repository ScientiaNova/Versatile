@file:JvmName("FormProperties")

package com.scientianova.versatile.materialsystem.properties

import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.materialsystem.materials.Material
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderType
import net.minecraft.fluid.FlowingFluid
import net.minecraft.item.Item
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.fml.DistExecutor
import java.util.function.Supplier

val matPredicate = FormProperty<(Material) -> Boolean>("versatile:mat_predicate".toResLoc()) { { false } }
val indexBlacklist = FormProperty("versatile:index_blacklist".toResLoc()) { emptyList<Int>() }
val bucketVolume = FormProperty("versatile:bucket_volume".toResLoc()) { 0 }
val registryName = FormInstanceProperty("versatile:registry_name".toResLoc()) {
    "versatile:${mat.name}_${form.name}".toResLoc()
}
val formColor = FormInstanceProperty("versatile:color".toResLoc()) { mat[color] }
val formDensityMultiplier = FormInstanceProperty("versatile:density_multiplier".toResLoc()) { mat[densityMultiplier] }
val temp = FormInstanceProperty("versatile:temperature".toResLoc()) { 300 }
val singleTextureSet = FormProperty("versatile:single_texture_set".toResLoc()) { false }
val burnTime = FormInstanceProperty("versatile:burn_time".toResLoc()) {
    (mat[baseBurnTime] * (form[bucketVolume] / 144f)).toInt()
}
val itemTag = FormProperty("versatile:item_tag".toResLoc()) { "forge:${name}s" }
val blockTag = FormProperty("versatile:block_tag".toResLoc()) { "forge:${name}s" }
val combinedItemTags = FormInstanceProperty("versatile:combined_item_tags".toResLoc()) {
    mat[associatedNames].map { "${form[itemTag]}/$it" }
}
val combinedBlocKTags = FormInstanceProperty("versatile:combined_block_tags".toResLoc()) {
    mat[associatedNames].map { "${form[blockTag]}/$it" }
}
val combinedFluidTags = FormInstanceProperty("versatile:combined_fluid_tags".toResLoc()) {
    mat[associatedNames].map { "forge:${form}_$it" }
}
val alreadyImplemented = FormInstanceProperty("versatile:already_implemented".toResLoc()) { false }
val renderType = FormInstanceProperty("versatile:render_type".toResLoc()) {
    DistExecutor.runForDist<RenderType?>({ Supplier { RenderType.getSolid() } }, { Supplier { null } })
}
val itemModel = FormInstanceProperty("versatile:item_model".toResLoc()) {
    json {
        "parent" to "versatile:item/materialitems/" + (if (form[singleTextureSet]) "" else "${mat[textureSet]}/") + form.name
    }
}
val blockstateJSON = FormInstanceProperty("versatile:blockstate_json".toResLoc()) {
    json {
        "variants" {
            "" {
                "model" to "versatile:block/materialblocks/" + (if (form[singleTextureSet]) "" else "${mat[textureSet]}/") + form.name
            }
        }
    }
}
val item = RegistryProperty<Item>("versatile:item".toResLoc()) { get(block)?.asItem() }
val block = RegistryProperty<Block>("versatile:block".toResLoc())
val fluidProperties = RegistryProperty<ForgeFlowingFluid.Properties>("versatile:fluid_properties".toResLoc())
val stillFluid = RegistryProperty<FlowingFluid>("versatile:still_fluid".toResLoc())
val flowingFluid = RegistryProperty<FlowingFluid>("versatile:flowing_fluid".toResLoc())