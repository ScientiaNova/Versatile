package com.EmosewaPixel.pixellib.materialSystem.element;

import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialStack;

//Elements Stacks are ways of getting an amount of a certain Element
public class ElementStack {
    private Element element;
    private int count;

    public ElementStack(Element element, int count) {
        this.element = element;
        this.count = count;
    }

    public ElementStack(Element element) {
        this(element, 1);
    }

    public ElementStack(MaterialStack ms) {
        this(ms.getMaterial().getElement(), ms.getCount());
    }

    public Element getElement() {
        return element;
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

    public ElementStack copy() {
        return new ElementStack(this.element, this.count);
    }
}