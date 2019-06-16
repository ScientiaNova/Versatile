package com.EmosewaPixel.pixellib.materialSystem.materials;

//Material Stacks are ways of getting an amount of a certain Material
public class MaterialStack {
    private Material material;
    private int count;

    public MaterialStack(Material material, int count) {
        this.material = material;
        this.count = count;
    }

    public MaterialStack(Material material) {
        this(material, 1);
    }

    public Material getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public MaterialStack copy() {
        return new MaterialStack(this.material, this.count);
    }
}