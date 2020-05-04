package com.scientianova.versatile.materialsystem.properties

interface IBranchingProperty {
    operator fun get(name: String): Any?
}