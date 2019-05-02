package com.EmosewaPixel.pixellib.materialSystem.materials;

public enum HarvestTiers implements IHarvestTier {
    STONE(1.5F, 1.5F, 0),
    IRON(3, 3, 1),
    DIAMOND(3, 3, 2);

    private float hardness;
    private float resistance;
    private int level;

    HarvestTiers(float hardness, float resistance, int level) {
        this.hardness = hardness;
        this.resistance = resistance;
        this.level = level;
    }

    @Override
    public float getHardness() {
        return hardness;
    }

    @Override
    public float getResistance() {
        return resistance;
    }

    @Override
    public int getHarvestLevel() {
        return level;
    }
}