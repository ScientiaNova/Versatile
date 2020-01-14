package com.scientianovateam.versatile.data.generation

import com.scientianovateam.versatile.materialsystem.builders.blockForm
import com.scientianovateam.versatile.materialsystem.builders.fluidForm
import com.scientianovateam.versatile.materialsystem.builders.itemForm
import com.scientianovateam.versatile.velisp.formGet
import com.scientianovateam.versatile.velisp.invoke
import com.scientianovateam.versatile.velisp.matGet
import com.scientianovateam.versatile.velisp.struct
import net.minecraft.data.DataGenerator

fun DataGenerator.addForms() = forms {
    +itemForm("dust") {
        "generate" set "has_dust".matGet
        "bucket_volume" set 144
    }
    +itemForm("gem") {
        "generate" set "has_gem".matGet
        "bucket_volume" set 144
    }
    +itemForm("ingot") {
        "generate" set "has_ingot".matGet
        "bucket_volume" set 144
    }
    +itemForm("nugget") {
        "generate" set "and"("has_ingot".matGet, "malleable".matGet)
        "bucket_volume" set 16
    }
    +blockForm("storage_block") {
        "generate" set "!="("block_compaction".matGet, "none")
        "registry_name" set "concat"("versatile:", "name".matGet, "_block")
        "burn_time" set "*"("burn_time".matGet, 10)
        "bucket_volume" set 1296
    }
    +blockForm("ore") {
        "generate" set "has_ore".matGet
        "item" set struct {
            "type" to "block_item"
            "block" to "registry_name".formGet
        }
        "block" set struct {
            "type" to "regular"
            "material" to "rock"
            "sound_type" to "stone"
            "hardness" to "hardness".matGet
            "resistance" to "resistance".matGet
            "harvest_tier" to "harvest_tier".matGet
        }
        "color" set "unrefined_color".matGet
        "index_blackList" set "list"(1)
        "bucket_volume" set 144
    }
    +fluidForm("liquid") {
        "generate" set ">"("liquid_temperature".matGet, 0)
        "registry_name" set "concat"("versatile:", "name".matGet)
        "item_tag" set "forge:buckets"
        "combined_item_tags" set "list"("concat"("forge:buckets/", "liquid_name".matGet))
        "combined_block_tags" set "list"("concat"("forge:", "liquid_name".matGet))
        "combined_fluid_tags" set "list"("concat"("forge", "liquid_name".matGet))
        "temperature" set "liquid_temperature".matGet
        //TODO fluids
    }
    +fluidForm("gas") {
        "generate" set ">"("gas_temperature".matGet, 0)
        "registry_name" set "concat"("versatile:", "name".matGet)
        "item_tag" set "forge:buckets"
        "combined_item_tags" set "list"("concat"("forge:buckets/", "gas_name".matGet))
        "combined_block_tags" set "list"("concat"("forge:", "gas_name".matGet))
        "combined_fluid_tags" set "list"("concat"("forge", "gas_name".matGet))
        "temperature" set "gas_temperature".matGet
        //TODO fluids
    }
}