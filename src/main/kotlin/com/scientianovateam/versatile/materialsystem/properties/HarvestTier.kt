package com.scientianovateam.versatile.materialsystem.properties

data class HarvestTier(val hardness: Float, val resistance: Float, val harvestLevel: Int) : IBranchingProperty {
    override fun get(name: String): Any? = when (name) {
        "hardness" -> hardness
        "resistance" -> resistance
        "harvest_level" -> harvestLevel
        else -> null
    }
}