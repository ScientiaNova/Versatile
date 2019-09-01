package com.EmosewaPixel.pixellib.materialsystem.types

import com.EmosewaPixel.pixellib.materialsystem.materials.Material

import java.util.function.Predicate

//Item Types are Object Types used for generating Items
class ItemType(name: String, requirement: Predicate<Material>) : ObjectType(name, requirement)