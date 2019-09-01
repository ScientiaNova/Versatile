package com.EmosewaPixel.pixellib.materialsystem.types

import com.EmosewaPixel.pixellib.materialsystem.lists.TextureTypes

//Texture Types determine which sets of textures Material use for Object Types
class TextureType(private val name: String) {
    init {
        TextureTypes.add(this)
    }

    override fun toString() = name
}