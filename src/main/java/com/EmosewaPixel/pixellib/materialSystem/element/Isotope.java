package com.EmosewaPixel.pixellib.materialsystem.element;

//An isotope is an element with a different amount of neutrons, both in irl and here
public class Isotope extends Element {
    private Element standardIsotope;

    public Isotope(String symbol, Element element, int nucleons) {
        super(symbol, element.getProtons(), nucleons - element.getProtons());
        standardIsotope = element;
    }

    public Isotope(Element element, int nucleons) {
        this(element.getSymbol() + "-" + Integer.toString(nucleons), element, nucleons);
    }

    public Element getStandardIsotope() {
        return standardIsotope;
    }
}