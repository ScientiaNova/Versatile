package com.scientianovateam.versatile.materialsystem.elements

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getIntOrNull
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.materialsystem.lists.ELEMENTS

sealed class Element {
    abstract val name: String
    abstract val symbol: String
    abstract val protons: Int
    abstract val neutrons: Int
    open val nucleons get() = protons + neutrons

    val atomicMass get() = protons * 1.00782458 + neutrons * 1.008664

    val density get() = atomicMass / 22.414 * 1000

    operator fun times(count: Int) = ElementStack(this, count)

    @JvmOverloads
    fun toStack(count: Int = 1) = ElementStack(this, count)
}

data class BaseElement(
        override val name: String,
        override val symbol: String = name.take(2).capitalize(),
        override val protons: Int,
        override val neutrons: Int
) : Element()

data class Isotope(
        override val name: String,
        val standardIsotope: BaseElement,
        override val nucleons: Int,
        override val symbol: String = "${standardIsotope.symbol}-$nucleons"
) : Element() {
    override val protons = standardIsotope.protons
    override val neutrons = nucleons - protons
}

object ElementSerializer : IJSONSerializer<Element, JsonObject> {
    override fun read(json: JsonObject) = json.getStringOrNull("standard_isotope")?.let {
        val standardIsotope = (ELEMENTS[it] as? BaseElement
                ?: error("Isotope required a base element, but got $it"))
        val nucleons = json.getIntOrNull("nucleons") ?: error("Missing nucleons for isotope")
        Isotope(
                name = json.getStringOrNull("name") ?: error("Isotope missing a registry name"),
                standardIsotope = standardIsotope,
                nucleons = nucleons,
                symbol = json.getStringOrNull("symbol") ?: "${standardIsotope.symbol}-$nucleons"
        )
    } ?: run {
        val name = json.getStringOrNull("name") ?: error("Element missing a registry name")
        BaseElement(
                name = name,
                symbol = json.getStringOrNull("symbol") ?: name.take(2).capitalize(),
                protons = json.getIntOrNull("protons") ?: error("Missing protons for $name"),
                neutrons = json.getIntOrNull("neutrons") ?: error("Missing neutrons for $name")
        )
    }

    override fun write(obj: Element) = when (obj) {
        is BaseElement -> json {
            "symbol" to obj.symbol
            "protons" to obj.protons
            "neutrons" to obj.neutrons
        }
        is Isotope -> json {
            "standard_isotope" to obj.standardIsotope.name
            "nucleons" to obj.nucleons
            if (obj.symbol != "${obj.standardIsotope.symbol}-${obj.nucleons}")
                "symbol" to obj.symbol
        }
    }
}