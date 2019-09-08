package com.emosewapixel.pixellib.materialsystem.types

import com.emosewapixel.pixellib.materialsystem.lists.TextureTypes

//Texture Types determine which sets of textures Material use for Object Types
open class TextureType(private val name: String) {
    init {
        TextureTypes.add(this)
    }

    override fun toString() = name
}