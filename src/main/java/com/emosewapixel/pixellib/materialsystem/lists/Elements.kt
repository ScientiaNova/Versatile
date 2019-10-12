package com.emosewapixel.pixellib.materialsystem.lists

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.elements.Element
import org.openzen.zencode.java.ZenCodeGlobals
import org.openzen.zencode.java.ZenCodeType

//This class contains functions for interacting with the global list of elements
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.lists.Elements")
object Elements {
    private val elements = hashMapOf<String, Element>()

    @ZenCodeGlobals.Global("elements")
    val instance = this

    @JvmStatic
    val all: Collection<Element>
        @ZenCodeType.Getter get() = elements.values

    @JvmStatic
    fun add(element: Element) {
        elements[element.symbol.toLowerCase()] = element
    }

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(name: String) = elements[name]

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    operator fun contains(name: String) = name in elements
}