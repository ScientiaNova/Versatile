package com.scientianova.versatile.common.registry

import com.scientianova.versatile.materialsystem.elements.Element
import com.scientianova.versatile.materialsystem.forms.GlobalForm
import com.scientianova.versatile.materialsystem.materials.Material

lateinit var ELEMENTS: StringBasedRegistry<Element>
    internal set

lateinit var MATERIALS: StringBasedRegistry<Material>
    internal set

lateinit var FORMS: StringBasedRegistry<GlobalForm>
    internal set