package com.EmosewaPixel.pixellib.materialSystem.element;

import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ElementUtils {
    public static List<MaterialStack> getElementalComposition(Material mat) {
        if (mat.isElement())
            return Arrays.asList(new MaterialStack(mat));

        if (mat.getComposition().size() == 0)
            return Collections.emptyList();

        return mat.getComposition().stream().flatMap(ms ->
            ElementUtils.getElementalComposition(ms.getMaterial()).stream()
                    .map(m -> new MaterialStack(m.getMaterial(), m.getCount() * ms.getCount()))
        ).collect(Collectors.toList());
    }

    public static int getTotalProtons(Material mat) {
        return getElementalComposition(mat).stream().mapToInt(m -> m.getMaterial().getElementalproperties().getProtons() * m.getCount()).sum();
    }

    public static int getTotalNeutrons(Material mat) {
        return getElementalComposition(mat).stream().mapToInt(m -> m.getMaterial().getElementalproperties().getNeutrons() * m.getCount()).sum();
    }

    public static double getMolarMass(Material mat) {
        return getElementalComposition(mat).stream().mapToDouble(m -> m.getMaterial().getElementalproperties().getAtomicMass() * m.getCount()).sum();
    }

    public static double getTotalDensity(Material mat) {
        return getElementalComposition(mat).stream().mapToDouble(m -> m.getMaterial().getElementalproperties().getDensity() * m.getCount()).sum();
    }
}