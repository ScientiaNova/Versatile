package com.emosewapixel.pixellib.integration.crafttweaker

import com.blamejared.crafttweaker.api.annotations.BracketDumper
import com.blamejared.crafttweaker.api.annotations.BracketResolver
import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.lists.Elements
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes

@ZenRegister
object BracketHandler {
    @BracketResolver("mat")
    fun getMat(tokens: String) = Materials[tokens]

    @BracketDumper("mat")
    fun getMats() = Materials.all

    @BracketResolver("objType")
    fun getObjType(tokens: String) = ObjTypes[tokens]

    @BracketDumper("objType")
    fun getObjTypes() = ObjTypes.all

    @BracketResolver("element")
    fun getElement(tokens: String) = Elements[tokens]

    @BracketDumper("element")
    fun getElements() = Elements.all
}