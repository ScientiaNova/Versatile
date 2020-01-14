package com.scientianovateam.versatile.materialsystem.main

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.materialsystem.addition.FormProperties
import com.scientianovateam.versatile.velisp.evaluated.*
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent

class Form(val properties: Map<Material, Map<String, IEvaluated>>) {
    val any = properties.values.first()

    fun isMaterialCompatible(mat: Material) = mat in properties

    val name = (any[FormProperties.NAME] as StringValue).toString()

    val indexBlackList: List<Int>
        get() = (any[FormProperties.INDEX_BLACKLIST] as ListValue).value.map { (it as NumberValue).value.toInt() }

    val bucketVolume: (Material) -> Int
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.BUCKET_VOLUME] as NumberValue).value.toInt()
        }

    val registryName: (Material) -> ResourceLocation
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.REGISTRY_NAME] as StringValue).value.toResLoc()
        }

    val itemTag: ResourceLocation
        get() = (any[FormProperties.ITEM_TAG] as StringValue).value.toResLoc()

    val blockTag: ResourceLocation
        get() = (any[FormProperties.BLOCK_TAG] as StringValue).value.toResLoc()

    val combinedItemTags: (Material) -> List<ResourceLocation>
        get() = { mat ->
            ((properties[mat] ?: error("$name doesn't generate for $mat"))
                    [FormProperties.COMBINED_ITEM_TAGS] as ListValue).value.map { (it as StringValue).value.toResLoc() }
        }

    val combinedBlockTags: (Material) -> List<ResourceLocation>
        get() = { mat ->
            ((properties[mat] ?: error("$name doesn't generate for $mat"))
                    [FormProperties.COMBINED_BLOCK_TAGS] as ListValue).value.map { (it as StringValue).value.toResLoc() }
        }

    val combinedFluidTags: (Material) -> List<ResourceLocation>
        get() = { mat ->
            ((properties[mat] ?: error("$name doesn't generate for $mat"))
                    [FormProperties.COMBINED_FLUID_TAGS] as ListValue).value.map { (it as StringValue).value.toResLoc() }
        }

    val color: (Material) -> Int
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.COLOR] as NumberValue).value.toInt()
        }

    val densityMultiplier: (Material) -> Float
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.DENSITY_MULTIPLIER] as NumberValue).value.toFloat()
        }

    val temperature: (Material) -> Int
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.TEMPERATURE] as NumberValue).value.toInt()
        }

    val singleTextureSet: (Material) -> Boolean
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.SINGLE_TEXTURE_SET] as BoolValue).value
        }

    val burnTime: (Material) -> Int
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.BURN_TIME] as NumberValue).value.toInt()
        }

    val itemModel: (Material) -> JsonObject
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.ITEM_MODEL] as StructValue).toJSON()
        }

    val blockstateJSON: (Material) -> JsonObject
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.BLOCKSTATE] as StructValue).toJSON()
        }

    val blockModels: (Material) -> Map<String, JsonObject>
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.BLOCK_MODELS] as StructValue).toJSON().entrySet().map { (key, value) -> key to (value as JsonObject) }
                    .toMap()
        }

    val itemJSON: (Material) -> JsonObject
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.ITEM] as StructValue).toJSON()
        }

    val blockJSON: (Material) -> JsonObject
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.BLOCK] as StructValue).toJSON()
        }

    val fluidJSON: (Material) -> JsonObject
        get() = {
            ((properties[it] ?: error("$name doesn't generate for $it"))
                    [FormProperties.FLUID] as StructValue).toJSON()
        }

    operator fun invoke(builder: Form.() -> Unit) = builder(this)

    fun localize(mat: Material) = TranslationTextComponent("form.$name", mat.localizedName)

    override fun toString() = name
}