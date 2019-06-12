package com.EmosewaPixel.pixellib.machineTiers;

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