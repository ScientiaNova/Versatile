package com.scientianovateam.versatile.common.registry

import com.scientianovateam.versatile.materialsystem.properties.MatProperty
import com.scientianovateam.versatile.materialsystem.properties.FormProperty

val MATERIAL_PROPERTIES = Registry<MatProperty<*>>()

val OBJECT_TYPE_PROPERTIES = Registry<FormProperty<*>>()