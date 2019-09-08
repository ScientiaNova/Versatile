package com.emosewapixel.pixellib.materialsystem.types

import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialItem
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.google.gson.JsonObject
import net.minecraft.block.Block

//Block Types are Object Types used for generating Blocks
class BlockType(name: String, requirement: (Material) -> Boolean, val properties: Block.Properties) : ObjectType(name, requirement) {
    var blockstateFun: (IMaterialItem) -> JsonObject = {
        val states = JsonObject()
        val variants = JsonObject()
        val variant = JsonObject()
        variant.addProperty("model", "pixellib:block/materialblocks/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in typeTags) name else it.mat.textureType.toString() + "/" + name)
        variants.add("", variant)
        states.add("variants", variants)
        states
    }

    @JvmName("invokeBlock")
    operator fun invoke(builder: BlockType.() -> Unit) = builder(this)

    fun getBlockStateJson(item: IMaterialItem) = blockstateFun(item)
}