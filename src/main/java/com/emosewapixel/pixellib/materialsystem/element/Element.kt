package com.emosewapixel.pixellib.materialsystem.element

/*
Elements are objects with a few properties, that can be assigned to materials. They're mainly meant to be used for tech mods.
If you're adding materials such as Copper, Tin, Lead, etc. please do assign them, but if you're adding fantasy materials, you may ignore them.
*/
open class Element(val symbol: String, val protons: Int, val neutrons: Int) {
    val atomicMass: Double
        get() = protons * 1.00782458 + neutrons * 1.008664

    val density: Double
        get() = atomicMass / 22.414
}