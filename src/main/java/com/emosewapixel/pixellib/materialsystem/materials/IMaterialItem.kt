package com.emosewapixel.pixellib.materialsystem.materials

import com.emosewapixel.pixellib.materialsystem.types.ObjectType

//Tis interface is used for clases that take a material and object type
interface IMaterialItem {
    val mat: Material
    val objType: ObjectType
}