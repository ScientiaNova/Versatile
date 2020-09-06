package com.scientianova.versatile.common.registry

import com.scientianova.versatile.materialsystem.elements.Element
import com.scientianova.versatile.materialsystem.forms.Form
import com.scientianova.versatile.materialsystem.materials.Material

lateinit var elements: StringBasedRegistry<Element>
    internal set

lateinit var materials: StringBasedRegistry<Material>
    internal set

lateinit var forms: StringBasedRegistry<Form>
    internal set