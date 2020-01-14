package com.scientianovateam.versatile.materialsystem.builders

import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.velisp.*
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

open class FormIntermediate(val name: String) {
    private val map = mutableMapOf<String, IUnresolved>()

    infix fun String.set(value: IUnresolved) = map.put(this, value)
    infix fun String.set(value: Any?) = set(value.expr())

    fun toJSON() = json {
        map.forEach {
            it.key to it.value.serialize()
        }
    }
}

fun form(name: String, builder: FormIntermediate.() -> Unit) = FormIntermediate(name).apply(builder)

fun itemForm(name: String, builder: FormIntermediate.() -> Unit) = FormIntermediate(name).apply {
    "item" set struct {
        "type" to "regular"
        "burn_time" to "burn_time".formGet
    }
    builder()
}

fun blockForm(name: String, builder: FormIntermediate.() -> Unit) = FormIntermediate(name).apply {
    "item" set struct {
        "type" to "block_item"
        "burn_time" to "burn_time".formGet
    }
    "block" set struct {
        "type" to "regular"
        "material" to "iron"
        "hardness" to "hardness".matGet
        "resistance" to "resistance".matGet
        "harvest_tier" to "harvest_tier".matGet
    }
    "item_model" set struct {
        "parent" to "concat"("versatile:block/", "registry_name".formGet)
    }
    builder()
}

fun fluidForm(name: String, builder: FormIntermediate.() -> Unit) = FormIntermediate(name).apply {
    "item" set struct {
        "type" to "bucket"
        "fluid" to "registry_name".formGet
        "burn_time" to "burn_time".formGet
    }
    "block" set struct {
        "type" to "fluid"
        "fluid" to "registry_name".formGet
        "material" to "water"
    }
    //TODO fluids
    "single_texture_set" set true
    "index_blacklist" set "list"(0)
    "bucket_volume" set 1000
    "item_model" set struct {
        "parent" to "item/generated"
        "textures" {
            "layer0" to "versatile:item/fluid_bucket_base"
            "layer1" to "versatile:item/fluid_bucket_overlay"
        }
    }
    "block_models" set struct {
        "" {
            "textures" {
                "particle" to "minecraft:block/water_still"
            }
        }
    }
    builder()
}