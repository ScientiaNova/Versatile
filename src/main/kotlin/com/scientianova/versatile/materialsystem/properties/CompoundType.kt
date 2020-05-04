package com.scientianova.versatile.materialsystem.properties

/*
This is used to determine whether a compound material is an alloy or chemical compound. This is quite important for tech mods that add electrolysis and centrifuging
and for determining the densities of the materials' fluids. This doesn't really matter for pure elemental materials.
 */
enum class CompoundType {
    ALLOY,
    CHEMICAL
}