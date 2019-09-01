package com.EmosewaPixel.pixellib.materialsystem.materials

import com.EmosewaPixel.pixellib.materialsystem.types.ObjectType

//Tis interface is used for clases that take a material and object type
interface IMaterialItem {
    val mat: Material

    val objType: ObjectType
}