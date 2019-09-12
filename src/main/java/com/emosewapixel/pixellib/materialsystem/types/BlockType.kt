package com.emosewapixel.pixellib.materialsystem.types

import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.google.gson.JsonObject
import net.minecraft.block.Block

//Block Types are Object Types used for generating Blocks
class BlockType(name: String, requirement: (Material) -> Boolean, val properties: Block.Properties) : ObjectType(name, requirement) {
    var blockstateFun: (IMaterialObject) -> JsonObject = {
        json {
            "variants" {
                "" {
                    "model" to "pixellib:block/materialblocks/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in typeTags) name else "${it.mat.textureType}/$name"
                }
            }
        }
    }

    @JvmName("invokeBlock")
    operator fun invoke(builder: BlockType.() -> Unit) = builder(this)

    fun getBlockStateJson(item: IMaterialObject) = blockstateFun(item)
}