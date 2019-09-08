package com.emosewapixel.pixellib.materialsystem.types

import com.emosewapixel.pixellib.materialsystem.materials.Material

import java.util.function.Predicate

//Item Types are Object Types used for generating Items
open class ItemType(name: String, requirement: Predicate<Material>) : ObjectType(name, requirement)