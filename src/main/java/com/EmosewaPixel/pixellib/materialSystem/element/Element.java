package com.EmosewaPixel.pixellib.materialsystem.element;

/*
Elements are objects with a few properties, that can be assigned to materials. They're mainly meant to be used for tech mods.
If you're adding materials such as Copper, Tin, Lead, etc. please do assign them, but if you're adding fantasy materials, you may ignore them.
*/
public class Element {
    private String symbol;
    private int protons;
    private int neutrons;

    public Element(String symbol, int protons, int neutrons) {
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
        return getAtomicMass() / 22.414;
    }
}