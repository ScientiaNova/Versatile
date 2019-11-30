package com.emosewapixel.pixellib.materialsystem.lists

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import org.openzen.zencode.java.ZenCodeGlobals
import org.openzen.zencode.java.ZenCodeType

//This class contains functions for interacting with the global list of object types
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.lists.ObjTypes")
object ObjTypes {
    private val objTypes = hashMapOf<String, ObjectType>()

    @ZenCodeGlobals.Global("objTypes")
    val instance = this

    @JvmStatic
    val all
        @ZenCodeType.Getter get() = objTypes.values

    @JvmStatic
    fun add(type: ObjectType) = objTypes[type.name]?.merge(type) ?: objTypes.put(type.name, type)

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(name: String): ObjectType? = objTypes[name]

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    operator fun contains(name: String) = name in objTypes
}