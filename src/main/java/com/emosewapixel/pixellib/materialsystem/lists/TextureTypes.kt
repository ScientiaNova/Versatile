package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.materialsystem.types.TextureType

//This class contains functions for interacting with the global list of texture types
object TextureTypes {
    private val textureTypes = hashMapOf<String, TextureType>()

    @JvmStatic
    fun getAll() = textureTypes.values

    @JvmStatic
    fun add(type: TextureType) {
        textureTypes[type.toString()] = type
    }

    @JvmStatic
    operator fun get(name: String): TextureType? = textureTypes[name]
}