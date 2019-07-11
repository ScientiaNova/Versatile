package com.EmosewaPixel.pixellib.materialsystem.element;

import com.EmosewaPixel.pixellib.materialsystem.materials.CompoundType;
import com.EmosewaPixel.pixellib.materialsystem.materials.Material;
import com.EmosewaPixel.pixellib.materialsystem.materials.MaterialStack;
import com.EmosewaPixel.pixellib.miscutils.StreamUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

//This class contains functions used for determining the elemental properties of compounds
public class ElementUtils {
    public static List<ElementStack> getElementalComposition(Material mat) {
        if (mat.getComposition().size() == 0)
            return Collections.singletonList(new ElementStack(mat.getElement()));

        Map<Element, Integer> map = mat.getFullComposition().stream().collect(Collectors.toMap(s -> s.getMaterial().getElement(), MaterialStack::getCount, StreamUtils::sum));

        return map.keySet().contains(Elements.NULL) ? Collections.singletonList(new ElementStack(Elements.NULL)) : map.entrySet().stream().map(e -> new ElementStack(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    public static int getTotalProtons(Material mat) {
        return getElementalComposition(mat).stream().mapToInt(m -> m.getElement().getProtons() * m.getCount()).sum();
    }

    public static int getTotalNeutrons(Material mat) {
        return getElementalComposition(mat).stream().mapToInt(m -> m.getElement().getNeutrons() * m.getCount()).sum();
    }

    public static double getMolarMass(Material mat) {
        return getElementalComposition(mat).stream().mapToDouble(m -> m.getElement().getAtomicMass() * m.getCount()).sum();
    }

    public static double getTotalDensity(Material mat) {
        DoubleStream stream = getElementalComposition(mat).stream().mapToDouble(m -> m.getElement().getDensity() * m.getCount());
        return mat.getCompoundType() == CompoundType.CHEMICAL ? stream.sum() : stream.average().getAsDouble();
    }
}