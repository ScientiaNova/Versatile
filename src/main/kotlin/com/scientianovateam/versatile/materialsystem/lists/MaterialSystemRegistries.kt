package com.scientianovateam.versatile.materialsystem.lists

import com.scientianovateam.versatile.common.registry.NamespacelessRegistry
import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.materialsystem.elements.Element
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.Property

val MATERIAL_PROPERTIES = Registry<Property>()
val FORM_PROPERTIES = Registry<Property>()
val ELEMENTS = NamespacelessRegistry<Element>()
val MATERIALS = NamespacelessRegistry<Material>()
val FORMS = NamespacelessRegistry<Form>()

fun NamespacelessRegistry<Element>.add(element: Element) = set(element.name, element)
fun NamespacelessRegistry<Material>.add(mat: Material) = set(mat.name, mat)
fun NamespacelessRegistry<Form>.add(form: Form) = set(form.name, form)