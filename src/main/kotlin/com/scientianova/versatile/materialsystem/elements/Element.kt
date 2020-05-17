package com.scientianova.versatile.materialsystem.elements

sealed class Element(val name: String) {
    abstract val symbol: String
    abstract val protons: Int
    abstract val neutrons: Int

    val atomicMass get() = protons * 1.00782458 + neutrons * 1.008664

    val density get() = atomicMass / 22.414 * 1000

    operator fun times(count: Int) = ElementStack(this, count)

    override fun toString() = symbol

    @JvmOverloads
    fun toStack(count: Int = 1) = ElementStack(this, count)
}

class BaseElement(name: String, override val protons: Int, override val neutrons: Int, override val symbol: String) : Element(name)
class Isotope(name: String, standard: () -> BaseElement, val nucleons: Int, symbolFn: (() -> String)?) : Element(name) {
    val standard by lazy(standard)
    override val symbol by lazy { symbolFn?.invoke() ?: "${this.standard.symbol}-$nucleons" }
    override val protons get() = standard.protons
    override val neutrons get() = nucleons - standard.protons
}