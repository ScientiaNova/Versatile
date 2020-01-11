package com.scientianovateam.versatile.data.generation

import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.*
import com.scientianovateam.versatile.velisp.types.*
import net.minecraft.data.DataGenerator

fun DataGenerator.addFormProperties() = formProperties {
    +Property(
            name = "bucket_volume",
            type = NUMBER,
            default = 0,
            valid = "is_positive"(it)
    )
    +Property(
            name = "registry_name",
            type = STRING,
            default = "concat"("name".matGet, "_", "name".formGet),
            valid = "valid_resource_location"(it)
    )
    +Property(
            name = "color",
            type = NUMBER,
            default = 0xFFFFFF,
            valid = "in_range"(it, 0x000000, 0xFFFFFF)
    )
    +Property(
            name = "density_multiplier",
            type = NUMBER,
            default = 1,
            valid = "is_positive"(it)
    )
    +Property(
            name = "temperature",
            type = NUMBER,
            default = "if"("is_positive"("fluid_temperature".matGet), "fluid_temperature".matGet, 0)
    )
    +Property(
            name = "single_texture_set",
            type = BOOL,
            default = false
    )
    +Property(
            name = "burn_time",
            type = NUMBER,
            default = "*"("standard_burn_time".matGet, "/"("bucket_volume".matGet, 144))
    )
    +Property(
            name = "item",
            type = OptionalType(STRUCT),
            default = null
    )
    +Property(
            name = "block",
            type = OptionalType(STRUCT),
            default = null
    )
    +Property(
            name = "fluid",
            type = OptionalType(STRUCT),
            default = null
    )
    +Property(
            name = "item_model",
            type = STRUCT,
            default = struct {
                "parent" to "item/generated"
                "textures" {
                    "layer0" to "if"("single_texture_set".formGet,
                            "concat"("versatile:item/", "name".formGet),
                            "concat"("versatile:item/", "texture_set".matGet, "/", "name".formGet)
                    )
                }
            }
    )
    +Property(
            name = "blockstate",
            type = STRUCT,
            default = struct {
                "variants" {
                    "" {
                        "model" to "concat"("versatile:block/", "registry_name".formGet)
                    }
                }
            }
    )
    +Property(
            name = "block_models",
            type = STRUCT,
            default = struct {
                "" {
                    "parent" to "versatile:block/colored_cube_all"
                    "textures" {
                        "all" to "if"("single_texture_set".formGet,
                                "concat"("versatile:block/", "name".formGet),
                                "concat"("versatile:block/", "texture_set".matGet, "/", "name".formGet)
                        )
                    }
                }
            }
    )
}