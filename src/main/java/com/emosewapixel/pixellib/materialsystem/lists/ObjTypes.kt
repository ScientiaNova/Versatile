package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.materialsystem.types.ObjectType

//This class contains functions for interacting with the global list of object types
object ObjTypes {
    private val objTypes = hashMapOf<String, ObjectType>()

    @JvmStatic
    fun getAll() = objTypes.values

    @JvmStatic
    fun add(type: ObjectType) = if (type.name in objTypes) objTypes[type.name]!!.merge(type) else objTypes[type.name] = type

    @JvmStatic
    operator fun get(name: String): ObjectType? = objTypes[name]
}