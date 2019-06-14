package com.EmosewaPixel.pixellib.materialSystem.element;

public class ElementalProperties {
    private String symbol;
    private int protons;
    private int neutrons;

    public ElementalProperties(String symbol, int protons, int neutrons) {
        this.symbol = symbol;
        this.protons = protons;
        this.neutrons = neutrons;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getProtons() {
        return protons;
    }

    public int getNeutrons() {
        return neutrons;
    }

    public double getAtomicMass() {
        return protons * 1.00782458 + neutrons * 1.008664;
    }

    public double getDensity() {
        return getAtomicMass() /  22.414;
    }
}