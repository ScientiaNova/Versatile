package com.EmosewaPixel.pixellib.materialSystem.element;

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