package com.scientianovateam.versatile.data.generation

import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.invoke
import com.scientianovateam.versatile.velisp.it
import com.scientianovateam.versatile.velisp.matGet
import com.scientianovateam.versatile.velisp.types.*
import net.minecraft.data.DataGenerator

fun DataGenerator.addMatProperties() = matProperties {
    +Property(
            name = "texture_set",
            type = STRING,
            default = "regular"
    )
    +Property(
            name = "color",
            type = NUMBER,
            default = -1
    )
    +Property(
            name = "tier",
            type = NUMBER,
            default = 0
    )
    +Property(
            name = "hardness",
            type = NUMBER,
            default = 0
    )
    +Property(
            name = "resistance",
            type = NUMBER,
            default = 0
    )
    +Property(
            name = "harvest_level",
            type = NUMBER,
            default = 0
    )
    +Property(
            name = "tool_tier",
            type = OptionalType(STRING),
            default = null
    )
    +Property(
            name = "armor_tier",
            type = OptionalType(STRING),
            default = null
    )
    +Property(
            name = "element",
            type = STRING,
            default = "null"
    )
    +Property(
            name = "burn_time",
            type = NUMBER,
            default = -1
    )
    +Property(
            name = "compound_type",
            type = STRING,
            default = "chemical",
            valid = "or"("="(it, "compound"), "="(it, "chemical"))
    )
    +Property(
            name = "density_multiplier",
            type = NUMBER,
            default = 1
    )
    +Property(
            name = "processing_multiplier",
            type = NUMBER,
            default = 1
    )
    +Property(
            name = "unrefined_color",
            type = NUMBER,
            default = "color".matGet
    )
    +Property(
            name = "liquid_temperature",
            type = NUMBER,
            default = 0
    )
    +Property(
            name = "gas_temperature",
            type = NUMBER,
            default = 0
    )
    +Property(
            name = "liquid_name",
            type = STRING,
            default = "name".matGet
    )
    +Property(
            name = "gas_name",
            type = STRING,
            default = "name".matGet
    )
    +Property(
            name = "gas_color",
            type = NUMBER,
            default = "color".matGet
    )
    +Property(
            name = "refined_material",
            type = OptionalType(MATERIAL),
            default = null
    )
    +Property(
            name = "ph",
            type = NUMBER,
            default = 7,
            valid = "in_range"(it, 0, 14)
    )
    +Property(
            name = "alpha",
            type = NUMBER,
            default = 0xFF
    )
    +Property(
            name = "block_compaction",
            type = STRING,
            default = "3x3",
            valid = "or"("="(it, "3x3"), "="(it, "2x2"), "="(it, "none"))
    )
    +Property(
            name = "transition_amount",
            type = NUMBER,
            default = 2
    )
    +Property(
            name = "transition_material",
            type = OptionalType(STRING),
            default = null
    )
    +Property(
            name = "has_ore",
            type = BOOL,
            default = false
    )
    +Property(
            name = "simple_processing",
            type = BOOL,
            default = true
    )
    +Property(
            name = "rod_output_count",
            type = NUMBER,
            default = 1
    )
    +Property(
            name = "has_dust",
            type = BOOL,
            default = false
    )
    +Property(
            name = "has_ingot",
            type = BOOL,
            default = false
    )
    +Property(
            name = "has_gem",
            type = BOOL,
            default = false
    )
    +Property(
            name = "is_group",
            type = BOOL,
            default = false
    )
}