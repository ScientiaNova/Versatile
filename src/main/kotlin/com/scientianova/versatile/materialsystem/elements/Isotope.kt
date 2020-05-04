package com.scientianova.versatile.materialsystem.elements

//An isotope is an element with a different amount of neutrons, both in irl and here
class Isotope(symbol: String, val standardIsotope: Element, nucleons: Int) : Element(symbol, standardIsotope.protons, nucleons - standardIsotope.protons) {
    constructor(element: Element, nucleons: Int) : this(element.symbol + "-" + nucleons.toString(), element, nucleons)
}