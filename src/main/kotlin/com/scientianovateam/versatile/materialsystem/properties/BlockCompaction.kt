package com.scientianovateam.versatile.materialsystem.properties

enum class BlockCompaction(val serializedName: String) {
    FROM_3X3("3x3"),
    FROM_2X2("2x2"),
    NONE("none");

    companion object {
        fun fromString(name: String) = when (name) {
            "3x3" -> FROM_3X3
            "2x2" -> FROM_2X2
            "none" -> NONE
            else -> error("No block compaction with name $name")
        }
    }
}