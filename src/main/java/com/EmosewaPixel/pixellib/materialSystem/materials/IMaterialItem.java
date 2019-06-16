package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;

//Tis interface is used for clases that take a material and object type
public interface IMaterialItem {
    Material getMaterial();

    ObjectType getObjType();
}