package com.emosewapixel.pixellib.materialsystem.elements

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.lists.Elements
import org.openzen.zencode.java.ZenCodeType

/*
Elements are objects with a few properties, that can be assigned to materials. They're mainly meant to be used for tech mods.
If you're adding materials such as Copper, Tin, Lead, etc. please do assign them, but if you're adding fantasy materials, you may ignore them.
*/
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.elements.Element")
open class Element @ZenCodeType.Constructor constructor(@ZenCodeType.Field val symbol: String, @ZenCodeType.Field val protons: Int, @ZenCodeType.Field val neutrons: Int) {
    init {
        Elements.add(this)
    }

    val atomicMass: Double
        @ZenCodeType.Getter get() = protons * 1.00782458 + neutrons * 1.008664

    val density: Double
        @ZenCodeType.Getter get() = atomicMass / 22.414 * 1000

    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    operator fun times(count: Int) = ElementStack(this, count)

    fun toStack(count: Int = 1) = ElementStack(this, count)
}