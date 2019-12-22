package com.scientianovateam.versatile.materialsystem.properties

data class TransitionProperties(val neededAmount: Int, val to: String) : IBranchingProperty {
    override fun get(name: String): Any? = when (name) {
        "needed_amount" -> neededAmount
        "end_material" -> to
        else -> null
    }
}