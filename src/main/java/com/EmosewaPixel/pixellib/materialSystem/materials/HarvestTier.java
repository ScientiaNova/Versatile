package com.EmosewaPixel.pixellib.materialSystem.materials;

public class HarvestTier {
    private float hardness;
    private float resistance;
    private int level;

    public HarvestTier(float hardness, float resistance, int level) {
        this.hardness = hardness;
        this.resistance = resistance;
        this.level = level;
    }

    public float getHardness() {
        return hardness;
    }

    public float getResistance() {
        return resistance;
    }

    public int getHarvestLevel() {
        return level;
    }
}