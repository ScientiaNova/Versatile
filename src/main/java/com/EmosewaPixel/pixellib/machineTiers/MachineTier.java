package com.EmosewaPixel.pixellib.machineTiers;

//Machine Tiers are used for making a tiered machine system. This is a base class that you will probably want to extend if you want to make such a system
public class MachineTier {
    private String name;
    private int color;

    public MachineTier(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}