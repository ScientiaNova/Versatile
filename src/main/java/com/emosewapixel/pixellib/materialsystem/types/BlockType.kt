package com.emosewapixel.pixellib.materialsystem.types

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.blocks.MaterialBlock
import com.emosewapixel.pixellib.blocks.MaterialBlockItem
import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.materials.utility.ct.MaterialRequirement
import com.google.gson.JsonObject
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.text.TranslationTextComponent
import org.openzen.zencode.java.ZenCodeType

//Block Types are Object Types used for generating Blocks
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.types.BlockType")
class BlockType @JvmOverloads @ZenCodeType.Constructor constructor(name: String, requirement: MaterialRequirement, val properties: Block.Properties, constructor: (Material, BlockType) -> Block = ::MaterialBlock, val itemConstructor: (Material, BlockType) -> Item = ::MaterialBlockItem) : ObjectType<Block, BlockType>(name, requirement, constructor) {
    var buildBlockStateJson: (IMaterialObject) -> JsonObject = {
        json {
            "variants" {
                "" {
                    "model" to "pixellib:block/materialblocks/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in typeTags) name else "${it.mat.textureType}/$name"
                }
            }
        }
    }

    override fun localize(mat: Material) = TranslationTextComponent("blocktype.$name", mat.localizedName)
}